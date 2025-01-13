package com.example.controller;

import com.example.service.ComparisonService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comparison")
public class ComparisonController {

    @Autowired
    private ComparisonService comparisonService;

    @GetMapping("/farthest-cities")
    public ResponseEntity<Object> getFarthestCities(
            @RequestParam(value = "year", required = false, defaultValue = "2023") Integer year) {

        String filePath = "ParsedJson/" + year + "/tellingen_results.json";

        try {
            JsonNode root = comparisonService.loadElectionData(filePath);

            Map<String, Integer> overallResults = comparisonService.calculateOverallVotes(root);
            List<Map<String, Object>> cityDistances = comparisonService.calculateCityDistances(root, overallResults);

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

        try {
            JsonNode root = comparisonService.loadElectionData(filePath);

            Map<String, Integer> overallResults = comparisonService.calculateOverallVotes(root);
            List<Map<String, Object>> cityDistances = comparisonService.calculateCityDistances(root, overallResults);

            cityDistances.sort((c1, c2) -> Double.compare((double) c1.get("distance"), (double) c2.get("distance")));
            List<Map<String, Object>> closestCities = cityDistances.stream().limit(3).toList();

            if (closestCities.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No cities found for the specified year.");
            }

            return ResponseEntity.ok(closestCities);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data for the year " + year + " is not available.");
        }
    }
    @GetMapping("/city-percentage-deviation")
    public ResponseEntity<Object> getCityPercentageDeviation(
            @RequestParam(value = "year", required = false, defaultValue = "2023") Integer year) {

        String filePath = "ParsedJson/" + year + "/tellingen_results.json";

        try {
            JsonNode root = comparisonService.loadElectionData(filePath);

            Map<String, Integer> overallResults = comparisonService.calculateOverallVotes(root);
            List<Map<String, Object>> cityDeviations = comparisonService.calculateCityPercentageDeviations(root, overallResults);

            if (cityDeviations.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No cities found for the specified year.");
            }

            return ResponseEntity.ok(cityDeviations);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data for the year " + year + " is not available.");
        }
    }
}
