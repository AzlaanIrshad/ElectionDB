package service;

import entity.User;
import repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (encoder.matches(password, user.get().getPassword())) {
                return user; // Return the logged-in user
            }
        }
        return Optional.empty(); // Login failed
    }

    public User register(String username, String email, String password) {
        User user = new User(username, email, new BCryptPasswordEncoder().encode(password), User.Role.USER);
        return userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void logout(String email) {
        // Implement logout logic if necessary
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
