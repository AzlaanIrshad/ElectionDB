package com.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.service.UserService;
import com.example.entity.User;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData, HttpServletRequest request) {
        try {
            String email = loginData.get("email");
            String password = loginData.get("password");
            System.out.println("Attempting login for email: " + email);
            
            User user = userService.login(email, password);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("userId", user.getId());
                return ResponseEntity.ok(new LoginResponse(session.getId()));
            }
            
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam String email, @RequestParam String password, @RequestParam String username) {
        return ResponseEntity.ok(userService.register(email, password, username));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logged out successfully");
    }

    class LoginResponse {
        private String sessionId;
    
        public LoginResponse(String sessionId) {
            this.sessionId = sessionId;
        }
    
        public String getSessionId() {
            return sessionId;
        }
    }
}