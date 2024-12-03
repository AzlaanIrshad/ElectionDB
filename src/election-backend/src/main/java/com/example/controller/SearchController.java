package com.example.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RestController
@RequestMapping("/search")
public class SearchController {

    @GetMapping("/2023")
    public ResponseEntity<Object> searchParties(@RequestParam(value = "query") String query) {
        String basePath = "ParsedJson/2023/";
        String filePath = basePath + "tellingen_results.json";

        try (InputStream inputStream = new ClassPathResource(filePath).getInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(inputStream);
            Map<String, Map<String, Object>> partiesMap = new HashMap<>();

            for (JsonNode transaction : root) {
                JsonNode contests = transaction.path("count").path("election").path("contests").path("contests");

                for (JsonNode contest : contests) {
                    JsonNode selections = contest.path("totalVotes").path("selections");
                    for (JsonNode selection : selections) {
                        String partyId = selection.path("affiliationIdentifier").path("id").asText();
                        String partyName = selection.path("affiliationIdentifier").path("registeredName").asText("Unknown Party");
                        int validVotes = selection.path("validVotes").asInt(0);

                        if (partyName.toLowerCase().contains(query.toLowerCase())) {

                            partiesMap.putIfAbsent(partyId, new HashMap<>());
                            Map<String, Object> partyData = partiesMap.get(partyId);
                            partyData.put("partyId", partyId);
                            partyData.put("partyName", partyName);
                            partyData.put("totalVotes", (int) partyData.getOrDefault("totalVotes", 0) + validVotes);
                        }
                    }
                }
            }

            List<Map<String, Object>> parties = new ArrayList<>(partiesMap.values());
            parties.sort((p1, p2) -> Integer.compare((int) p2.get("totalVotes"), (int) p1.get("totalVotes")));


            if (!parties.isEmpty()) {
                parties.remove(0);
            }

            return ResponseEntity.ok(parties);

        } catch (IOException e) {
            String errorMessage = "Error: resultatenbestand niet gevonden voor 2023.";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }
}
