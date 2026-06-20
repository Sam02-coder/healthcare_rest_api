package com.healthcare.dto.response;

import com.healthcare.enums.Role;

import lombok.Data;

@Data
public class UserResponse {

	private Long id;
	private String username;
	private String email;
	private Role role;
}
