package com.hungerbox.vendor_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hungerbox.vendor_service.annotation.RequireRole;
import com.hungerbox.vendor_service.client.IdentityClient;
import com.hungerbox.vendor_service.constant.ErrorMessageWarnConstant.Message;
import com.hungerbox.vendor_service.constant.VendorConstantAndEnum.Role;
import com.hungerbox.vendor_service.dto.request.OrgAndAdmCreate;
import com.hungerbox.vendor_service.dto.request.OrganizationRequest;
import com.hungerbox.vendor_service.dto.request.RegisterOrgRequest;
import com.hungerbox.vendor_service.dto.response.ApiResponse;
import com.hungerbox.vendor_service.dto.response.OrganizationResponse;
import com.hungerbox.vendor_service.service.OrganizationService;

@RestController
@RequestMapping("/organization")
public class OrganizationController {
	
	private static final Logger log = LoggerFactory.getLogger(OrganizationController.class);

	private final OrganizationService organizationService;
	private final IdentityClient identityClient;

	public OrganizationController(OrganizationService organizationService,IdentityClient identityClient) {
		this.organizationService = organizationService;
		this.identityClient=identityClient;
	}

	@PostMapping
	@RequireRole(Role.ROLE_SUPER_ADMIN)
	public ResponseEntity<ApiResponse> createOrg(@RequestBody OrgAndAdmCreate req) {

	    OrganizationResponse orgRes = organizationService.createOrganization(
	        new OrganizationRequest(req.name(), req.domain())
	    );

	    try {
	        identityClient.registerOrgAdmin(new RegisterOrgRequest(
	            req.username(), req.email(), req.password(), null,
	            req.phoneNumber(), orgRes.getId(), req.employeeId(),
	            req.officeId(), req.shopName(), req.gstNumber()
	        ));
	        
	        return ResponseEntity.status(HttpStatus.CREATED)
	                .body(new ApiResponse(true, "Organization and Admin created successfully", orgRes));

	    } catch (Exception e) {
	    	log.error("Admin creation failed for org {}. Rolling back...", orgRes.getId());
	        organizationService.deleteOrganization(orgRes.getId()); 
	        
	        throw new RuntimeException("Failed to setup Admin: " + e.getMessage());
	    }
	}

	@GetMapping
	public ResponseEntity<ApiResponse> getAllOrgs() {
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse(true, Message.ORG_FETCHED.getMessage(), organizationService.findAllOrganisations()));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse> getOrganizationById(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.CREATED).body(
				new ApiResponse(true, Message.ORG_FETCHED.getMessage(), organizationService.findOrganizationById(id)));
	}
}
