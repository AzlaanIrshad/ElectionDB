package entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    public enum Role {
        USER, MODERATOR, ADMIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is required")
    @Column(name = "username", length = 255, nullable = false)
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(name = "email", length = 255, unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Password is required")
    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "is_active")
    private boolean isActive = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 50, nullable = false)
    private Role role = Role.USER; // Default role is 'USER'

    @PrePersist
    private void hashPassword() {
        if (this.password != null) {
            this.password = new BCryptPasswordEncoder().encode(this.password);
        }
    }
}
