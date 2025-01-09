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

    @GetMapping("/farthest-cities")
    public ResponseEntity<Object> getFarthestCities(
            @RequestParam(value = "year", required = false, defaultValue = "2023") Integer year) {

        String filePath = "ParsedJson/" + year + "/tellingen_results.json";

        try (InputStream inputStream = new ClassPathResource(filePath).getInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(inputStream);

            Map<String, Integer> overallResults = calculateOverallVotes(root);
            List<Map<String, Object>> cityDistances = calculateCityDistances(root, overallResults);

            cityDistances.sort((c1, c2) -> Double.compare((double) c2.get("distance"), (double) c1.get("distance")));

            List<Map<String, Object>> farthestCities = cityDistances.stream().limit(3).toList();

            if (farthestCities.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No cities found for the specified year.");
            }

            return ResponseEntity.ok(farthestCities);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data for the year " + year + " is not available.");
        }
    }

    @GetMapping("/closest-cities")
    public ResponseEntity<Object> getClosestCities(
            @RequestParam(value = "year", required = false, defaultValue = "2023") Integer year) {

        String filePath = "ParsedJson/" + year + "/tellingen_results.json";

        try (InputStream inputStream = new ClassPathResource(filePath).getInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(inputStream);

            Map<String, Integer> overallResults = calculateOverallVotes(root);
            List<Map<String, Object>> cityDistances = calculateCityDistances(root, overallResults);

            cityDistances.sort(Comparator.comparingDouble(city -> (double) city.get("distance")));

            List<Map<String, Object>> topCities = cityDistances.stream().limit(3).toList();

            if (topCities.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No cities found for the specified year.");
            }

            return ResponseEntity.ok(topCities);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data for the year " + year + " is not available.");
        }
    }

    @GetMapping("/city-percentage-deviation")
    public ResponseEntity<Object> getCityPercentageDeviation(
            @RequestParam(value = "year", required = false, defaultValue = "2023") Integer year) {

        String filePath = "ParsedJson/" + year + "/tellingen_results.json";

        try (InputStream inputStream = new ClassPathResource(filePath).getInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(inputStream);

            Map<String, Integer> overallResults = calculateOverallVotes(root);
            Map<String, Double> overallPercentages = normalizeVotes(overallResults);

            List<Map<String, Object>> cityDeviations = new ArrayList<>();

            for (JsonNode transaction : root) {
                String cityName = getCityName(transaction);
                if (cityName != null && !cityName.isEmpty()) {
                    Map<String, Integer> cityVotes = calculateCityVotes(transaction);
                    Map<String, Double> cityPercentages = normalizeVotes(cityVotes);

                    double deviation = calculateManhattanDistance(overallPercentages, cityPercentages);

                    Map<String, Object> cityResult = new HashMap<>();
                    cityResult.put("cityName", cityName);
                    cityResult.put("percentageDeviation", deviation);
                    cityDeviations.add(cityResult);
                }
            }

            cityDeviations.sort(Comparator.comparingDouble(city -> (double) city.get("percentageDeviation")));

            return ResponseEntity.ok(cityDeviations);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data for the year " + year + " is not available.");
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
                    overallVotes.put(partyName, overallVotes.getOrDefault(partyName, 0) + votes);
                }
            }
        }

        return overallVotes;
    }

    private Map<String, Integer> calculateCityVotes(JsonNode transaction) {
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

    private Map<String, Double> normalizeVotes(Map<String, Integer> votes) {
        int totalVotes = votes.values().stream().mapToInt(Integer::intValue).sum();
        Map<String, Double> normalizedVotes = new HashMap<>();

        for (Map.Entry<String, Integer> entry : votes.entrySet()) {
            double percentage = (entry.getValue() * 100.0) / totalVotes;
            normalizedVotes.put(entry.getKey(), percentage);
        }

        return normalizedVotes;
    }

    private double calculateManhattanDistance(Map<String, Double> overallPercentages, Map<String, Double> cityPercentages) {
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

    private String getCityName(JsonNode transaction) {
        return transaction.path("managingAuthority").path("authorityIdentifier").path("value").asText();
    }

    private List<Map<String, Object>> calculateCityDistances(JsonNode root, Map<String, Integer> overallResults) {
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
}
