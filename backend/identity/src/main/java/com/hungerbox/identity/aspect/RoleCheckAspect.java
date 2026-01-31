package com.hungerbox.identity.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.hungerbox.identity.annotation.RequireRole;
import com.hungerbox.identity.constant.ErrorMessageWarnConstant.Error;
import com.hungerbox.identity.entity.User;
import com.hungerbox.identity.repository.UserRepository;

@Aspect
@Component
public class RoleCheckAspect {

	UserRepository userRepository;

	public RoleCheckAspect(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Before("@annotation(requireRole)")
	public void checkRole(RequireRole requireRole) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
			User user = userRepository.findByUsername(authentication.getName())
					.orElseThrow(() -> new RuntimeException(Error.USER_NOT_FOUND.getMessage()));
			String requiredRole = requireRole.value().toString();
			if (!user.getRole().name().equalsIgnoreCase(requiredRole)) {
				throw new RuntimeException(
						"Access Denied: You do not have the required permissions (" + requiredRole + ")");
			}
		} else {
			throw new RuntimeException("Access Denied: No authentication context found.");
		}

	}
}