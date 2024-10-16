package service;

import entity.User;
import repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUser(String email) {
        return userRepository.findByEmail(email);
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public User register(String email, String password, String username) {
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
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
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setActive(false);
            userRepository.save(user);
        }
    }
}