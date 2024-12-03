package com.example.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RestController
@RequestMapping("/party-result")
public class PartyController {

    @GetMapping("/{partyId}")
    public ResponseEntity<Object> GetSpecifiedParty(
            @PathVariable String partyId,
            @RequestParam(value = "years", required = false) List<Integer> years) {

        // Default year to 2023 if no years are provided
        if (years == null || years.isEmpty()) {
            years = List.of(2023);
        }

        Map<String, Object> resultsByYear = new HashMap<>();

        for (Integer year : years) {
            String basePath = "ParsedJson/";
            String filePath = basePath + year + "/tellingen_results.json";

            try (InputStream inputStream = new ClassPathResource(filePath).getInputStream()) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode root = objectMapper.readTree(inputStream);

                Map<String, Object> partyData = null;

                // Loop through all transactions in the data
                for (JsonNode transaction : root) {
                    JsonNode contests = transaction.path("count").path("election").path("contests").path("contests");

                    for (JsonNode contest : contests) {
                        JsonNode selections = contest.path("totalVotes").path("selections");
                        String currentPartyId = null;
                        List<Map<String, Object>> candidates = new ArrayList<>();

                        // Create a map to store candidate vote data
                        Map<String, Map<String, Object>> candidateVotesMap = new HashMap<>();

                        for (JsonNode selection : selections) {
                            // Handle affiliation (party) information
                            if (selection.has("affiliationIdentifier")) {
                                currentPartyId = selection.path("affiliationIdentifier").path("id").asText();
                                if (partyId.equals(currentPartyId)) {
                                    String partyName = selection.path("affiliationIdentifier").path("registeredName").asText("Unknown Party");
                                    int totalVotes = selection.path("validVotes").asInt(0);

                                    // Initialize partyData map
                                    partyData = new HashMap<>();
                                    partyData.put("partyId", currentPartyId);
                                    partyData.put("partyName", partyName);
                                    partyData.put("totalVotes", totalVotes);
                                    partyData.put("candidates", candidateVotesMap);
                                }
                            }

                            // Handle candidate data
                            if (selection.has("candidate") && currentPartyId != null && currentPartyId.equals(partyId)) {
                                String candidateId = selection.path("candidate").path("candidateIdentifier").path("id").asText();
                                int candidateVotes = selection.path("validVotes").asInt(0);

                                Map<String, Object> candidateData = candidateVotesMap.computeIfAbsent(candidateId, k -> new HashMap<>());
                                candidateData.put("candidateId", candidateId);
                                candidateData.put("validVotes", candidateVotes);
                            }
                        }

                        // Now populate candidate names and finalize the data
                        if (partyData != null) {
                            // Add candidates to the party
                            for (Map.Entry<String, Map<String, Object>> entry : candidateVotesMap.entrySet()) {
                                Map<String, Object> candidateData = entry.getValue();
                                String candidateId = (String) candidateData.get("candidateId");
                                String firstName = "First Name"; // You can replace with actual logic for name mapping
                                String lastName = "Last Name";  // Replace with actual logic if available
                                candidateData.put("name", firstName + " " + lastName);
                                candidates.add(candidateData);
                            }
                        }

                    }
                }

                // If we found data for the party, return it
                if (partyData != null) {
                    resultsByYear.put(year.toString(), partyData);
                } else {
                    resultsByYear.put(year.toString(), "No data found for partyId: " + partyId);
                }

            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Error: Unable to find results file for year " + year + ".");
            }
        }

        return ResponseEntity.ok(resultsByYear);
    }
}
