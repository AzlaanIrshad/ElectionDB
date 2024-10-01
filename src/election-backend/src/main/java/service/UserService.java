package service;

import entity.UserEntity;
import repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);
    }

    public UserEntity getUser(String email) {
        return userRepository.findByEmail(email);
    }

    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    public void deactivateUser(String email) {
        UserEntity user = userRepository.findByEmail(email);
        if (user != null) {
            user.setActive(false);
            userRepository.save(user);
        }
    }
}
