package com.hungerbox.identity.entity;

import com.hungerbox.identity.constant.IdentityConstantAndEnum.Role;
import com.hungerbox.identity.constant.IdentityConstantAndEnum.USER_ACC_STAT;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "users")
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String username;

	@Column(unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;

	@Column(nullable = true)
	private String employeeId;

	@Column(nullable = true)
	private Long organizationId;

	@Column(nullable = true)
	private String shopName;
	@Column(nullable = true)
	private String gstNumber;
	@Column(nullable = true)
	private String phoneNumber;

	@Column(nullable = true)
	private Boolean isEmailVerified;

	@Column(nullable = true)
	private Boolean isPhoneVerified;

	@Column(nullable = true)
	private Long officeId;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = true)
	private USER_ACC_STAT status;
	
	@Column(nullable = true)
	private Boolean isProfileComplete;
	
	public User() {
		super();
	}

	public User(Long id, String username, String email, String password, Role role, String employeeId,
			Long organizationId, String shopName, String gstNumber, String phoneNumber, Boolean isEmailVerified,
			Boolean isPhoneVerified, Long officeId, USER_ACC_STAT status, Boolean isProfileComplete) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
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



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
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
