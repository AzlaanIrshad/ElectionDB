# Hashing Guidelines

This document explains the use of hashing for secure password management in the `UserService` class.

---

## User Need: Security
Users need assurance that their passwords are securely stored and managed to prevent unauthorized access.

## **Password Hashing Implementation**
- **Hashing Algorithm**: Passwords are hashed using `BCryptPasswordEncoder`, which is widely regarded as secure due to its computational complexity and built-in salting mechanism.
  ```java
  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  ```
- **Verification**: During authentication, the provided password is verified against the stored hash using the `matches` method of `BCryptPasswordEncoder`.
  ```java
  if (passwordEncoder.matches(password, user.getPassword())) {
      // Password matches
  }
  ```

## **Benefits of BCrypt**
1. **Salting**: Automatically adds a unique salt to each password to protect against rainbow table attacks.
2. **Adaptive Hashing**: Supports increasing the work factor to remain secure as computational power increases.
3. **Secure Verification**: Ensures that passwords are compared securely without exposing raw data.

## Conclusion
By leveraging `BCryptPasswordEncoder` for password hashing, the `UserService` ensures strong protection against unauthorized access. This approach aligns with modern security practices and provides a secure foundation for user authentication.

