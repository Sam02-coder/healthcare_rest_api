package com.healthcare.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.healthcare.dto.request.UserRequest;
import com.healthcare.dto.response.UserResponse;
import com.healthcare.entity.User;
import com.healthcare.exception.DuplicateResourceException;
import com.healthcare.repository.UserRepository;
import com.healthcare.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserResponse register(UserRequest request) {
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new DuplicateResourceException("Email already exists");
		}
		User user = new User();

		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(request.getRole());

		User saved = userRepository.save(user);

		UserResponse response = new UserResponse();
		
		response.setId(saved.getId());
		response.setUsername(saved.getUsername());
		response.setEmail(saved.getEmail());
		response.setRole(saved.getRole());
		
		return response;
	}

}
