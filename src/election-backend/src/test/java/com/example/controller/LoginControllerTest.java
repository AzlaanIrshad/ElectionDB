package com.example.controller;

import com.example.service.LoginService;
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
public class LoginControllerTest {

    @Mock
    private LoginService loginService;

    @InjectMocks
    private LoginController loginController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    void testLogin_Success() throws Exception {
        String email = "test@example.com";
        String password = "password123";
        String token = "fake-jwt-token";

        when(loginService.authenticate(email, password)).thenReturn(token);

        mockMvc.perform(post("/login")
                        .contentType("application/json")
                        .content("{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(token));

        verify(loginService, times(1)).authenticate(email, password);
    }

    @Test
    void testLogin_InvalidCredentials() throws Exception {
        String email = "test@example.com";
        String password = "wrongpassword";

        when(loginService.authenticate(email, password)).thenReturn(null);

        mockMvc.perform(post("/login")
                        .contentType("application/json")
                        .content("{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid email or password"));

        verify(loginService, times(1)).authenticate(email, password);
    }

    @Test
    void testLogin_InvalidEmailFormat() throws Exception {
        mockMvc.perform(post("/login")
                        .contentType("application/json")
                        .content("{\"email\":\"invalid-email\",\"password\":\"password123\"}"))
                .andExpect(status().isBadRequest());

        verify(loginService, times(0)).authenticate(anyString(), anyString());
    }

    @Test
    void testLogin_MissingFields() throws Exception {
        mockMvc.perform(post("/login")
                        .contentType("application/json")
                        .content("{}"))
                .andExpect(status().isBadRequest());

        verify(loginService, times(0)).authenticate(anyString(), anyString());
    }
}