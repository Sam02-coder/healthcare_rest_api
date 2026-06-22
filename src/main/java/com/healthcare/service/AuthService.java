package com.healthcare.service;

import com.healthcare.dto.request.UserRequest;
import com.healthcare.dto.response.UserResponse;

public interface AuthService {

	UserResponse register(UserRequest request);
}
