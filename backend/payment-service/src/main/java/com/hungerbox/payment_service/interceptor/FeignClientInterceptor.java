package com.hungerbox.payment_service.interceptor;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.hungerbox.payment_service.util.UserContext;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class FeignClientInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {

        UserContext.UserContextHolder context = UserContext.get();
        
        String authHeader=context.authHeader();
        Long userId = context.userId();
        String username = context.username();
        String role = context.role();
        Long orgId = context.orgId();
        Long officeId = context.officeId();

        // Propagate the headers to the next service
        if (authHeader!=null &&  !authHeader.isBlank()) {
        	template.header(HttpHeaders.AUTHORIZATION, userId.toString());
		}
        if (userId != null)
            template.header("X-Auth-User-Id", userId.toString());
        if (username != null)
            template.header("X-Auth-User-Username", username);
        if (role != null)
            template.header("X-Auth-User-Role", role);
        if (orgId != null)
            template.header("X-Auth-User-Org-Id", orgId.toString());
        if (officeId != null)
            template.header("X-Auth-User-Office-Id", officeId.toString());
    }
}
