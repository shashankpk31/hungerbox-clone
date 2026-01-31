package com.hungerbox.payment_service.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.hungerbox.payment_service.annotation.RequireRole;
import com.hungerbox.payment_service.util.UserContext;

@Aspect
@Component
public class RoleCheckAspect {

    @Before("@annotation(requireRole)")
    public void checkRole(RequireRole requireRole) {
        // Pull from our custom ThreadLocal context
        UserContext.UserContextHolder context = UserContext.get();

        if (context == null || context.role() == null) {
            throw new RuntimeException("Access Denied: No authentication context found.");
        }

        String requiredRole = requireRole.value().toString();
        if (!context.role().equalsIgnoreCase(requiredRole)) {
            throw new RuntimeException("Access Denied: You do not have the required permissions (" + requiredRole + ")");
        }
    }
}