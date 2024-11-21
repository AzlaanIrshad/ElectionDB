package com.example.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api")
public class ElectionController {

    @GetMapping("/election-results")
    public String getElectionResults() throws Exception {
        InputStream inputStream = new ClassPathResource("election_results.json").getInputStream();
        return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    }

    @GetMapping("/parties/{id}")
    public String getSingleParty(@PathVariable Long id) throws Exception {
        InputStream inputStream = new ClassPathResource("election_results.json").getInputStream();
        return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    }
}
