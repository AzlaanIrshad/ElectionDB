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

            List<Map<String, Object>> results = processTransactions(root, parties);

            return ResponseEntity.ok(results);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: resultatenbestand niet gevonden of een fout opgetreden.");
        }
    }

    // Verwerkt alle transacties
    private List<Map<String, Object>> processTransactions(JsonNode root, List<String> parties) {
        List<Map<String, Object>> results = new ArrayList<>();
        for (JsonNode transaction : root) {
            String cityName = getCityName(transaction);
            if (cityName != null && !cityName.isEmpty()) {
                Map<String, Object> cityResult = processContests(transaction.path("count").path("election").path("contests").path("contests"), cityName, parties);
                if (cityResult != null) {
                    results.add(cityResult);
                }
            }
        }
        return results;
    }

    // Haalt de naam van de stad op uit de transactie
    private String getCityName(JsonNode transaction) {
        return transaction.path("managingAuthority").path("authorityIdentifier").path("value").asText();
    }

    // Verwerkt de contesten voor een specifieke stad
    private Map<String, Object> processContests(JsonNode contests, String cityName, List<String> parties) {
        String leadingParty = null;
        int maxVotes = 0;

        for (JsonNode contest : contests) {
            Map<String, Object> contestResult = processSelections(contest.path("totalVotes").path("selections"), parties);
            if (contestResult != null) {
                leadingParty = (String) contestResult.get("leadingParty");
                maxVotes = (int) contestResult.get("maxVotes");
            }
        }

        if (leadingParty == null) {
            return null;
        }

        return buildCityResult(cityName, leadingParty, maxVotes);
    }

    // Verwerkt de selectie van partijen binnen een contest
    private Map<String, Object> processSelections(JsonNode selections, List<String> parties) {
        String leadingParty = null;
        int maxVotes = 0;

        for (JsonNode selection : selections) {
            String partyName = selection.path("affiliationIdentifier").path("registeredName").asText();
            int validVotes = selection.path("validVotes").asInt(0);

            if (partyName.isEmpty()) continue;

            if (validVotes > maxVotes && (parties == null || parties.contains(partyName))) {
                leadingParty = partyName;
                maxVotes = validVotes;
            }
        }

        if (leadingParty == null) {
            return null;
        }

        Map<String, Object> result = new HashMap<>();
        result.put("leadingParty", leadingParty);
        result.put("maxVotes", maxVotes);
        return result;
    }

    // Bouwt de stadresultaten
    private Map<String, Object> buildCityResult(String cityName, String leadingParty, int maxVotes) {
        Map<String, Object> cityResult = new HashMap<>();
        cityResult.put("cityName", cityName);
        cityResult.put("leadingParty", leadingParty);
        cityResult.put("votes", maxVotes);
        return cityResult;
    }
}
