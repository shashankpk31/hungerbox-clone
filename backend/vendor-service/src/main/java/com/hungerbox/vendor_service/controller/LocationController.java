package com.hungerbox.vendor_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hungerbox.vendor_service.annotation.RequireRole;
import com.hungerbox.vendor_service.constant.ErrorMessageWarnConstant.Message;
import com.hungerbox.vendor_service.constant.VendorConstantAndEnum.Role;
import com.hungerbox.vendor_service.dto.request.LocationRequest;
import com.hungerbox.vendor_service.dto.request.OfficeRequest;
import com.hungerbox.vendor_service.dto.response.ApiResponse;
import com.hungerbox.vendor_service.service.LocationService;

@RestController
@RequestMapping("/vendors")

public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping("/locations")
    @RequireRole(Role.ROLE_ORG_ADMIN)
    public ResponseEntity<ApiResponse> createLocation(@RequestBody LocationRequest location) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
				new ApiResponse(true, Message.LOC_CREATED.getMessage(), locationService.saveLocation(location)));
    }

    @GetMapping("/locations/org/{orgId}")
    public ResponseEntity<ApiResponse> getLocationsByOrg(@PathVariable Long orgId) {
        return ResponseEntity.status(HttpStatus.OK).body(
				new ApiResponse(true, Message.LOC_FETCHED.getMessage(), locationService.findByOrgId(orgId)));
    }

    @PostMapping("/offices")
    @RequireRole(Role.ROLE_ORG_ADMIN)
    public ResponseEntity<ApiResponse> createOffice(@RequestBody OfficeRequest office) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
				new ApiResponse(true, Message.OFF_CREATED.getMessage(), locationService.createOffice(office)));
    }

    @GetMapping("/offices/location/{locationId}")
    public ResponseEntity<ApiResponse> getOfficesByLocation(@PathVariable Long locationId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
				new ApiResponse(true, Message.OFF_FETCHED.getMessage(), locationService.getOfficesByLocation(locationId)));
    }
}
