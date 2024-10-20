package com.example.service;

import com.example.entity.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    public User login(String email, String password) {
        System.out.println("Logging in user: " + email);
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            System.out.println("User found, checking password.");
//            if (passwordEncoder.matches(password, user.getPassword())) {
//                System.out.println("Password matches.");
//                return user;
//            } else {
//                System.out.println("Password does not match.");
//            }
        } else {
            System.out.println("User not found.");
        }
        return null;
    }

    public User register(String email, String password, String username) {
        if (userRepository.existsByEmail(email)) {
            return null; // Email already in use
        }

        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
//        user.setPassword(passwordEncoder.encode(password)); // Hash the password
        return userRepository.save(user);
    }

    public void logout(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        userOptional.ifPresent(user -> {
            user.setActive(false);
            userRepository.save(user);
        });
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User deactivateUser(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setActive(false);
            return userRepository.save(user);
        }
        return null;
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true; // User deleted successfully
        }
        return false; // User not found
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

//    // Method to promote a user to admin
//    public User promoteUserToAdmin(Long userId, Long adminId) {
//        User admin = userRepository.findById(adminId)
//                .orElseThrow(() -> new RuntimeException("Admin not found")); // Handle admin not found
//
//        // Ensure the user performing the action is an admin
//        if (!admin.isAdmin()) { // Assuming isAdmin() method exists in User
//            throw new RuntimeException("Only admins can promote users.");
//        }
//
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found")); // Handle user not found
//
//        user.setRole(Role.ADMIN); // Assuming setRole() method exists in User
//        return userRepository.save(user); // Save and return the updated user
//    }
}
