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

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getUser(String email) {
        return userRepository.findByEmail(email);
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
