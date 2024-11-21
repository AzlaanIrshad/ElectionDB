package com.example.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api")
public class ElectionController {

    @GetMapping("/election-results")
    public String getElectionResults() throws Exception {
        Path path = new ClassPathResource("election_results.json").getFile().toPath();
        return Files.readString(path);
    }

@GetMapping("/parties/{id}")
    public String getSingleParty(@PathVariable Long id) throws Exception {
        Path path = new ClassPathResource("election_results.json").getFile().toPath();
        return Files.readString(path);
    }
}
