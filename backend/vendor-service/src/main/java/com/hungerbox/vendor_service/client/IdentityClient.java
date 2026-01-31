package com.hungerbox.vendor_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hungerbox.vendor_service.dto.request.RegisterOrgRequest;
import com.hungerbox.vendor_service.dto.response.ApiResponse;


@FeignClient(name = "IDENTITY-SERVICE") 
public interface IdentityClient {
	@PostMapping("/auth/register/org-admin")
	public ResponseEntity<ApiResponse> registerOrgAdmin(@RequestBody RegisterOrgRequest user);
}
