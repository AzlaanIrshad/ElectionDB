package com.example.controller;

import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        if (userService.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already taken");
        }
        if (user.getPassword().length() < 6) {
            return ResponseEntity.badRequest().body("Password must be at least 6 characters long");
        }

        userService.saveUser(user);
        return ResponseEntity.ok("User registered successfully");
    }
}
