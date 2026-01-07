package com.hungerbox.identity.dto.response;

import com.hungerbox.identity.entity.Role;

public class UserResponse {
	private String username;
	private String email;
	private Role role;
	public UserResponse() {
		super();
	}
	public UserResponse(String username, String email, Role role) {
		super();
		this.username = username;
		this.email = email;
		this.role = role;
	}
	public String getUsername() {
		return username;
	}
	public String getEmail() {
		return email;
	}
	public Role getRole() {
		return role;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	
}
