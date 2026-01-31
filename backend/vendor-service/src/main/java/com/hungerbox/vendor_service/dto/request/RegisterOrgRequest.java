package com.hungerbox.vendor_service.dto.request;

import com.hungerbox.vendor_service.constant.VendorConstantAndEnum.Role;

public record RegisterOrgRequest(String username, String email, String password, Role role, String phoneNumber,
		Long organizationId, String employeeId, Long officeId, String shopName, String gstNumber) {
	public RegisterOrgRequest {
		if (role == null) {
			role = Role.ROLE_ORG_ADMIN;
		}
	}

}
