package com.hungerbox.identity.dto.request;


import com.hungerbox.identity.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20)
    String username,

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    String email,

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    String password,

    @NotNull(message = "Role is required")
    Role role,

    // Employee Specific
    String employeeId,
    String companyName,

    // Vendor Specific
    String shopName,
    String gstNumber,
    String contactNumber,

    String adminSecretCode 
) {}