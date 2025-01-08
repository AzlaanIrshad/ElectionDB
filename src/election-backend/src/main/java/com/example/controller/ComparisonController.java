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
@RequestMapping("/comparison")
public class ComparisonController {

    @GetMapping("/closest-city")
    public ResponseEntity<Object> getClosestCity(
            @RequestParam(value = "year", required = false, defaultValue = "2023") Integer year) {

        String filePath = "ParsedJson/" + year + "/tellingen_results.json";

        try (InputStream inputStream = new ClassPathResource(filePath).getInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(inputStream);

            // Calculate overall results
            Map<String, Integer> overallResults = calculateOverallVotes(root);

            // Calculate distances for each city
            List<Map<String, Object>> cityDistances = calculateCityDistances(root, overallResults);

            // Find the closest city
            Map<String, Object> closestCity = cityDistances.stream()
                    .min(Comparator.comparingInt(city -> (int) city.get("distance")))
                    .orElse(null);

            if (closestCity == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No cities found for the specified year.");
            }

            return ResponseEntity.ok(closestCity);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: resultatenbestand niet gevonden of een fout opgetreden.");
        }
    }

    private Map<String, Integer> calculateOverallVotes(JsonNode root) {
        Map<String, Integer> overallVotes = new HashMap<>();

        for (JsonNode transaction : root) {
            JsonNode contests = transaction.path("count").path("election").path("contests").path("contests");
            for (JsonNode contest : contests) {
                JsonNode selections = contest.path("totalVotes").path("selections");
                for (JsonNode selection : selections) {
                    String partyName = selection.path("affiliationIdentifier").path("registeredName").asText();
                    int votes = selection.path("validVotes").asInt(0);

                    if (!partyName.isEmpty()) {
                        overallVotes.put(partyName, overallVotes.getOrDefault(partyName, 0) + votes);
                    }
                }
            }
        }

        return overallVotes;
    }

    private List<Map<String, Object>> calculateCityDistances(JsonNode root, Map<String, Integer> overallResults) {
        List<Map<String, Object>> cityDistances = new ArrayList<>();

        for (JsonNode transaction : root) {
            String cityName = getCityName(transaction);
            if (cityName != null && !cityName.isEmpty()) {
                Map<String, Integer> cityVotes = calculateCityVotes(transaction);

                // Calculate Manhattan distance
                int distance = calculateManhattanDistance(overallResults, cityVotes);

                // Add city result
                Map<String, Object> cityResult = new HashMap<>();
                cityResult.put("cityName", cityName);
                cityResult.put("distance", distance);
                cityDistances.add(cityResult);
            }
        }

        return cityDistances;
    }

    private Map<String, Integer> calculateCityVotes(JsonNode transaction) {
        Map<String, Integer> cityVotes = new HashMap<>();

        JsonNode contests = transaction.path("count").path("election").path("contests").path("contests");
        for (JsonNode contest : contests) {
            JsonNode selections = contest.path("totalVotes").path("selections");
            for (JsonNode selection : selections) {
                String partyName = selection.path("affiliationIdentifier").path("registeredName").asText();
                int votes = selection.path("validVotes").asInt(0);

                if (!partyName.isEmpty()) {
                    cityVotes.put(partyName, cityVotes.getOrDefault(partyName, 0) + votes);
                }
            }
        }

        return cityVotes;
    }

    private int calculateManhattanDistance(Map<String, Integer> overallResults, Map<String, Integer> cityVotes) {
        int distance = 0;

        // Combine all parties
        Set<String> allParties = new HashSet<>(overallResults.keySet());
        allParties.addAll(cityVotes.keySet());

        // Calculate Manhattan distance
        for (String party : allParties) {
            int overallVotes = overallResults.getOrDefault(party, 0);
            int cityVotesForParty = cityVotes.getOrDefault(party, 0);
            distance += Math.abs(overallVotes - cityVotesForParty);
        }

        return distance;
    }

    private String getCityName(JsonNode transaction) {
        return transaction.path("managingAuthority").path("authorityIdentifier").path("value").asText();
    }
}
