package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        return ResponseEntity.ok(userService.login(email, password));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam String email, @RequestParam String password, @RequestParam String username) {
        return ResponseEntity.ok(userService.register(email, password, username));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam String email) {
        userService.logout(email);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
