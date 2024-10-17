package service;

import entity.User;
import main.java.exception.UserAlreadyExistsException;
import repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import exception.ResourceNotFoundException;
import exception.UnauthorizedActionException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User login(String email, String password) {
        System.out.println("Logging in user: " + email);
        User user = userRepository.findByEmail(email);
        if (user != null) {
            System.out.println("User found, checking password.");
            if (passwordEncoder.matches(password, user.getPassword())) {
                System.out.println("Password matches.");
                return user;
            } else {
                System.out.println("Password does not match.");
            }
        } else {
            System.out.println("User not found.");
        }
        return null;
    }

    public User register(String email, String password, String username) {
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("Email already in use");
        }

        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // Hash
        return userRepository.save(user);
    }

    public void logout(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setActive(false);
            userRepository.save(user);
        }
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void deactivateUser(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        userOptional.ifPresent(user -> {
            user.setActive(false);
            userRepository.save(user);
        });
    }

    public User promoteUserToAdmin(Long userId, Long adminId) {
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));

        if (admin.getRole() != Role.ADMIN) {
            throw new UnauthorizedActionException("Only admins can promote users.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setRole(Role.ADMIN);
        return userRepository.save(user);
    }
}
