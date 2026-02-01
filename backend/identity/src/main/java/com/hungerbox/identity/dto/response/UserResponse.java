package com.hungerbox.identity.dto.response;

import com.hungerbox.identity.constant.IdentityConstantAndEnum.Role;
import com.hungerbox.identity.constant.IdentityConstantAndEnum.USER_ACC_STAT;


public class UserResponse {
	private String username;
	private String email;
	private Role role;
	private String employeeId;
	private Long organizationId;
	private String shopName;
	private String gstNumber;
	private String phoneNumber;
	private Boolean isEmailVerified;
	private Boolean isPhoneVerified;
	private Long officeId;
	private USER_ACC_STAT status;	
	private Boolean isProfileComplete;

	public UserResponse() {
		super();
	}

	public UserResponse(String username, String email, Role role, String employeeId, Long organizationId,
			String shopName, String gstNumber, String phoneNumber, Boolean isEmailVerified, Boolean isPhoneVerified,
			Long officeId, USER_ACC_STAT status, Boolean isProfileComplete) {
		super();
		this.username = username;
		this.email = email;
		this.role = role;
		this.employeeId = employeeId;
		this.organizationId = organizationId;
		this.shopName = shopName;
		this.gstNumber = gstNumber;
		this.phoneNumber = phoneNumber;
		this.isEmailVerified = isEmailVerified;
		this.isPhoneVerified = isPhoneVerified;
		this.officeId = officeId;
		this.status = status;
		this.isProfileComplete = isProfileComplete;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getGstNumber() {
		return gstNumber;
	}

	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Boolean getIsEmailVerified() {
		return isEmailVerified;
	}

	public void setIsEmailVerified(Boolean isEmailVerified) {
		this.isEmailVerified = isEmailVerified;
	}

	public Boolean getIsPhoneVerified() {
		return isPhoneVerified;
	}

	public void setIsPhoneVerified(Boolean isPhoneVerified) {
		this.isPhoneVerified = isPhoneVerified;
	}

	public Long getOfficeId() {
		return officeId;
	}

	public void setOfficeId(Long officeId) {
		this.officeId = officeId;
	}

	public USER_ACC_STAT getStatus() {
		return status;
	}

	public void setStatus(USER_ACC_STAT status) {
		this.status = status;
	}

	public Boolean getIsProfileComplete() {
		return isProfileComplete;
	}

	public void setIsProfileComplete(Boolean isProfileComplete) {
		this.isProfileComplete = isProfileComplete;
	}
	
}
