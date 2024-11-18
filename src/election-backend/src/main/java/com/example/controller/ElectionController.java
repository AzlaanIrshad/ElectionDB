package com.example.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
public class ElectionController {

    @GetMapping("/api/election-results")
    public String getElectionResults() throws Exception {
        Path path = new ClassPathResource("election_results.json").getFile().toPath();
        return Files.readString(path);
    }
}
