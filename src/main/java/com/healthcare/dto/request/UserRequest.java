package com.healthcare.dto.request;

import com.healthcare.enums.Role;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequest {

	@NotNull
	private String username;
	
	@NotBlank
	private String email;
	
	@NotBlank
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
}
