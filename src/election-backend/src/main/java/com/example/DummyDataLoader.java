package com.example.election;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DummyDataLoader implements CommandLineRunner {

    @Autowired
    private CandidateRepository candidateRepository;

    @Override
    public void run(String... args) throws Exception {
        // Insert dummy data
        candidateRepository.save(new Candidate("Alice Johnson", "Democratic Party", 45));
        candidateRepository.save(new Candidate("Bob Williams", "Republican Party", 52));
        candidateRepository.save(new Candidate("Charlie Smith", "Green Party", 39));
    }
}
