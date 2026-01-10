package com.hungerbox.identity.controller;

import java.util.Map;

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

import com.hungerbox.identity.constant.ErrorMessageWarnConstant.Message;
import com.hungerbox.identity.dto.request.AuthRequest;
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
	
	@GetMapping("/home")
	public ResponseEntity<ApiResponse> home() {
		return ResponseEntity.ok(new ApiResponse(true, Message.VALID_TOKEN.getMessage(), new EmptyJson()));
	}

	@PostMapping("/register")
	public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest user) {
		logger.info("REST request to register user: {}", user.username());
		String msg = authService.register(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(true, msg, new EmptyJson()));
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse> login(@RequestBody AuthRequest authRequest) {
		logger.info("REST request to login user: {}", authRequest.email());
		LoginResponse response = authService.login(authRequest.email(), authRequest.password());
		return ResponseEntity
				.ok(new ApiResponse(true, Message.AUTH_LOGIN_SUCCESS.getMessage(), response));
	}

	@GetMapping("/validate")
	public ResponseEntity<ApiResponse> validateToken(@RequestParam("token") String token) {
		authService.validateToken(token);
		return ResponseEntity.ok(new ApiResponse(true, Message.VALID_TOKEN.getMessage(), new EmptyJson()));
	}
}