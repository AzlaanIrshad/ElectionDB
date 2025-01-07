package com.example.service;

import com.example.entity.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String registerUser(User user) {
        if (existsByUsername(user.getUsername())) {
            return "Gebruikersnaam is al in gebruik.";
        }

        if (existsByEmail(user.getEmail())) {
            return "E-mailadres is al in gebruik.";
        }

        if (user.getPassword().length() < 6) {
            return "Wachtwoord moet minimaal 6 tekens bevatten.";
        }

        if (!user.getPassword().matches(".*[A-Z].*")) {
            return "Wachtwoord moet minimaal één hoofdletter bevatten.";
        }

        if (!user.getPassword().matches(".*[0-9!@#$%^&*].*")) {
            return "Wachtwoord moet minimaal één cijfer of speciaal teken bevatten.";
        }

        saveUser(user);
        return "Gebruiker succesvol geregistreerd.";
    }

    private boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    private void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
