package com.example.controller;

import com.example.entity.User;
import com.example.service.RegisterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(MockitoExtension.class)
public class RegisterControllerTest {

    @Mock
    private RegisterService registerService;

    @InjectMocks
    private RegisterController registerController;

    private MockMvc mockMvc;

    private User validUser;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(registerController).build();

        validUser = new User();
        validUser.setUsername("testuser");
        validUser.setEmail("test@example.com");
        validUser.setPassword("Password1!");
    }

    @Test
    void testRegister_Success() throws Exception {
        when(registerService.registerUser(any(User.class))).thenReturn("Gebruiker succesvol geregistreerd.");

        mockMvc.perform(post("/register")
                        .contentType("application/json")
                        .content("{\"username\":\"testuser\",\"email\":\"test@example.com\",\"password\":\"Password1!\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Gebruiker succesvol geregistreerd."));

        verify(registerService, times(1)).registerUser(any(User.class));
    }

    @Test
    void testRegister_Failure() throws Exception {
        when(registerService.registerUser(any(User.class))).thenReturn("E-mailadres is al in gebruik.");

        mockMvc.perform(post("/register")
                        .contentType("application/json")
                        .content("{\"username\":\"testuser\",\"email\":\"test@example.com\",\"password\":\"Password1!\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("E-mailadres is al in gebruik."));

        verify(registerService, times(1)).registerUser(any(User.class));
    }

    @Test
    void testRegister_InvalidRequest() throws Exception {
        mockMvc.perform(post("/register")
                        .contentType("application/json")
                        .content("{\"username\":\"\",\"email\":\"\",\"password\":\"\"}"))
                .andExpect(status().isBadRequest());

        verify(registerService, times(0)).registerUser(any(User.class));
    }
}