package com.hungerbox.identity.interceptor;

import com.hungerbox.identity.entity.User;
import feign.RequestInterceptor;
import feign.RequestTemplate;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();

            
            template.header(HttpHeaders.AUTHORIZATION, "");
            template.header("X-Auth-User-Id", String.valueOf(user.getId()));
            template.header("X-Auth-User-Username", user.getUsername());
            template.header("X-Auth-User-Role", user.getRole().name());

            if (user.getOrganizationId() != null) {
                template.header("X-Auth-User-Org-Id", String.valueOf(user.getOrganizationId()));
            }
            if (user.getOfficeId() != null) {
                template.header("X-Auth-User-Office-Id", String.valueOf(user.getOfficeId()));
            }
        }
    }
}