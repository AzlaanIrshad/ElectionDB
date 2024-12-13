package com.example.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.entity.Party;
import com.example.repository.PartyRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RestController
@RequestMapping("/party-result")
public class PartyController {

    @Autowired
    private PartyRepository partyRepository;

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
            String firstFilePath = "ParsedJson/2023/tellingen_results.json";
            String partyFilePath = "ParsedJson/" + year + "/tellingen_results.json";
            String candidateFilePath = "ParsedJson/" + year + "/kandidatenlijsten_results.json";

            try (InputStream partyInputStream = new ClassPathResource(partyFilePath).getInputStream();
                 InputStream firstInputStream = new ClassPathResource(firstFilePath).getInputStream();
                 InputStream candidateInputStream = new ClassPathResource(candidateFilePath).getInputStream()) {

                JsonNode firstRoot = objectMapper.readTree(firstInputStream);
                JsonNode partyRoot = objectMapper.readTree(partyInputStream);
                JsonNode candidateRoot = objectMapper.readTree(candidateInputStream);

                Map<String, Map<String, String>> partyCandidatesMap = extractCandidateData(candidateRoot);

                String partyName = extractPartyName(firstRoot, partyId);
                System.out.println("partyname: " + partyName);

                if (partyName != null) {
                    Map<String, Object> partyData = extractPartyDataByName(partyRoot, partyName, partyCandidatesMap);
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

    private String extractPartyName(JsonNode root, String partyId) {
        for (JsonNode transaction : root) {
            JsonNode contests = transaction.path("count").path("election").path("contests").path("contests");

            for (JsonNode contest : contests) {
                JsonNode selections = contest.path("totalVotes").path("selections");

                for (JsonNode selection : selections) {
                    if (selection.has("affiliationIdentifier")) {
                        String currentPartyId = selection.path("affiliationIdentifier").path("id").asText();
                        if (partyId.equals(currentPartyId)) {
                            return selection.path("affiliationIdentifier").path("registeredName").asText("Unknown Party");
                        }
                    }
                }
            }
        }
        return null;
    }

    private Map<String, Object> extractPartyDataByName(JsonNode root, String partyName, Map<String, Map<String, String>> partyCandidatesMap) {
        Map<String, Object> partyData = null;
        List<Map<String, Object>> candidates = new ArrayList<>();
        Map<String, Map<String, Object>> candidateVotesMap = new HashMap<>();
        int accumulatedTotalVotes = 0;

        for (JsonNode transaction : root) {
            JsonNode contests = transaction.path("count").path("election").path("contests").path("contests");

            for (JsonNode contest : contests) {
                JsonNode selections = contest.path("totalVotes").path("selections");
                String currentPartyName = null;

                for (JsonNode selection : selections) {
                    if (selection.has("affiliationIdentifier")) {
                        currentPartyName = selection.path("affiliationIdentifier").path("registeredName").asText("Unknown Party");

                        if (partyName.equals(currentPartyName)) {
                            String partyId = selection.path("affiliationIdentifier").path("id").asText();
                            int totalVotes = selection.path("validVotes").asInt(0);

                            Optional<Party> optionalParty = partyRepository.findByName(partyName);
                            String entityParty;

                            if (optionalParty.isPresent()) {
                                Party party = optionalParty.get();
                                entityParty = party.getDescription();
                            } else {
                                entityParty = "Party not found";
                            }

                            if (partyData==null) {
                                partyData = new HashMap<>();
                                partyData.put("partyId", partyId);
                                partyData.put("partyName", partyName);
                                partyData.put("candidates", candidates);
                                partyData.put("description", entityParty);
                            }

                            accumulatedTotalVotes += totalVotes;
                        }
                    }
                    if (selection.has("candidate") && currentPartyName != null && currentPartyName.equals(partyName)) {
                        String candidateId = selection.path("candidate").path("candidateIdentifier").path("id").asText();
                        int candidateVotes = selection.path("validVotes").asInt(0);

                        Map<String, Object> candidateData = candidateVotesMap.computeIfAbsent(candidateId, k -> {
                            Map<String, Object> newCandidateData = new HashMap<>();
                            String candidateName = partyCandidatesMap.getOrDefault(partyName, new HashMap<>()).get(candidateId);
                            newCandidateData.put("candidateId", candidateId);
                            newCandidateData.put("name", candidateName != null ? candidateName : "Unknown Candidate");
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

        if (!partyData.isEmpty()) {
            partyData.put("totalVotes", accumulatedTotalVotes);
        }

        return partyData;
    }

    private Map<String, Map<String, String>> extractCandidateData(JsonNode root) {
        Map<String, Map<String, String>> partyCandidatesMap = new HashMap<>();

        for (JsonNode transaction : root) {
            JsonNode contests = transaction.path("candidateList").path("election").path("contests");

            for (JsonNode contest : contests) {
                JsonNode affiliations = contest.path("affiliations");

                for (JsonNode affiliation : affiliations) {
                    String partyName = affiliation.path("affiliationIdentifier").path("registeredName").asText();
                    JsonNode candidates = affiliation.path("candidates");

                    Map<String, String> candidatesForParty = partyCandidatesMap.computeIfAbsent(partyName, k -> new HashMap<>());

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
}
