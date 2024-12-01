package com.example.controller;

import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        logger.info("Received request to fetch all users");
        List<User> users = userService.getUsers();
        logger.info("Fetched {} users", users.size());
        return ResponseEntity.ok(users);
    }

    @PostMapping("/users/{id}")
    public ResponseEntity<User> toggleUserActive(@PathVariable Long id) {
        logger.info("Received request to toggle active status for user with ID: {}", id);
        User updatedUser = userService.toggleActiveStatus(id);

        if (updatedUser != null) {
            logger.info("Successfully toggled active status for user with ID: {}", id);
            return ResponseEntity.ok(updatedUser);
        } else {
            logger.warn("User with ID: {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/users/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("Received request to delete user with ID: {}", id);
        boolean isRemoved = userService.deleteUser(id);

        if (!isRemoved) {
            logger.warn("Failed to delete user with ID: {}. User not found.", id);
            return ResponseEntity.notFound().build();
        }

        logger.info("Successfully deleted user with ID: {}", id);
        return ResponseEntity.ok().build();
    }
}
