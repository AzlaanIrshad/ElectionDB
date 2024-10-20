package com.example.service;

import com.example.entity.User;
import com.example.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    private final String secretKey = "mySecretKey"; // Store securely in a real application

    public String login(String email, String password) {
        System.out.println("Attempting login for: " + email); // Log the email for debugging
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            System.out.println("User found: " + user.getEmail()); // Log if user is found
            // Compare passwords
            if (password.equals(user.getPassword())) {
                // Token generation
                return generateToken(user); // Encapsulate token generation in a method
            }
        }
        return null; // Return null if login fails
    }

    private String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 864_000_00)) // 1 day validity
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}
