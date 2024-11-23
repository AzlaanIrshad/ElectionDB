package com.example.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;

@RequestMapping("/api")
@RestController
public class CandidateController {

    @GetMapping("/candidates")
    public String getCandidates() {
        try {
            Path path = new ClassPathResource("kandidatenlijsten_results.json").getFile().toPath();
            return Files.readString(path);
        } catch (Exception e) {
            return "Error: candidates_results.json not found in classpath";
        }
    }

    @GetMapping("/candidates/{id}")
    public String getSingleCandidate(@PathVariable Long id) {
        try {
            Path path = new ClassPathResource("kandidatenlijsten_results.json").getFile().toPath();
            return Files.readString(path);
        } catch (Exception e) {
            return "Error: candidates_results.json not found in classpath";
        }
    }
}
