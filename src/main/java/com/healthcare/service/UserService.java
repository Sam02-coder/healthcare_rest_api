package com.healthcare.service;

import java.util.List;

import com.healthcare.entity.User;

public interface UserService {
	User save(User user);

	List<User> getAllUser();

	User getUser(Long id);
}
