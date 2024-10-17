package main.java.Controller;

import entity.User;
import service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid; // Updated import
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

    @PostMapping("/login")
    public ResponseEntity<User> login(@Valid @RequestBody User user) {
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

//    @PostMapping("/register")
//    public ResponseEntity<User> register(@Valid @RequestBody User user) {
//        // Validate user input
//        if (user.getEmail() == null || user.getPassword() == null || user.getUsername() == null) {
//            return ResponseEntity.badRequest().body(null);
//        }
//
//        try {
//            User registeredUser = userService.register(user.getEmail(), user.getPassword(), user.getUsername());
//            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
//        } catch (UserAlreadyExistsException e) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // User already exists
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Handle other errors
//        }
//    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody User user) {
        if (user.getEmail() == null) {
            return ResponseEntity.badRequest().build();
        }

        userService.logout(user.getEmail());
        return ResponseEntity.ok().build();
    }

}
