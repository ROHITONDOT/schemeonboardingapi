package com.schemeonboardingapi.schemeonboardingapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schemeonboardingapi.schemeonboardingapi.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmpEmail(String empEmail); // Use camelCase
}