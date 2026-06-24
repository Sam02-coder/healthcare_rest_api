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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(
	    name = "User Management",
	    description = "User CRUD APIs"
	)
public class UserController {
	private final UserService userService;

	@Operation(
		    summary = "Add User",
		    description = "Creates a new user."
		)
	@PostMapping
	public ApiResponse<UserResponse>  addUser(@RequestBody UserRequest request) {
		return new ApiResponse<>(true,
				"User Added Succesfuly",
				userService.addUser(request));
	}

	@Operation(
		    summary = "Get User By ID",
		    description = "Retrieves user details by ID."
		)
	@GetMapping("/{id}")
	public ApiResponse<UserResponse>  getUser(@PathVariable Long id) {
		return new ApiResponse<>(true,
				"User Fetched Succesfully",
				userService.getUser(id));
	}

	@Operation(
		    summary = "Get All Users",
		    description = "Retrieves all users with pagination and sorting."
		)
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
	
	@Operation(
		    summary = "Search Users",
		    description = "Searches users using a keyword."
		)
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
