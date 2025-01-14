package com.example.controller;

import com.example.service.ElectionResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/election-results")
public class ElectionController {

    private final ElectionResultService electionService;

    @Autowired
    public ElectionController(@Qualifier("electionResultService") ElectionResultService electionService) {
        this.electionService = electionService;
    }

    @GetMapping
    public ResponseEntity<Object> getTotalVotesPerYears(
            @RequestParam(value = "years", required = false) List<Integer> years) {
        try {
            if (years == null || years.isEmpty()) {
                years = List.of(2023);
            }
            return ResponseEntity.ok(electionService.getTotalVotesPerYears(years));
        } catch (IOException e) {
            return ResponseEntity.status(404).body("Error: resultatenbestand niet gevonden.");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getFilteredResults(
            @RequestParam(value = "years", required = false) List<Integer> years) {
        try {
            if (years == null || years.isEmpty()) {
                years = List.of(2023);
            }
            return ResponseEntity.ok(electionService.getFilteredResults(years));
        } catch (IOException e) {
            return ResponseEntity.status(404).body("Error: resultatenbestand niet gevonden.");
        }
    }
}
