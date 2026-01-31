package com.hungerbox.identity.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hungerbox.identity.annotation.RequireRole;
import com.hungerbox.identity.constant.ErrorMessageWarnConstant.Error;
import com.hungerbox.identity.constant.ErrorMessageWarnConstant.Message;
import com.hungerbox.identity.constant.IdentityConstantAndEnum.Role;
import com.hungerbox.identity.dto.request.AuthRequest;
import com.hungerbox.identity.dto.request.RegisterOrgRequest;
import com.hungerbox.identity.dto.request.RegisterRequest;
import com.hungerbox.identity.dto.response.ApiResponse;
import com.hungerbox.identity.dto.response.EmptyJson;
import com.hungerbox.identity.dto.response.LoginResponse;
import com.hungerbox.identity.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/register")
	public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest user) {
		logger.info("REST request to register user: {}", user.username());
		String msg = authService.register(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(true, msg, new EmptyJson()));
	}
	
	@PostMapping("/register/org-admin")
	@RequireRole(Role.ROLE_SUPER_ADMIN)
	public ResponseEntity<ApiResponse> registerOrgAdmin(@RequestBody RegisterOrgRequest user) {
		logger.info("REST request to register user: {}", user.username());
		String msg = authService.registerOrg(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(true, msg, new EmptyJson()));
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse> login(@RequestBody AuthRequest authRequest) {
		logger.info("REST request to login user: {}", authRequest.userIdentifier());
		LoginResponse response = authService.login(authRequest.userIdentifier(), authRequest.password());
		return ResponseEntity
				.ok(new ApiResponse(true, Message.AUTH_LOGIN_SUCCESS.getMessage(), response));
	}

	@GetMapping("/validate")
	public ResponseEntity<ApiResponse> validateToken(@RequestParam("token") String token) {
		authService.validateToken(token);
		return ResponseEntity.ok(new ApiResponse(true, Message.VALID_TOKEN.getMessage(), new EmptyJson()));
	}

	@PostMapping("/verify")
	public ResponseEntity<ApiResponse> verify(@RequestParam String identifier, @RequestParam String otp) {
		boolean isVerified = authService.verifyIdentifier(identifier, otp);
		if (isVerified) {
			return ResponseEntity.ok(new ApiResponse(true, Message.ACC_VERIFIED.getMessage(), new EmptyJson()));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ApiResponse(false, Error.INVALID_EXPIRED_OTP.getMessage(), new EmptyJson()));
	}
	
	@PostMapping("/resend-otp")
	public ResponseEntity<ApiResponse> resendOtp(@RequestParam String identifier, @RequestParam String type) {

		logger.info("REST request to resend {} OTP for: {}", type, identifier);
		String result = authService.resendOtp(identifier, type);

		return ResponseEntity.ok(new ApiResponse(true, result, new EmptyJson()));
	}
	
}