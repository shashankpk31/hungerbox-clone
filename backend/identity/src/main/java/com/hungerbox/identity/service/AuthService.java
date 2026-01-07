package com.hungerbox.identity.service;

import com.hungerbox.identity.Mapper.UserMapper;
import com.hungerbox.identity.constant.ErrorMessageWarnConstant.Message;
import com.hungerbox.identity.dto.request.RegisterRequest;
import com.hungerbox.identity.entity.User;
import com.hungerbox.identity.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userMapper=userMapper;
    }

    public String register(RegisterRequest request) { 
        logger.info("Registering new user: {}", request.username());
        User userEntity = userMapper.toEntity(request);        
        userEntity.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(userEntity);
        
        return Message.AUTH_REG_SUCCESS.getMessage();
    }

    public String login(String username, String password) {
    	String token = userRepository.findByUsername(username)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .map(user -> jwtService.generateToken(user.getUsername(), user.getRole().name()))
                .orElseThrow(() -> {
                    logger.error("Authentication failed for user: {}", username);
                    return new RuntimeException("Invalid Credentials");
                });
        return token;
    }

    public void validateToken(String token) {
        jwtService.extractUsername(token);
    }
}