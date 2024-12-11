package com.example.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/election-results")
public class ElectionController {

    @GetMapping
    public ResponseEntity<Object> getTotalVotesPerYears(
            @RequestParam(value = "years", required = false) List<Integer> years) {

        // standaardjaar 2023
        if (years == null || years.isEmpty()) {
            years = List.of(2023);
        }

        Map<String, Map<String, Integer>> resultsByYear = new HashMap<>();

        for (Integer year : years) {
            String basePath = "ParsedJson/";
            String filePath = basePath + year + "/tellingen_results.json";

            try (InputStream inputStream = new ClassPathResource(filePath).getInputStream()) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode root = objectMapper.readTree(inputStream);

                // Totale stemmen per partij berekenen
                Map<String, Integer> partyVotes = new HashMap<>();
                for (JsonNode transaction : root) {
                    JsonNode contests = transaction.path("count").path("election").path("contests").path("contests");
                    for (JsonNode contest : contests) {
                        JsonNode selections = contest.path("totalVotes").path("selections");
                        for (JsonNode selection : selections) {
                            String party = selection.path("affiliationIdentifier").path("registeredName").asText();
                            int votes = selection.path("validVotes").asInt(0);

                            if (party != null && !party.isEmpty()) {
                                partyVotes.put(party, partyVotes.getOrDefault(party, 0) + votes);
                            }
                        }
                    }
                }

                resultsByYear.put(year.toString(), partyVotes);

            } catch (IOException e) {
                String errorMessage = "Error: resultatenbestand niet gevonden voor jaar " + year + ".";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }
        }

        return ResponseEntity.ok(resultsByYear);
    }
        @GetMapping("/all")
        public ResponseEntity<Object> getFilteredResults(
                @RequestParam(value = "years", required = false) List<Integer> years) {

            if (years == null || years.isEmpty()) {
                years = List.of(2023); // default year
            }

            Map<String, List<Map<String, Object>>> resultsByYear = new HashMap<>();

            for (Integer year : years) {
                String basePath = "ParsedJson/";
                String filePath = basePath + year + "/tellingen_results.json";

                try (InputStream inputStream = new ClassPathResource(filePath).getInputStream()) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode root = objectMapper.readTree(inputStream);
                    List<Map<String, Object>> citiesData = new ArrayList<>();

                    for (JsonNode transaction : root) {
                        String cityName = transaction.path("managingAuthority").path("authorityIdentifier").path("value").asText();
                        String cityId = transaction.path("managingAuthority").path("authorityIdentifier").path("id").asText();

                        Map<String, Map<String, Object>> partiesMap = new HashMap<>();
                        JsonNode contests = transaction.path("count").path("election").path("contests").path("contests");

                        for (JsonNode contest : contests) {
                            JsonNode selections = contest.path("totalVotes").path("selections");
                            for (JsonNode selection : selections) {
                                String partyId = selection.path("affiliationIdentifier").path("id").asText();
                                String partyName = selection.path("affiliationIdentifier").path("registeredName").asText("Unknown Party");
                                int validVotes = selection.path("validVotes").asInt(0);

                                partiesMap.putIfAbsent(partyId, new HashMap<>());
                                Map<String, Object> partyData = partiesMap.get(partyId);

                                partyData.put("partyId", partyId);
                                partyData.put("partyName", partyName);
                                partyData.put("totalVotes", (int) partyData.getOrDefault("totalVotes", 0) + validVotes);

                                if (selection.has("candidate")) {
                                    Map<String, Object> candidate = Map.of(
                                            "id", selection.path("candidate").path("candidateIdentifier").path("id").asText(),
                                            "validVotes", validVotes
                                    );
                                    partiesMap.get(partyId).putIfAbsent("candidates", new ArrayList<>());
                                    ((List<Map<String, Object>>) partyData.get("candidates")).add(candidate);
                                }
                            }
                        }

                        List<Map<String, Object>> parties = new ArrayList<>(partiesMap.values());
                        if (!parties.isEmpty()) {
                            parties.removeFirst(); // Removes the first party in the list
                        }
                        int totalVotes = parties.stream().mapToInt(p -> (int) p.get("totalVotes")).sum();

                        Map<String, Object> chartData = prepareChartData(parties);

                        citiesData.add(Map.of(
                                "cityId", cityId,
                                "cityName", cityName,
                                "totalVotes", totalVotes,
                                "parties", parties,
                                "chartData", chartData
                        ));
                    }

                    resultsByYear.put(year.toString(), citiesData);

                } catch (IOException e) {
                    String errorMessage = "Error: resultatenbestand niet gevonden voor jaar " + year + ".";
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
                }
            }

            return ResponseEntity.ok(resultsByYear);
        }

        private Map<String, Object> prepareChartData(List<Map<String, Object>> parties) {
            // Sort parties by totalVotes in descending order
            parties.sort((p1, p2) -> Integer.compare((int) p2.get("totalVotes"), (int) p1.get("totalVotes")));

            List<String> labels = new ArrayList<>();
            List<Integer> data = new ArrayList<>();

            for (Map<String, Object> party : parties) {
                labels.add((String) party.get("partyName"));
                data.add((Integer) party.get("totalVotes"));
            }

            return Map.of(
                    "labels", labels,
                    "datasets", List.of(
                            Map.of(
                                    "label", "Totaal Stemmen",
                                    "data", data,
                                    "borderWidth", 1
                            )
                    )
            );
        }
    }