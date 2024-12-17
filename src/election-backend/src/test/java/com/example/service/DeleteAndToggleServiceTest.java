package com.example.service;

import com.example.entity.User;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteAndToggleServiceTest  {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToggleActiveStatus_UserExists() {

        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setActive(false);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updatedUser = userService.toggleActiveStatus(userId);

        assertNotNull(updatedUser);
        assertTrue(updatedUser.getActive());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testToggleActiveStatus_UserDoesNotExist() {

        Long userId = 2L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        User result = userService.toggleActiveStatus(userId);

        assertNull(result);
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(any());
    }

    @Test
    void testDeleteUser_UserExists() {

        Long userId = 3L;
        when(userRepository.existsById(userId)).thenReturn(true);

        boolean result = userService.deleteUser(userId);

        assertTrue(result);
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testDeleteUser_UserDoesNotExist() {

        Long userId = 4L;
        when(userRepository.existsById(userId)).thenReturn(false);

        boolean result = userService.deleteUser(userId);

        assertFalse(result);
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, never()).deleteById(any());
    }
}
