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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setUsername("testuser");
        user.setPassword(new BCryptPasswordEncoder().encode("Password123"));
        user.setActive(true);
    }

    @Test
    void testAuthenticateSuccess() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(jwtUtil.generateToken(anyString(), anyString())).thenReturn("mockToken");

        String token = userService.authenticate("test@example.com", "Password123");

        assertNotNull(token);
        assertEquals("mockToken", token);
        verify(jwtUtil, times(1)).generateToken(user.getEmail(), user.getRole().name());
    }

    @Test
    void testAuthenticateInvalidPassword() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        String token = userService.authenticate("test@example.com", "WrongPassword");

        assertNull(token);
        verify(jwtUtil, never()).generateToken(anyString(), anyString());
    }

    @Test
    void testAuthenticateUserNotFound() {
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        String token = userService.authenticate("nonexistent@example.com", "Password123");

        assertNull(token);
        verify(jwtUtil, never()).generateToken(anyString(), anyString());
    }

    @Test
    void testSaveUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.saveUser(user);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testExistsByEmail() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        boolean exists = userService.existsByEmail("test@example.com");

        assertTrue(exists);
        verify(userRepository, times(1)).existsByEmail("test@example.com");
    }

    @Test
    void testExistsByUsername() {
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        boolean exists = userService.existsByUsername("testuser");

        assertTrue(exists);
        verify(userRepository, times(1)).existsByUsername("testuser");
    }

    @Test
    void testToggleActiveStatus() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User updatedUser = userService.toggleActiveStatus(1L);

        assertNotNull(updatedUser);
        assertFalse(updatedUser.getActive());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testToggleActiveStatusUserNotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        User updatedUser = userService.toggleActiveStatus(2L);

        assertNull(updatedUser);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testDeleteUser() {
        when(userRepository.existsById(1L)).thenReturn(true);

        boolean result = userService.deleteUser(1L);

        assertTrue(result);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUserNotFound() {
        when(userRepository.existsById(2L)).thenReturn(false);

        boolean result = userService.deleteUser(2L);

        assertFalse(result);
        verify(userRepository, never()).deleteById(anyLong());
    }
}
