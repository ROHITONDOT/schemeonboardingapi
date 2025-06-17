package com.schemeonboardingapi.schemeonboardingapi.Controller;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
	 public String getEmpemail() {
		return empemail;
	}

	public void setEmpemail(String emp_email) {
		this.empemail = emp_email;
	}

	public String getUser_password() {
		return userpassword;
	}

	public void setUser_password(String user_password) {
		this.userpassword = user_password;
	}

	@NotBlank
	    private String empemail;

	    @NotBlank
	    private String userpassword;

	   
}