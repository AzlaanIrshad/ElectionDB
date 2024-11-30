package com.example.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/election-results")
public class ElectionController {

    @GetMapping
    public ResponseEntity<Object> getTotalVotesPerYears(
            @RequestParam(value = "years", required = false) List<Integer> years) {

        // standaardjaar 2023
        if (years == null || years.isEmpty()) {
            years = List.of(2023);
        }

        Map<String, Map<String, Integer>> resultsByYear = new HashMap<>();

        for (Integer year : years) {
            String basePath = "ParsedJson/";
            String filePath = basePath + year + "/tellingen_results.json";

            try (InputStream inputStream = new ClassPathResource(filePath).getInputStream()) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode root = objectMapper.readTree(inputStream);

                // Totale stemmen per partij berekenen
                Map<String, Integer> partyVotes = new HashMap<>();
                for (JsonNode transaction : root) {
                    JsonNode contests = transaction.path("count").path("election").path("contests").path("contests");
                    for (JsonNode contest : contests) {
                        JsonNode selections = contest.path("totalVotes").path("selections");
                        for (JsonNode selection : selections) {
                            String party = selection.path("affiliationIdentifier").path("registeredName").asText();
                            int votes = selection.path("validVotes").asInt(0);

                            if (party != null && !party.isEmpty()) {
                                partyVotes.put(party, partyVotes.getOrDefault(party, 0) + votes);
                            }
                        }
                    }
                }

                resultsByYear.put(year.toString(), partyVotes);

            } catch (IOException e) {
                String errorMessage = "Error: resultatenbestand niet gevonden voor jaar " + year + ".";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }
        }

        return ResponseEntity.ok(resultsByYear);
    }
}