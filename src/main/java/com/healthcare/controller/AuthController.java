package com.healthcare.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.dto.request.LoginRequest;
import com.healthcare.dto.request.UserRequest;
import com.healthcare.dto.response.JwtResponse;
import com.healthcare.dto.response.UserResponse;
import com.healthcare.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(
	    name = "Authentication",
	    description = "Authentication APIs"
	)
public class AuthController {
	
	private final AuthService authService;

	@Operation(
		    summary = "Register User",
		    description = "Registers a new user account."
		)
	@PostMapping("register")
	public UserResponse register(
			@Valid
			@RequestBody UserRequest request) {
		return authService.register(request);
	}
	
	@Operation(
		    summary = "User Login",
		    description = "Authenticates user and returns JWT token."
		)
	@PostMapping("/login")
	public JwtResponse login(
			@Valid
			@RequestBody
			LoginRequest loginRequest) {
		return authService.login(loginRequest);
	}
}
