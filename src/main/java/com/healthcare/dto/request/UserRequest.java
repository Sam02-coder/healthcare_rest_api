package com.healthcare.dto.request;

import com.healthcare.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequest {

	@NotNull
	private String username;
	
	@Email
	private String email;
	
	@NotBlank
	private String password;
	
	private Role role;
	
}
