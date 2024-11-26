package com.example.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@RequestMapping("/api")
@RestController
public class CandidateController {

    @GetMapping("/candidates")
    public String getCandidates() {
        try (InputStream inputStream = new ClassPathResource("kandidatenlijsten_results.json").getInputStream()) {
            Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name());
            return scanner.useDelimiter("\\A").next();
        } catch (Exception e) {
            return "Error: kandidatenlijsten_results.json not found in classpath or could not be read";
        }
    }

    @GetMapping("/candidates/{id}")
    public String getSingleCandidate(@PathVariable Long id) {
        try (InputStream inputStream = new ClassPathResource("kandidatenlijsten_results.json").getInputStream()) {
            Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name());
            return scanner.useDelimiter("\\A").next();
        } catch (Exception e) {
            return "Error: kandidatenlijsten_results.json not found in classpath or could not be read";
        }
    }
}
