package com.hungerbox.identity.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.hungerbox.identity.dto.response.ApiResponse;

@FeignClient(name = "WALLET-SERVICE")
public interface WalletClient {
	
    @PostMapping("/init/{userId}")
    public ResponseEntity<ApiResponse> initWallet(@PathVariable Long userId);
}
