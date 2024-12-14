package com.example.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class ElectionResultService {

    public Map<String, Map<String, Integer>> getTotalVotesPerYears(List<Integer> years) throws IOException {
        Map<String, Map<String, Integer>> resultsByYear = new HashMap<>();

        for (Integer year : years) {
            String basePath = "ParsedJson/";
            String filePath = basePath + year + "/tellingen_results.json";

            try (InputStream inputStream = new ClassPathResource(filePath).getInputStream()) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode root = objectMapper.readTree(inputStream);

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
            }
        }

        return resultsByYear;
    }

    public Map<String, List<Map<String, Object>>> getFilteredResults(List<Integer> years) throws IOException {
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
                    parties.sort((p1, p2) -> Integer.compare((int) p2.get("totalVotes"), (int) p1.get("totalVotes")));

                    Map<String, Object> chartData = prepareChartData(parties);
                    citiesData.add(Map.of(
                            "cityId", cityId,
                            "cityName", cityName,
                            "totalVotes", parties.stream().mapToInt(p -> (int) p.get("totalVotes")).sum(),
                            "parties", parties,
                            "chartData", chartData
                    ));
                }

                resultsByYear.put(year.toString(), citiesData);
            }
        }

        return resultsByYear;
    }

    private Map<String, Object> prepareChartData(List<Map<String, Object>> parties) {
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
                                "backgroundColor", labels.stream().map(label -> "#06b6d4").toArray(),
                                "borderColor", labels.stream().map(label -> "rgba(0,0,0,1)").toArray(),
                                "borderWidth", 1
                        )
                )
        );
    }
}
