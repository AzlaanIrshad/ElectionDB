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
@RequestMapping("/map-results")
public class MapController {

    @GetMapping
    public ResponseEntity<Object> getMapResults(
            @RequestParam(value = "year", required = false, defaultValue = "2023") Integer year,
            @RequestParam(value = "parties", required = false) List<String> parties) {

        String filePath = "ParsedJson/" + year + "/tellingen_results.json";
        try (InputStream inputStream = new ClassPathResource(filePath).getInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(inputStream);

            List<Map<String, Object>> results = new ArrayList<>();

            for (JsonNode transaction : root) {
                String cityName = transaction.path("managingAuthority").path("authorityIdentifier").path("value").asText();
                if (cityName.isEmpty()) continue;

                JsonNode contests = transaction.path("count").path("election").path("contests").path("contests");
                String leadingParty = null;
                int maxVotes = 0;

                for (JsonNode contest : contests) {
                    JsonNode selections = contest.path("totalVotes").path("selections");
                    for (JsonNode selection : selections) {
                        String partyName = selection.path("affiliationIdentifier").path("registeredName").asText();
                        int validVotes = selection.path("validVotes").asInt(0);

                        if (partyName.isEmpty()) continue;

                        if (leadingParty == null || validVotes > maxVotes) {
                            leadingParty = partyName;
                            maxVotes = validVotes;
                        }
                    }
                }

                if (leadingParty == null || (parties != null && !parties.contains(leadingParty))) {
                    continue;
                }

                Map<String, Object> cityResult = new HashMap<>();
                cityResult.put("cityName", cityName);
                cityResult.put("leadingParty", leadingParty);
                cityResult.put("votes", maxVotes);
                results.add(cityResult);
            }

            return ResponseEntity.ok(results);
        } catch (IOException e) {
            String errorMessage = "Error: resultatenbestand niet gevonden of een fout opgetreden.";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }
}
