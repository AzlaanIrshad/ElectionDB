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

        if (years == null || years.isEmpty()) {
            years = List.of(2023);
        }

        Map<String, Object> resultsByYear = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (Integer year : years) {
            String partyFilePath = "ParsedJson/" + year + "/tellingen_results.json";
            String candidateFilePath = "ParsedJson/" + year + "/kandidatenlijsten_results.json";

            try (InputStream partyInputStream = new ClassPathResource(partyFilePath).getInputStream();
                 InputStream candidateInputStream = new ClassPathResource(candidateFilePath).getInputStream()) {

                JsonNode partyRoot = objectMapper.readTree(partyInputStream);
                JsonNode candidateRoot = objectMapper.readTree(candidateInputStream);

                Map<String, Map<String, String>> partyCandidatesMap = extractCandidateData(candidateRoot);

                Map<String, Object> partyData = extractPartyData(partyRoot, partyId, partyCandidatesMap);
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

    private Map<String, Map<String, String>> extractCandidateData(JsonNode root) {
        Map<String, Map<String, String>> partyCandidatesMap = new HashMap<>();

        for (JsonNode transaction : root) {
            JsonNode contests = transaction.path("candidateList").path("election").path("contests");

            for (JsonNode contest : contests) {
                JsonNode affiliations = contest.path("affiliations");

                for (JsonNode affiliation : affiliations) {
                    String partyId = affiliation.path("affiliationIdentifier").path("id").asText();
                    JsonNode candidates = affiliation.path("candidates");

                    Map<String, String> candidatesForParty = partyCandidatesMap.computeIfAbsent(partyId, k -> new HashMap<>());

                    for (JsonNode candidate : candidates) {
                        String candidateId = candidate.path("candidateIdentifier").path("id").asText();
                        String firstName = candidate.path("candidateFullName").path("personName").path("firstName").asText("");
                        String lastName = candidate.path("candidateFullName").path("personName").path("lastName").asText("");

                        if (!candidateId.isEmpty()) {
                            candidatesForParty.put(candidateId, firstName + " " + lastName);
                        }
                    }
                }
            }
        }

        return partyCandidatesMap;
    }

    private Map<String, Object> extractPartyData(JsonNode root, String partyId, Map<String, Map<String, String>> partyCandidatesMap) {
        Map<String, Object> partyData = null;
        List<Map<String, Object>> candidates = new ArrayList<>();
        Map<String, Map<String, Object>> candidateVotesMap = new HashMap<>();
        int accumulatedTotalVotes = 0;

        Map<String, String> candidateNameMap = partyCandidatesMap.getOrDefault(partyId, new HashMap<>());

        for (JsonNode transaction : root) {
            JsonNode contests = transaction.path("count").path("election").path("contests").path("contests");

            for (JsonNode contest : contests) {
                JsonNode selections = contest.path("totalVotes").path("selections");
                String currentPartyId = null;

                for (JsonNode selection : selections) {
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
                            accumulatedTotalVotes += totalVotes;
                        }
                    }

                    if (selection.has("candidate") && currentPartyId != null && currentPartyId.equals(partyId)) {
                        String candidateId = selection.path("candidate").path("candidateIdentifier").path("id").asText();
                        int candidateVotes = selection.path("validVotes").asInt(0);

                        Map<String, Object> candidateData = candidateVotesMap.computeIfAbsent(candidateId, k -> {
                            Map<String, Object> newCandidateData = new HashMap<>();
                            String candidateName = candidateNameMap.getOrDefault(candidateId, "Unknown Candidate");
                            newCandidateData.put("candidateId", candidateId);
                            newCandidateData.put("name", candidateName);
                            newCandidateData.put("validVotes", 0);
                            return newCandidateData;
                        });

                        int existingVotes = (int) candidateData.get("validVotes");
                        candidateData.put("validVotes", existingVotes + candidateVotes);

                        if (!candidates.contains(candidateData)) {
                            candidates.add(candidateData);
                        }
                    }
                }
            }
        }

        if (partyData != null) {
            partyData.put("totalVotes", accumulatedTotalVotes);
        }

        return partyData;
    }
}
