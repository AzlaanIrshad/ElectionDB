package com.example.service;

import com.example.entity.User;
import com.example.repository.UserRepository;
import com.example.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private LoginService loginService;

    private User dummyUser;

    @BeforeEach
    void setUp() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        dummyUser = new User();
        dummyUser.setEmail("test@example.com");
        dummyUser.setPassword(passwordEncoder.encode("password123"));
        dummyUser.setRole(User.Role.USER);
        dummyUser.setId(1L);
    }

    @Test
    void testAuthenticate_Success() {
        when(userRepository.findByEmail(dummyUser.getEmail())).thenReturn(Optional.of(dummyUser));
        when(jwtUtil.generateToken(dummyUser.getEmail(), dummyUser.getRole().name(), String.valueOf(dummyUser.getId())))
                .thenReturn("fake-jwt-token");

        String token = loginService.authenticate(dummyUser.getEmail(), "password123");

        assertNotNull(token);
        assertEquals("fake-jwt-token", token);
        verify(userRepository, times(1)).findByEmail(dummyUser.getEmail());
        verify(jwtUtil, times(1)).generateToken(dummyUser.getEmail(), dummyUser.getRole().name(), String.valueOf(dummyUser.getId()));
    }

    @Test
    void testAuthenticate_InvalidPassword() {
        when(userRepository.findByEmail(dummyUser.getEmail())).thenReturn(Optional.of(dummyUser));

        String token = loginService.authenticate(dummyUser.getEmail(), "wrongpassword");

        assertNull(token);
        verify(userRepository, times(1)).findByEmail(dummyUser.getEmail());
        verify(jwtUtil, times(0)).generateToken(anyString(), anyString(), anyString());
    }

    @Test
    void testAuthenticate_UserNotFound() {
        when(userRepository.findByEmail(dummyUser.getEmail())).thenReturn(Optional.empty());

        String token = loginService.authenticate(dummyUser.getEmail(), "password123");

        assertNull(token);
        verify(userRepository, times(1)).findByEmail(dummyUser.getEmail());
        verify(jwtUtil, times(0)).generateToken(anyString(), anyString(), anyString());
    }
}