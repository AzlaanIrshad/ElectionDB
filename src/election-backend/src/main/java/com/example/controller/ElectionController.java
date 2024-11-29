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

@RestController
@RequestMapping("/election-results")
public class ElectionController {

    @GetMapping("/{year}")
    public ResponseEntity<Object> getElectionResults(
            @PathVariable int year,
            @RequestParam(value = "party", required = false) String party) {
        String filePath = "ParsedJson/" + year + "/tellingen_results.json";

        try (InputStream inputStream = new ClassPathResource(filePath).getInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(inputStream);

            if (party != null && !party.isEmpty()) {
                ArrayNode filteredResults = objectMapper.createArrayNode();

                for (JsonNode transaction : root) {
                    JsonNode contests = transaction.path("count").path("election").path("contests").path("contests");
                    for (JsonNode contest : contests) {
                        JsonNode selections = contest.path("totalVotes").path("selections");
                        for (JsonNode selection : selections) {
                            String partyName = selection.path("identifier").path("registeredName").asText();
                            if (party.equalsIgnoreCase(partyName)) {
                                filteredResults.add(selection);
                            }
                        }
                    }
                }

                if (filteredResults.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Geen resultaten gevonden voor partij: " + party);
                }
                return ResponseEntity.ok(filteredResults);
            }

            // Als er geen party error
            return ResponseEntity.ok(root);
        } catch (IOException e) {
            String errorMessage = "Error: resultatenbestand voor jaar " + year + " niet gevonden of een fout opgetreden.";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }
}
