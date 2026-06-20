package com.healthcare.mapper;

import com.healthcare.dto.response.UserResponse;
import com.healthcare.entity.User;

public class UserMapper {

	public static UserResponse map(User user) {
		UserResponse response = new UserResponse();

		response.setId(user.getId());
		response.setUsername(user.getUsername());
		response.setEmail(user.getEmail());
		response.setRole(user.getRole());

		return response;

	}
}
