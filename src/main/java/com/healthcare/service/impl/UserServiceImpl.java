package com.healthcare.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.healthcare.entity.User;
import com.healthcare.repository.UserRepository;
import com.healthcare.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	@Override
	public User getUser(Long id) {
		return userRepository.findById(id).orElseThrow();
	}

}
