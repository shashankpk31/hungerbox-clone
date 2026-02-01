package com.hungerbox.identity.service;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hungerbox.identity.Mapper.UserMapper;
import com.hungerbox.identity.client.VendorClient;
import com.hungerbox.identity.config.RabbitConfig;
import com.hungerbox.identity.constant.ErrorMessageWarnConstant;
import com.hungerbox.identity.constant.ErrorMessageWarnConstant.Error;
import com.hungerbox.identity.constant.ErrorMessageWarnConstant.Message;
import com.hungerbox.identity.constant.IdentityConstantAndEnum.NOTIF_TYPE;
import com.hungerbox.identity.constant.IdentityConstantAndEnum.USER_ACC_STAT;
import com.hungerbox.identity.constant.IdentityConstantAndEnum.Role;
import com.hungerbox.identity.dto.NotificationPayload;
import com.hungerbox.identity.dto.request.RegisterOrgRequest;
import com.hungerbox.identity.dto.request.RegisterRequest;
import com.hungerbox.identity.dto.response.ApiResponse;
import com.hungerbox.identity.dto.response.LoginResponse;
import com.hungerbox.identity.entity.User;
import com.hungerbox.identity.repository.UserRepository;

@Service
public class AuthService {

	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final UserMapper userMapper;

	private final JwtService jwtService;

	private final RabbitTemplate rabbitTemplate;
	private final StringRedisTemplate redisTemplate;

	private final VendorClient vendorClient;

	private final SecureRandom secureRandom = new SecureRandom();

	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
			UserMapper userMapper, RabbitTemplate rabbitTemplate, StringRedisTemplate redisTemplate,
			VendorClient vendorClient) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.userMapper = userMapper;
		this.redisTemplate = redisTemplate;
		this.rabbitTemplate = rabbitTemplate;
		this.vendorClient = vendorClient;
	}

	public String register(RegisterRequest request) {
		logger.info("Registering new user: {}", request.username());

		// 1. Validate Organization (Robustness Check)
		if (request.organizationId() != null) {
			try {
				ResponseEntity<ApiResponse> response = vendorClient.getOrganizationById(request.organizationId());
				if (!response.getStatusCode().is2xxSuccessful()) {
					throw new RuntimeException("Organization not found or inactive.");
				}
			} catch (Exception e) {
				throw new RuntimeException("Could not verify organization. Vendor service might be down.");
			}
		}

		// 2. Existing phone check
		if (request.phoneNumber() != null && userRepository.findByPhoneNumber(request.phoneNumber()).isPresent()) {
			throw new RuntimeException(Error.PHONE_ALREADY_REGISTERED.getMessage());
		}

		// 3. Save Entity (Ensure userMapper maps organizationId and role)
		User userEntity = userMapper.toEntity(request);

		if (request.role().equals(Role.ROLE_EMPLOYEE)) {
			userEntity.setStatus(USER_ACC_STAT.ACTIVE);
		} else if (request.role().equals(Role.ROLE_VENDOR)) {
			userEntity.setStatus(USER_ACC_STAT.PENDING_APPROVAL);
		}else {
			throw new RuntimeException(Error.UNAUTHORIZED.getMessage());
		}
		
		userEntity.setPassword(passwordEncoder.encode(request.password()));
		userRepository.save(userEntity);

		// 2. Determine Primary Identifier & Notification Type
		String identifier;
		String type;

		if (request.email() != null && !request.email().isBlank()) {
			identifier = request.email();
			type = NOTIF_TYPE.EMAIL.toString();
		} else {
			identifier = request.phoneNumber();
			type = NOTIF_TYPE.SMS.toString();
		}

		String otp = String.format("%06d", secureRandom.nextInt(1000000));
		redisTemplate.opsForValue().set("OTP:" + identifier, otp, 5, TimeUnit.MINUTES);

		NotificationPayload payload = new NotificationPayload(identifier,
				"Welcome to HungerBox! Your verification code is: " + otp, type);
		rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTING_KEY, payload);

		return Message.AUTH_REG_SUCCESS.getMessage();
	}

	public LoginResponse login(String loginIdentifier, String password) {
		LoginResponse response = new LoginResponse();
	    User existingUser = userRepository.findByEmailOrPhoneNumber(loginIdentifier, loginIdentifier)
	            .orElseThrow(() -> new UsernameNotFoundException(Error.USER_NOT_FOUND.getMessage()));

	    // 1. Validate Password
	    if (!passwordEncoder.matches(password, existingUser.getPassword())) {
	        throw new RuntimeException(Error.INVALID_CREDENTIALS.getMessage());
	    }

	    // 2. BLOCK LOGIN if not verified
	    boolean verified = existingUser.getIsEmailVerified() != null && existingUser.getIsEmailVerified() || 
	                       existingUser.getIsPhoneVerified() != null && existingUser.getIsPhoneVerified();
	    
	    if (!verified) {
	        throw new RuntimeException(Error.ACCOUNT_NOT_VERIFIED.getMessage());
	    }

	    // 3. Generate Token if verified
	    String token = jwtService.generateToken(existingUser);
	    response.setUser(userMapper.toResponse(existingUser));
	    response.setToken(token);
	    return response;
	}

	public void validateToken(String token) {
		jwtService.extractUsername(token);
	}

	public boolean verifyIdentifier(String identifier, String otp) {
		String storedOtp = redisTemplate.opsForValue().get("OTP:" + identifier);

		if (storedOtp != null && storedOtp.equals(otp)) {
			User user = userRepository.findByEmailOrPhoneNumber(identifier, identifier)
					.orElseThrow(() -> new RuntimeException(Error.USER_NOT_FOUND.getMessage()));

			if (identifier.contains("@")) {
				user.setIsEmailVerified(true);
			} else {
				user.setIsPhoneVerified(true);
			}

			userRepository.save(user);
			redisTemplate.delete("OTP:" + identifier);
			return true;
		}
		return false;
	}

	public String resendOtp(String identifier, String type) {
		// 1. Check if user exists (search by email OR phone)
		User user = userRepository.findByEmailOrPhoneNumber(identifier, identifier)
				.orElseThrow(() -> new RuntimeException(Error.USER_NOT_FOUND.getMessage()));

		// 2. Generate new 6-digit OTP
		String otp = String.format("%06d", secureRandom.nextInt(1000000));

		// 3. Update Redis (Overwrites existing OTP and resets 5-minute TTL)
		redisTemplate.opsForValue().set("OTP:" + identifier, otp, 5, TimeUnit.MINUTES);

		// 4. Create context-aware message
		String message = type.equalsIgnoreCase(NOTIF_TYPE.EMAIL.toString())
				? "Your new HungerBox email verification code is: " + otp
				: "Your HungerBox mobile verification code is: " + otp;

		// 5. Send to RabbitMQ
		NotificationPayload payload = new NotificationPayload(identifier, message, type.toUpperCase());
		rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTING_KEY, payload);

		return "New verification code sent via " + type;
	}

	public String registerOrg(RegisterOrgRequest request) {
		logger.info("Registering new Org Admin: {}", request.username());

		// 1. Validate Organization (Robustness Check)
		if (request.organizationId() != null) {
			try {
				ResponseEntity<ApiResponse> response = vendorClient.getOrganizationById(request.organizationId());
				if (!response.getStatusCode().is2xxSuccessful()) {
					throw new RuntimeException("Organization not found or inactive.");
				}
			} catch (Exception e) {
				throw new RuntimeException("Could not verify organization. Vendor service might be down.");
			}
		}

		// 2. Existing phone check
		if (request.phoneNumber() != null && userRepository.findByPhoneNumber(request.phoneNumber()).isPresent()) {
			throw new RuntimeException(Error.PHONE_ALREADY_REGISTERED.getMessage());
		}

		// 3. Save Entity (Ensure userMapper maps organizationId and role)
		User userEntity = userMapper.toEntity(request);
		userEntity.setStatus(USER_ACC_STAT.ACTIVE);

		
		userEntity.setPassword(passwordEncoder.encode(request.password()));
		userRepository.save(userEntity);

		// 2. Determine Primary Identifier & Notification Type
		String identifier;
		String type;

		if (request.email() != null && !request.email().isBlank()) {
			identifier = request.email();
			type = NOTIF_TYPE.EMAIL.toString();
		} else {
			identifier = request.phoneNumber();
			type = NOTIF_TYPE.SMS.toString();
		}

		String otp = String.format("%06d", secureRandom.nextInt(1000000));
		redisTemplate.opsForValue().set("OTP:" + identifier, otp, 5, TimeUnit.MINUTES);

		NotificationPayload payload = new NotificationPayload(identifier,
				"Welcome to HungerBox! Your verification code is: " + otp, type);
		rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTING_KEY, payload);

		return Message.AUTH_REG_SUCCESS.getMessage();
	}

}