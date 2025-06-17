package com.schemeonboardingapi.schemeonboardingapi.Controller;


public class AuthResponse {
	 private String token;
	    private int user_role;

	    // ✅ Correct Constructor
	    public AuthResponse(String token, int user_role) {
	        this.token = token;
	        this.user_role = user_role;
	    }

	    // Getters
	    public String getToken() {
	        return token;
	    }

	    public int getuser_role() {
	        return user_role;
	    }
}
