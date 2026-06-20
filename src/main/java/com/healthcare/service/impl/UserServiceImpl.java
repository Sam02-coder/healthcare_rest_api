package com.healthcare.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.healthcare.dto.request.UserRequest;
import com.healthcare.dto.response.PageResponse;
import com.healthcare.dto.response.UserResponse;
import com.healthcare.entity.User;
import com.healthcare.exception.DuplicateResourceException;
import com.healthcare.exception.ResourceNotFoundException;
import com.healthcare.mapper.UserMapper;
import com.healthcare.repository.UserRepository;
import com.healthcare.service.UserService;
import com.healthcare.util.AppConstants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Override
	public UserResponse addUser(UserRequest request) {
		
		if(userRepository.existsByEmail(request.getEmail())) {
			throw new DuplicateResourceException("Email already exists");
		}
		User user=new User();
		
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword());
		user.setRole(request.getRole());
		
		User savedUser=userRepository.save(user);
		return UserMapper.map(savedUser);
	}

	@Override
	public PageResponse<UserResponse> getAllUsers(
	        int pageNo,
	        int pageSize,
	        String sortBy,
	        String sortDir) {

	    Sort sort = sortDir.equalsIgnoreCase("asc")
	            ? Sort.by(sortBy).ascending()
	            : Sort.by(sortBy).descending();

	    Pageable pageable =
	            PageRequest.of(
	                    pageNo,
	                    pageSize,
	                    sort);

	    Page<User> page =
	            userRepository.findAll(pageable);

	    List<UserResponse> responses =
	            page.getContent()
	                    .stream()
	                    .map(UserMapper::map)
	                    .toList();

	    PageResponse<UserResponse> response =
	            new PageResponse<>();

	    response.setContent(responses);
	    response.setPageNo(page.getNumber());
	    response.setPageSize(page.getSize());
	    response.setTotalElements(page.getTotalElements());
	    response.setTotalPages(page.getTotalPages());
	    response.setLast(page.isLast());

	    return response;
	}

	@Override
	public UserResponse getUser(Long id) {
		User user=userRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND));
		return UserMapper.map(user);
	}
	
	@Override
	public List<UserResponse> searchUser(
	        String keyword) {

	    return userRepository
	            .findByUsernameContainingIgnoreCase(
	                    keyword)
	            .stream()
	            .map(UserMapper::map)
	            .toList();
	}
}
