package com.healthcare.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.dto.request.UserRequest;
import com.healthcare.dto.response.UserResponse;
import com.healthcare.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;

	@PostMapping("register")
	public UserResponse register(@Valid @RequestBody UserRequest request) {
		return authService.register(request);
	}
}
