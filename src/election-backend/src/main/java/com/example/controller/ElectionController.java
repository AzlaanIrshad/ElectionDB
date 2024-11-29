package com.example.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@RestController
@RequestMapping("/election-results")
public class ElectionController {

    @GetMapping("/{year}")
    public ResponseEntity<String> getElectionResults(@PathVariable int year) {
        String filePath = "ParsedJson/" + year + "/tellingen_results.json";


            try (InputStream inputStream = new ClassPathResource(filePath).getInputStream()) {
            Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name());
            String content = scanner.useDelimiter("\\A").next();
            return ResponseEntity.ok(content);
        } catch (IOException e) {
            String errorMessage = "Error: election_results.json for year " + year + " not found or another error occurred.";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }
}