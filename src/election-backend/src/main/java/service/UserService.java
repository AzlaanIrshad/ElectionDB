package service;

import entity.User;
import entity.User.Role;
import repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import exception.ResourceNotFoundException;
import exception.UnauthorizedActionException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();  // Fetch all users
    }

    public void deactivateUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        user.ifPresent(u -> {
            u.setActive(false);
            userRepository.save(u);
        });
    }
}
