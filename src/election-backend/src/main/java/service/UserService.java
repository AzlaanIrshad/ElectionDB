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

    public User saveUser(User user) {
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }
        return userRepository.save(user);
    }

    public User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
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
