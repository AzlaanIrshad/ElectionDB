package com.example.controller;

import com.example.service.ElectionResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/compare-manhattan")
    public ResponseEntity<Object> compareManhattanDistance(
            @RequestParam(value = "year", required = true) int year) {
        try {

            Map<String, Object> comparisonResults = electionService.calculateManhattanDistance(year);
            return ResponseEntity.ok(comparisonResults);
        } catch (IOException e) {
            return ResponseEntity.status(404).body("Error: resultatenbestand niet gevonden.");
        }
    }
}
