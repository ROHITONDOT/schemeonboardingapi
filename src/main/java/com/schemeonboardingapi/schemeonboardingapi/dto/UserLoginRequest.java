package com.schemeonboardingapi.schemeonboardingapi.dto;

import jakarta.validation.constraints.NotBlank;

public class UserLoginRequest {
	 public String getEmp_email() {
		return emp_email;
	}

	public void setEmp_email(String emp_email) {
		this.emp_email = emp_email;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	@NotBlank(message = "Email is required")
	    private String emp_email;

	    @NotBlank(message = "Password is required")
	    private String user_password;


}
