package com.hungerbox.identity.dto.response;

public class LoginResponse{
	UserResponse user;
	String token;
	
	public LoginResponse() {
		super();
	}
	
	public LoginResponse(UserResponse user, String token) {
		super();
		this.user = user;
		this.token = token;
	}


	public UserResponse getUser() {
		return user;
	}
	public void setUser(UserResponse user) {
		this.user = user;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
