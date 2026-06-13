package com.healthcare.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.entity.User;
import com.healthcare.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
	private final UserService userService;

	@GetMapping
	public List<User> getAllUsers() {
		return userService.getAllUser();
	}

	@GetMapping("/{id}")
	public User getUser(@PathVariable Long id) {
		return userService.getUser(id);
	}

	@PostMapping
	public User save(@RequestBody User user) {
		return userService.save(user);
	}
}
