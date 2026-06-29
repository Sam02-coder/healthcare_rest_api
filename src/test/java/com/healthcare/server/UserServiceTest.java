package com.healthcare.server;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.healthcare.dto.request.UserRequest;
import com.healthcare.dto.response.UserResponse;
import com.healthcare.entity.User;
import com.healthcare.enums.Role;
import com.healthcare.exception.DuplicateResourceException;
import com.healthcare.exception.ResourceNotFoundException;
import com.healthcare.repository.UserRepository;
import com.healthcare.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldAddUserSuccessfully() {

        UserRequest request = new UserRequest();
        request.setUsername("admin");
        request.setEmail("admin@gmail.com");
        request.setPassword("admin123");
        request.setRole(Role.ADMIN);

        User user = new User();
        user.setId(1L);
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponse response = userService.addUser(request);

        assertNotNull(response);
        assertEquals("admin", response.getUsername());
        assertEquals("admin@gmail.com", response.getEmail());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldThrowDuplicateResourceException() {

        UserRequest request = new UserRequest();
        request.setEmail("admin@gmail.com");

        when(userRepository.existsByEmail(request.getEmail()))
                .thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> userService.addUser(request));
    }

    @Test
    void shouldGetUserSuccessfully() {

        User user = new User();
        user.setId(1L);
        user.setUsername("admin");
        user.setEmail("admin@gmail.com");
        user.setRole(Role.ADMIN);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        UserResponse response = userService.getUser(1L);

        assertEquals("admin", response.getUsername());
    }

    @Test
    void shouldThrowResourceNotFoundException() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.getUser(1L));
    }

}