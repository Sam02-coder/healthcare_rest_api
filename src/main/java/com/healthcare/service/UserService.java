package com.healthcare.service;

import java.util.List;

import com.healthcare.dto.request.UserRequest;
import com.healthcare.dto.response.PageResponse;
import com.healthcare.dto.response.UserResponse;

public interface UserService {
	
	UserResponse addUser(UserRequest user);

	PageResponse<UserResponse> getAllUsers(
	        int pageNo,
	        int pageSize,
	        String sortBy,
	        String sortDir);

	List<UserResponse> searchUser(
	        String keyword);

	UserResponse getUser(Long id);
}
