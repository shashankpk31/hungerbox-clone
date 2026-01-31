package com.hungerbox.vendor_service.annotation;

import java.lang.annotation.*;

import com.hungerbox.vendor_service.constant.VendorConstantAndEnum.Role;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireRole {
	Role value(); // This will hold the role name, e.g., "ROLE_SUPER_ADMIN"
}