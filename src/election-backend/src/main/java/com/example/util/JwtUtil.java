package com.example.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private final Key key = Keys.hmacShaKeyFor("your_secret_key_your_secret_key_your".getBytes());

    /**
     * Genereert een JWT-token.
     *
     * @param email   Het e-mailadres van de gebruiker.
     * @param role    De rol van de gebruiker.
     * @param userId  De unieke gebruikers-ID.
     * @return Een JWT-token als String.
     */
    public String generateToken(String email, String role, String userId) {
        logger.info("Genereren van JWT-token voor email: {}, met rol: {}, en userId: {}", email, role, userId);
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .claim("userId", userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 uur
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extraheert claims uit een JWT-token.
     *
     * @param token De JWT-token.
     * @return De claims die in de token zijn opgenomen.
     */
    public Claims extractClaims(String token) {
        logger.debug("Extracting claims from token.");
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    /**
     * Extraheert het e-mailadres uit een JWT-token.
     *
     * @param token De JWT-token.
     * @return Het e-mailadres als String.
     */
    public String extractEmail(String token) {
        String email = extractClaims(token).getSubject();
        logger.info("Extracted email from token: {}", email);
        return email;
    }

    /**
     * Extraheert de rol van de gebruiker uit een JWT-token.
     *
     * @param token De JWT-token.
     * @return De rol van de gebruiker als String.
     */
    public String extractRole(String token) {
        String role = extractClaims(token).get("role", String.class);
        logger.info("Extracted role from token: {}", role);
        return role;
    }

    /**
     * Extraheert de userId van de gebruiker uit een JWT-token.
     *
     * @param token De JWT-token.
     * @return De userId van de gebruiker als String.
     */
    public String extractUserId(String token) {
        String userId = extractClaims(token).get("userId", String.class);
        logger.info("Extracted userId from token: {}", userId);
        return userId;
    }

    /**
     * Controleert of een token is verlopen.
     *
     * @param token De JWT-token.
     * @return `true` als de token is verlopen, anders `false`.
     */
    public boolean isTokenExpired(String token) {
        boolean expired = extractClaims(token).getExpiration().before(new Date());
        logger.info("Token expired: {}", expired);
        return expired;
    }

    /**
     * Valideert een token tegen een specifiek e-mailadres.
     *
     * @param token De JWT-token.
     * @param email Het e-mailadres om te valideren.
     * @return `true` als het token geldig is, anders `false`.
     */
    public boolean validateToken(String token, String email) {
        final String extractedEmail = extractEmail(token);
        boolean valid = extractedEmail.equals(email) && !isTokenExpired(token);
        logger.info("Token validation result for email {}: {}", email, valid);
        return valid;
    }
}
