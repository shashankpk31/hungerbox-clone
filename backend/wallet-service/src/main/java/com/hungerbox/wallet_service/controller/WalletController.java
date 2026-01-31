package com.hungerbox.wallet_service.controller;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hungerbox.wallet_service.dto.response.ApiResponse;
import com.hungerbox.wallet_service.entity.UserWallet;
import com.hungerbox.wallet_service.repository.UserWalletRepository;

@RestController

public class WalletController {
	
	UserWalletRepository userWalletRepository;
	
	WalletController(UserWalletRepository userWalletRepository){
		this.userWalletRepository=userWalletRepository;
	}
	
    @PostMapping("/init/{userId}")
    public ResponseEntity<ApiResponse> initWallet(@PathVariable Long userId) {
        UserWallet wallet = new UserWallet();
        wallet.setUserId(userId);
        wallet.setBalance(BigDecimal.ZERO);
        userWalletRepository.save(wallet);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
