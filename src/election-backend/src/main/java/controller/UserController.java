package main.java.Controller;

import entity.User;
import main.java.exception.UserAlreadyExistsException;
import service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid; // Updated import
import jakarta.validation.constraints.NotNull; // Updated import
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<User> login(@Valid @RequestBody User user) {
        // Validate user input
        if (user.getEmail() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        User loggedInUser = userService.login(user.getEmail(), user.getPassword());
        if (loggedInUser != null) {
            return ResponseEntity.ok(loggedInUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody User user) {
        // Validate user input
        if (user.getEmail() == null) {
            return ResponseEntity.badRequest().build();
        }

        userService.logout(user.getEmail());
        return ResponseEntity.ok().build();
    }
}
