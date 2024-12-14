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
        if (userService.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("Gebruikersnaam is al in gebruik.");
        }
        if (userService.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("E-mailadres is al in gebruik.");
        }
        if (user.getPassword().length() < 6) {
            return ResponseEntity.badRequest().body("Wachtwoord moet minimaal 6 tekens bevatten.");
        }
        if (!user.getPassword().matches(".*[A-Z].*")) {
            return ResponseEntity.badRequest().body("Wachtwoord moet minimaal één hoofdletter bevatten.");
        }
        if (!user.getPassword().matches(".*[0-9!@#$%^&*].*")) {
            return ResponseEntity.badRequest().body("Wachtwoord moet minimaal één cijfer of speciaal teken bevatten.");
        }

        userService.saveUser(user);
        return ResponseEntity.ok("Gebruiker succesvol geregistreerd.");
    }
}
