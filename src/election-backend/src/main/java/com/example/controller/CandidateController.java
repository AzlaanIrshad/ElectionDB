package com.example.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    @GetMapping("/{year}")
    public ResponseEntity<Object> getCandidates(
            @PathVariable int year,
            @RequestParam(value = "id", required = false) Integer candidateId) {

        String filePath = "ParsedJson/" + year + "/kandidatenlijsten_results.json";

        try (InputStream inputStream = new ClassPathResource(filePath).getInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(inputStream);

            if (candidateId != null) {
                for (JsonNode transaction : root) {
                    JsonNode contests = transaction.path("candidateList").path("election").path("contests");
                    for (JsonNode contest : contests) {
                        JsonNode affiliations = contest.path("affiliations");
                        for (JsonNode affiliation : affiliations) {
                            JsonNode candidates = affiliation.path("candidates");
                            for (JsonNode candidate : candidates) {
                                int id = candidate.path("candidateIdentifier").path("id").asInt();
                                if (id == candidateId) {
                                    return ResponseEntity.ok(candidate);
                                }
                            }
                        }
                    }
                }
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Kandidaat met id " + candidateId + " niet gevonden.");
            }

            return ResponseEntity.ok(root);

        } catch (IOException e) {
            String errorMessage = "Error: kandidatenlijsten_results.json for year " + year + " not found or another error occurred.";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }
}
