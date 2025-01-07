package com.example.service;

import com.example.entity.User;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RegisterServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RegisterService registerService;

    private User validUser;

    @BeforeEach
    void setUp() {
        validUser = new User();
        validUser.setUsername("testuser");
        validUser.setEmail("test@example.com");
        validUser.setPassword("Password1!");
    }

    @Test
    void testRegisterUser_UsernameAlreadyExists() {
        when(userRepository.existsByUsername(validUser.getUsername())).thenReturn(true);

        String result = registerService.registerUser(validUser);

        assertEquals("Gebruikersnaam is al in gebruik.", result);
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testRegisterUser_EmailAlreadyExists() {
        when(userRepository.existsByUsername(validUser.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(validUser.getEmail())).thenReturn(true);

        String result = registerService.registerUser(validUser);

        assertEquals("E-mailadres is al in gebruik.", result);
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testRegisterUser_PasswordTooShort() {
        validUser.setPassword("short");

        String result = registerService.registerUser(validUser);

        assertEquals("Wachtwoord moet minimaal 6 tekens bevatten.", result);
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testRegisterUser_PasswordMissingUppercase() {
        validUser.setPassword("password1!");

        String result = registerService.registerUser(validUser);

        assertEquals("Wachtwoord moet minimaal één hoofdletter bevatten.", result);
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testRegisterUser_PasswordMissingSpecialChar() {
        validUser.setPassword("PasswordZonderSpeciaalTeken");

        String result = registerService.registerUser(validUser);

        assertEquals("Wachtwoord moet minimaal één cijfer of speciaal teken bevatten.", result);
        verify(userRepository, times(0)).save(any(User.class));
    }
}