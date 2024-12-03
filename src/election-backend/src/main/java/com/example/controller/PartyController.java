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
    public ResponseEntity<Object> getSpecifiedParty(
            @PathVariable String partyId,
            @RequestParam(value = "years", required = false) List<Integer> years) {

        // Default year to 2023 if no years are provided
        if (years == null || years.isEmpty()) {
            years = List.of(2023);
        }

        Map<String, Object> resultsByYear = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (Integer year : years) {
            String filePath = "ParsedJson/" + year + "/tellingen_results.json";

            try (InputStream inputStream = new ClassPathResource(filePath).getInputStream()) {
                JsonNode root = objectMapper.readTree(inputStream);

                Map<String, Object> partyData = extractPartyData(root, partyId);
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

    private Map<String, Object> extractPartyData(JsonNode root, String partyId) {
        Map<String, Object> partyData = null;
        Map<String, Map<String, Object>> candidateVotesMap = new HashMap<>();
        List<Map<String, Object>> candidates = new ArrayList<>();
        int accumulatedTotalVotes = 0;

        for (JsonNode transaction : root) {
            JsonNode contests = transaction.path("count").path("election").path("contests").path("contests");

            for (JsonNode contest : contests) {
                JsonNode selections = contest.path("totalVotes").path("selections");
                String currentPartyId = null;

                for (JsonNode selection : selections) {
                    // Check for party data
                    if (selection.has("affiliationIdentifier")) {
                        currentPartyId = selection.path("affiliationIdentifier").path("id").asText();
                        if (partyId.equals(currentPartyId)) {
                            String partyName = selection.path("affiliationIdentifier").path("registeredName").asText("Unknown Party");
                            int totalVotes = selection.path("validVotes").asInt(0);

                            if (partyData == null) {
                                partyData = new HashMap<>();
                                partyData.put("partyId", currentPartyId);
                                partyData.put("partyName", partyName);
                                partyData.put("candidates", candidates);
                            }
                            accumulatedTotalVotes += totalVotes; // Add votes to accumulated total
                        }
                    }

                    if (selection.has("candidate") && currentPartyId != null && currentPartyId.equals(partyId)) {
                        String candidateId = selection.path("candidate").path("candidateIdentifier").path("id").asText();
                        int candidateVotes = selection.path("validVotes").asInt(0);

                        Map<String, Object> candidateData = candidateVotesMap.computeIfAbsent(candidateId, k -> new HashMap<>());

                        int existingVotes = (int) candidateData.getOrDefault("validVotes", 0);
                        candidateData.put("validVotes", existingVotes + candidateVotes);

                        String firstName = selection.path("candidate").path("candidateFullName").path("personName").path("firstName").asText("");
                        String lastName = selection.path("candidate").path("candidateFullName").path("personName").path("lastName").asText("");
                        candidateData.put("name", firstName + " " + lastName);

                        if (!candidates.contains(candidateData)) {
                            candidates.add(candidateData);
                        }
                    }
                }
            }
        }

        if (partyData != null) {
            // Accumulate total votes for the party
            partyData.put("totalVotes", accumulatedTotalVotes);
        }

        return partyData;
    }
}
