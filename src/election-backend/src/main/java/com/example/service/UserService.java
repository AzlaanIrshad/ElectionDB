package com.example.service;

import com.example.entity.User;
import com.example.repository.UserRepository;
import com.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String authenticate(String email, String password) {
        logger.info("Attempting authentication for email: {}", email);

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                logger.info("Authentication successful for email: {}", email);
                return jwtUtil.generateToken(user.getEmail(), user.getRole().name());
            } else {
                logger.warn("Authentication failed for email: {}. Incorrect password.", email);
            }
        } else {
            logger.warn("Authentication failed for email: {}. User not found.", email);
        }
        return null;
    }

    public void saveUser(User user) {
        logger.info("Saving new user with email: {}", user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }

    public List<User> getUsers() {
        logger.info("Fetching all users from the database");
        return userRepository.findAll();
    }

    public boolean existsByEmail(String email) {
        boolean exists = userRepository.existsByEmail(email);
        logger.info("Checked if email exists: {} - Result: {}", email, exists);
        return exists;
    }

    public boolean existsByUsername(String username) {
        boolean exists = userRepository.existsByUsername(username);
        logger.info("Checked if username exists: {} - Result: {}", username, exists);
        return exists;
    }

    public User toggleActiveStatus(Long id) {
        logger.info("Attempting to toggle active status for user with ID: {}", id);
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setActive(!user.getActive());
            userRepository.save(user);
            logger.info("Active status toggled successfully for user with ID: {}", id);
            return user;
        } else {
            logger.warn("User with ID: {} not found", id);
            return null;
        }
    }

    public boolean deleteUser(Long id) {
        logger.info("Attempting to delete user with ID: {}", id);

        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            logger.info("User with ID: {} deleted successfully", id);
            return true;
        } else {
            logger.warn("User with ID: {} not found. Cannot delete.", id);
            return false;
        }
    }
}
