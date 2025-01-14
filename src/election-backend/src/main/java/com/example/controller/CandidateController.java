package com.example.controller;

import com.example.service.CandidateService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping("/{year}")
    public ResponseEntity<Object> getCandidates(
            @PathVariable int year,
            @RequestParam(value = "id", required = false) Integer candidateId) {

        try {
            JsonNode root = candidateService.getCandidatesFromFile(year);

            if (candidateId != null) {
                JsonNode candidate = candidateService.findCandidateById(root, candidateId);
                if (candidate != null) {
                    return ResponseEntity.ok(candidate);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Kandidaat met id " + candidateId + " niet gevonden.");
                }
            }

            return ResponseEntity.ok(root);

        } catch (IOException e) {
            String errorMessage = "Error: kandidatenlijsten_results.json for year " + year + " not found or another error occurred.";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }
}