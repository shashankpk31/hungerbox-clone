package com.hungerbox.wallet_service.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.hungerbox.wallet_service.constant.WalletConstantAndEnum.Role;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireRole {
	Role value(); // This will hold the role name, e.g., "ROLE_SUPER_ADMIN"
}