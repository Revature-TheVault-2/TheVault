package com.revature.thevault.repository.entity;

public class PasswordResetRequest {

	private String password;
	private String token;
//	private int fkUserId;
	
	public PasswordResetRequest(String password, String token) {
		super();
		this.password = password;
		this.token = token;
//		this.fkUserId = fkUserId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "PasswordResetRequestModel [password=" + password + ", token=" + token + "]";
	}
	
}
