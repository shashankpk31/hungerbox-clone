package com.hungerbox.identity.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
		@NotBlank(message = "Email or Phone No is required") String userIdentifier,
		@NotBlank(message = "Password is required") String password
) {}
