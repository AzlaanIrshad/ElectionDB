package com.example.repository;

import com.example.entity.Role;
import com.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Zoek een gebruiker op basis van e-mailadres.
     *
     * @param email Het e-mailadres van de gebruiker.
     * @return Een optionele gebruiker.
     */
    Optional<User> findByEmail(String email);

    /**
     * Zoek een gebruiker op basis van gebruikersnaam.
     *
     * @param username De gebruikersnaam van de gebruiker.
     * @return Een optionele gebruiker.
     */
    Optional<User> findByUsername(String username);

    /**
     * Vind alle gebruikers met een specifieke rol.
     *
     * @param role De rol om op te filteren.
     * @return Een lijst van gebruikers met de opgegeven rol.
     */
    List<User> findByRole(Role role);

    /**
     * Controleer of een gebruiker bestaat op basis van e-mailadres.
     *
     * @param email Het e-mailadres om te controleren.
     * @return `true` als de gebruiker bestaat, anders `false`.
     */
    boolean existsByEmail(String email);

    /**
     * Controleer of een gebruiker bestaat op basis van gebruikersnaam.
     *
     * @param username De gebruikersnaam om te controleren.
     * @return `true` als de gebruiker bestaat, anders `false`.
     */
    boolean existsByUsername(String username);
}
