package com.hungerbox.identity.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.hungerbox.identity.dto.response.ApiResponse;

@FeignClient(name = "VENDOR-SERVICE") 
public interface VendorClient {
    
    @GetMapping("/organization/{id}")
    ResponseEntity<ApiResponse> getOrganizationById(@PathVariable("id") Long id);
}
