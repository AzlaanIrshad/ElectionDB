package com.example.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@RestController
public class ElectionController {

    @GetMapping("/election-results")
    public String getElectionResults() {
        try (InputStream inputStream = new ClassPathResource("election_results.json").getInputStream()) {
            Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name());
            String content = scanner.useDelimiter("\\A").next();
            return content;
        } catch (IOException e) {
            return "Error: election_results.json not found in classpath or another error occurred.";
        }
    }

    @GetMapping("/parties/{id}")
    public String getSingleParty(@PathVariable Long id) {
        try (InputStream inputStream = new ClassPathResource("election_results.json").getInputStream()) {
            Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name());
            String content = scanner.useDelimiter("\\A").next();
            return content;
        } catch (IOException e) {
            return "Error: election_results.json not found in classpath or another error occurred.";
        }
    }
}
