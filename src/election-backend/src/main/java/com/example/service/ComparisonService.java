package com.example.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class ComparisonService {

    public JsonNode loadElectionData(String filePath) throws IOException {
        InputStream inputStream = new ClassPathResource(filePath).getInputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(inputStream);
    }

    public Map<String, Integer> calculateOverallVotes(JsonNode root) {
        Map<String, Integer> overallVotes = new HashMap<>();

        for (JsonNode transaction : root) {
            JsonNode contests = transaction.path("count").path("election").path("contests").path("contests");
            for (JsonNode contest : contests) {
                JsonNode selections = contest.path("totalVotes").path("selections");
                for (JsonNode selection : selections) {
                    String partyName = selection.path("affiliationIdentifier").path("registeredName").asText();
                    int votes = selection.path("validVotes").asInt(0);
                    overallVotes.put(partyName, overallVotes.getOrDefault(partyName, 0) + votes);
                }
            }
        }

        return overallVotes;
    }

    public Map<String, Integer> calculateCityVotes(JsonNode transaction) {
        Map<String, Integer> cityVotes = new HashMap<>();

        JsonNode contests = transaction.path("count").path("election").path("contests").path("contests");
        for (JsonNode contest : contests) {
            JsonNode selections = contest.path("totalVotes").path("selections");
            for (JsonNode selection : selections) {
                String partyName = selection.path("affiliationIdentifier").path("registeredName").asText();
                int votes = selection.path("validVotes").asInt(0);
                cityVotes.put(partyName, cityVotes.getOrDefault(partyName, 0) + votes);
            }
        }

        return cityVotes;
    }

    public Map<String, Double> normalizeVotes(Map<String, Integer> votes) {
        int totalVotes = votes.values().stream().mapToInt(Integer::intValue).sum();
        Map<String, Double> normalizedVotes = new HashMap<>();

        for (Map.Entry<String, Integer> entry : votes.entrySet()) {
            double percentage = (entry.getValue() * 100.0) / totalVotes;
            normalizedVotes.put(entry.getKey(), percentage);
        }

        return normalizedVotes;
    }

    public double calculateManhattanDistance(Map<String, Double> overallPercentages, Map<String, Double> cityPercentages) {
        double distance = 0.0;

        Set<String> allParties = new HashSet<>(overallPercentages.keySet());
        allParties.addAll(cityPercentages.keySet());

        for (String party : allParties) {
            double overallPercentage = overallPercentages.getOrDefault(party, 0.0);
            double cityPercentage = cityPercentages.getOrDefault(party, 0.0);
            distance += Math.abs(overallPercentage - cityPercentage);
        }

        return distance;
    }

    public List<Map<String, Object>> calculateCityDistances(JsonNode root, Map<String, Integer> overallResults) {
        Map<String, Double> overallPercentages = normalizeVotes(overallResults);

        List<Map<String, Object>> cityDistances = new ArrayList<>();

        for (JsonNode transaction : root) {
            String cityName = getCityName(transaction);
            if (cityName != null && !cityName.isEmpty()) {
                Map<String, Integer> cityVotes = calculateCityVotes(transaction);
                Map<String, Double> cityPercentages = normalizeVotes(cityVotes);

                double distance = calculateManhattanDistance(overallPercentages, cityPercentages);

                Map<String, Object> cityResult = new HashMap<>();
                cityResult.put("cityName", cityName);
                cityResult.put("distance", distance);
                cityDistances.add(cityResult);
            }
        }

        return cityDistances;
    }

    public String getCityName(JsonNode transaction) {
        return transaction.path("managingAuthority").path("authorityIdentifier").path("value").asText();
    }
}
