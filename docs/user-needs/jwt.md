# JWT Guidelines

This document provides an overview of the implementation and use of JSON Web Tokens (JWT) for secure user authentication.

---

## User Need: Security
Users require a secure and reliable method for session management and authentication. JWT is implemented to ensure safe and stateless communication.

## **JWT Implementation**
### **Token Generation**
- A JWT is generated upon successful authentication using the `JwtUtil` class.
- Key details of the implementation:
  ```java
  return Jwts.builder()
          .setSubject(email)
          .claim("role", role)
          .setIssuedAt(new Date(System.currentTimeMillis()))
          .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
          .signWith(key, SignatureAlgorithm.HS256)
          .compact();
  ```
    - The token contains:
        - **Subject**: User email.
        - **Claim**: User role.
        - **Expiration**: Valid for 10 hours.
    - Signed with HMAC SHA-256 using a secure secret key.

### **Token Validation**
- Tokens are validated against the email and expiration status.
  ```java
  public boolean validateToken(String token, String email) {
      final String extractedEmail = extractEmail(token);
      return extractedEmail.equals(email) && !isTokenExpired(token);
  }
  ```

### **Claims Extraction**
- **Email Extraction**:
  ```java
  public String extractEmail(String token) {
      return extractClaims(token).getSubject();
  }
  ```
- **Role Extraction**:
  ```java
  public String extractRole(String token) {
      return extractClaims(token).get("role", String.class);
  }
  ```

## **Advantages of JWT**
- **Stateless**: Eliminates the need for server-side session storage.
- **Compact**: Easily transmitted in headers or URLs.
- **Self-Contained**: Carries user information and claims, reducing the need for additional database queries.

## Conclusion
The `JwtUtil` class provides a robust implementation of JWT for secure user authentication. Adhering to best practices ensures that user sessions are protected against common vulnerabilities.