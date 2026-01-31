package com.hungerbox.vendor_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hungerbox.vendor_service.annotation.RequireRole;
import com.hungerbox.vendor_service.constant.ErrorMessageWarnConstant.Message;
import com.hungerbox.vendor_service.constant.VendorConstantAndEnum.Role;
import com.hungerbox.vendor_service.dto.request.CafeteriaRequest;
import com.hungerbox.vendor_service.dto.response.ApiResponse;
import com.hungerbox.vendor_service.service.CafeteriaService;

@RestController
@RequestMapping("/vendors/cafeterias")

public class CafeteriaController {

	private final CafeteriaService cafeteriaService;

	public CafeteriaController(CafeteriaService cafeteriaService) {
		this.cafeteriaService = cafeteriaService;
	}

	@PostMapping
	@RequireRole(Role.ROLE_ORG_ADMIN)
	public ResponseEntity<ApiResponse> createCafeteria(@RequestBody CafeteriaRequest cafeteria) {
		return ResponseEntity.status(HttpStatus.CREATED).body(
				new ApiResponse(true, Message.CAF_CREATED.getMessage(), cafeteriaService.createCafeteria(cafeteria)));
	}

	@GetMapping("/office/{officeId}")
	public ResponseEntity<ApiResponse> getByOffice(@PathVariable Long officeId) {
		return ResponseEntity.status(HttpStatus.OK).body(
				new ApiResponse(true, Message.CAF_FETCHED.getMessage(), cafeteriaService.findByOfficeId(officeId)));
	}

	@PutMapping("/{id}/status")
	@RequireRole(Role.ROLE_ORG_ADMIN)
	public ResponseEntity<ApiResponse> toggleStatus(@PathVariable Long id, @RequestParam Boolean active) {
		return ResponseEntity.status(HttpStatus.OK).body(
				new ApiResponse(true, Message.CAF_UPDATED.getMessage(), cafeteriaService.updateStatus(id, active)));
	}
}
