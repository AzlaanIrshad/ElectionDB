package com.example.controller;

import com.example.service.UserService;
import com.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/users/{id}")
    public ResponseEntity<User> toggleUserActive(@PathVariable Long id) {
        User updatedUser = userService.toggleActiveStatus(id);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            boolean deleted = userService.deleteUser(id);
            if (deleted) {
                return ResponseEntity.ok().body("User deleted successfully.");
            } else {
                return ResponseEntity.status(404).body("User not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while deleting the user.");
        }
    }

}
