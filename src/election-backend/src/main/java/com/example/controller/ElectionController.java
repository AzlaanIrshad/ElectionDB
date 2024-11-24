package com.example.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;

@RequestMapping("/api")
@RestController
public class ElectionController {

    @GetMapping("/election-results")
    public String getElectionResults() {
        try {
            Path path = new ClassPathResource("election_results.json").getFile().toPath();
            return Files.readString(path);
        } catch (Exception e) {
            return "Error: election_results.json not found in classpath or another error occurred.";
        }
    }

    @GetMapping("/parties/{id}")
    public String getSingleParty(@PathVariable Long id) {
        try {
            Path path = new ClassPathResource("election_results.json").getFile().toPath();
            return Files.readString(path);
        } catch (Exception e) {return "Error: election_results.json not found in classpath or another error occurred.";
        }
    }
}
