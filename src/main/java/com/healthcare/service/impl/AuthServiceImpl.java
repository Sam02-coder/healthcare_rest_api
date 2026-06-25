package com.healthcare.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.healthcare.dto.request.LoginRequest;
import com.healthcare.dto.request.UserRequest;
import com.healthcare.dto.response.JwtResponse;
import com.healthcare.dto.response.UserResponse;
import com.healthcare.entity.User;
import com.healthcare.exception.DuplicateResourceException;
import com.healthcare.repository.UserRepository;
import com.healthcare.security.JwtUtil;
import com.healthcare.service.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserResponse register(UserRequest request) {
		
		log.info("User registration stated for email: {}", request.getEmail());
		if (userRepository.existsByEmail(request.getEmail())) {
			
			log.warn("Registration failed. Email already exists: {}", request.getEmail());
			
			throw new DuplicateResourceException("Email already exists");
		}
		User user = new User();

		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(request.getRole());

		User saved = userRepository.save(user);
		
		log.info("User registered succesfully with ID: {}", saved.getId());

		UserResponse response = new UserResponse();
		
		response.setId(saved.getId());
		response.setUsername(saved.getUsername());
		response.setEmail(saved.getEmail());
		response.setRole(saved.getRole());
				
		return response;
	}

	@Override
	public JwtResponse login(LoginRequest loginRequest) {
		
		log.info("Login attempt for email: {}", loginRequest.getEmail());
		
		authenticationManager
		.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginRequest.getEmail(),
						loginRequest.getPassword()));
		
		User user=userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();
		
		log.info("User logged in successfully: {}", user.getEmail());
		
		String token=jwtUtil.generateToken(user.getEmail());
		
		JwtResponse jwtResponse=new JwtResponse();
		
		jwtResponse.setToken(token);
		jwtResponse.setUsername(user.getUsername());
		jwtResponse.setRole(user.getRole().name());
		
		log.debug("JWT token generated for user: {}", user.getEmail());
		
		return jwtResponse;
	}

}
