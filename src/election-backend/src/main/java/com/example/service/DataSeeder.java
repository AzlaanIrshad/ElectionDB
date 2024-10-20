package com.example.service;

import com.example.entity.Candidate;
import com.example.entity.User;
import com.example.entity.User.Role;
import com.example.repository.CandidateRepository;
import com.example.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataSeeder implements CommandLineRunner {

    private final CandidateRepository candidateRepository;
    private final UserRepository userRepository;

    public DataSeeder(CandidateRepository candidateRepository, UserRepository userRepository) {
        this.candidateRepository = candidateRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Seed dummy Candidates
        if (candidateRepository.count() == 0) {
            Candidate candidate1 = new Candidate("John Doe", "Democratic");
            Candidate candidate2 = new Candidate("Jane Smith", "Republican");
            Candidate candidate3 = new Candidate("Robert Johnson", "Independent");

            candidateRepository.save(candidate1);
            candidateRepository.save(candidate2);
            candidateRepository.save(candidate3);
        }

        // Seed dummy Users
        if (userRepository.count() == 0) {
            User admin = new User("admin", "admin@example.com", "password123", Role.ADMIN);
            User moderator = new User("moderator", "moderator@example.com", "password123", Role.MODERATOR);
            User user = new User("user1", "user@user", "user", Role.USER);

            userRepository.save(admin);
            userRepository.save(moderator);
            userRepository.save(user);
        }
    }
}
