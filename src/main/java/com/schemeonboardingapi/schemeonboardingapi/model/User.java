package com.schemeonboardingapi.schemeonboardingapi.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import jakarta.validation.constraints.*;

@Entity
@Table(name = "mas_admin_users", schema = "bms_user") // Correct schema and table name
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Email
    @Column(name = "emp_email", unique = true) // Mapping snake_case to camelCase
    private String empEmail;

    @NotBlank
    @Size(min = 6)
    @Column(name = "user_password") // Ensure DB column matches
    private String userPassword;

    @NotNull
    @Column(name = "user_role") // Ensure DB column matches
    private Integer userRole; // Changed from `int` to `Integer` (avoids default value issues)

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }
}
