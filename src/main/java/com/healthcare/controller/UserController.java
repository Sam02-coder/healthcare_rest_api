package com.healthcare.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.dto.request.UserRequest;
import com.healthcare.dto.response.ApiResponse;
import com.healthcare.dto.response.PageResponse;
import com.healthcare.dto.response.UserResponse;
import com.healthcare.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
	private final UserService userService;

	@PostMapping
	public ApiResponse<UserResponse>  addUser(@RequestBody UserRequest request) {
		return new ApiResponse<>(true,
				"User Added Succesfuly",
				userService.addUser(request));
	}

	@GetMapping("/{id}")
	public ApiResponse<UserResponse>  getUser(@PathVariable Long id) {
		return new ApiResponse<>(true,
				"User Fetched Succesfully",
				userService.getUser(id));
	}

	@GetMapping
	public ApiResponse<PageResponse<UserResponse>> getAllUsers(

	        @RequestParam(defaultValue = "0")
	        int page,
	        @RequestParam(defaultValue = "5")
	        int size,
	        @RequestParam(defaultValue = "username")
	        String sortBy,
	        @RequestParam(defaultValue = "asc")
	        String sortDir) {

	    return new ApiResponse<>(
	            true,
	            "Users Fetched Successfully",
	            userService.getAllUsers(
	                    page,
	                    size,
	                    sortBy,
	                    sortDir)
	    );
	}
	
	@GetMapping("/search")
	public ApiResponse<List<UserResponse>> searchUser(
	        @RequestParam String keyword) {

	    return new ApiResponse<>(
	            true,
	            "Users Found Successfully",
	            userService.searchUser(keyword)
	    );
	}
}
