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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.healthcare.dto.request.LoginRequest;
import com.healthcare.dto.request.UserRequest;
import com.healthcare.dto.response.JwtResponse;
import com.healthcare.dto.response.UserResponse;
import com.healthcare.entity.User;
import com.healthcare.enums.Role;
import com.healthcare.exception.DuplicateResourceException;
import com.healthcare.repository.UserRepository;
import com.healthcare.security.JwtUtil;
import com.healthcare.service.impl.AuthServiceImpl;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void shouldRegisterUserSuccessfully() {

        UserRequest request = new UserRequest();
        request.setUsername("admin");
        request.setEmail("admin@gmail.com");
        request.setPassword("123");
        request.setRole(Role.ADMIN);

        User user = new User();
        user.setId(1L);
        user.setUsername("admin");
        user.setEmail("admin@gmail.com");
        user.setRole(Role.ADMIN);

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode("123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("admin@gmail.com", response.getEmail());
    }

    @Test
    void shouldThrowDuplicateResourceException() {

        UserRequest request = new UserRequest();
        request.setEmail("admin@gmail.com");

        when(userRepository.existsByEmail(request.getEmail()))
                .thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> authService.register(request));
    }

    @Test
    void shouldLoginSuccessfully() {

        LoginRequest request = new LoginRequest();
        request.setEmail("admin@gmail.com");
        request.setPassword("123");

        User user = new User();
        user.setUsername("admin");
        user.setEmail("admin@gmail.com");
        user.setRole(Role.ADMIN);

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(user));

        when(jwtUtil.generateToken(user.getEmail()))
                .thenReturn("jwt-token");

        JwtResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        assertEquals("admin", response.getUsername());
    }

    @Test
    void shouldEncodePassword() {

        when(passwordEncoder.encode("123"))
                .thenReturn("encodedPassword");

        assertEquals("encodedPassword",
                passwordEncoder.encode("123"));
    }

    @Test
    void shouldGenerateJwtToken() {

        when(jwtUtil.generateToken("admin@gmail.com"))
                .thenReturn("jwt-token");

        assertEquals("jwt-token",
                jwtUtil.generateToken("admin@gmail.com"));
    }

}