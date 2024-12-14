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

    @GetMapping("/city-leading-party-votes")
    public ResponseEntity<Object> getCityLeadingPartyVotes(
            @RequestParam(value = "year", required = false, defaultValue = "2023") Integer year) {

        String filePath = "ParsedJson/" + year + "/tellingen_results.json";

        try (InputStream inputStream = new ClassPathResource(filePath).getInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(inputStream);

            List<Map<String, Object>> leadingPartyVotes = calculateCityLeadingPartyVotes(root);

            Map<String, Object> response = new HashMap<>();
            response.put("year", year);
            response.put("cities", leadingPartyVotes);

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: resultatenbestand niet gevonden of een fout opgetreden.");
        }
    }
//dit is voor total votes per year voor mr manhattan

private List<Map<String, Object>> calculateCityLeadingPartyVotes(JsonNode root) {
    List<Map<String, Object>> cityLeadingPartyVotes = new ArrayList<>();

    for (JsonNode transaction : root) {
        String cityName = getCityName(transaction);
        if (cityName != null && !cityName.isEmpty()) {
            List<Map<String, Object>> partyVotes = getPartyVotes(transaction.path("count").path("election").path("contests").path("contests"));

            // Zoek de leidende partij
            if (!partyVotes.isEmpty()) {
                Map<String, Object> leadingParty = partyVotes.getFirst();

                Map<String, Object> cityResult = new HashMap<>();
                cityResult.put("cityName", cityName);
                cityResult.put("leadingParty", leadingParty.get("partyName"));
                cityResult.put("votes", leadingParty.get("validVotes"));
                cityLeadingPartyVotes.add(cityResult);
            }
        }
    }

    return cityLeadingPartyVotes;
}

    @GetMapping("/total-votes")
    public ResponseEntity<Object> getTotalVotes(
            @RequestParam(value = "year", required = false, defaultValue = "2023") Integer year) {

        String filePath = "ParsedJson/" + year + "/tellingen_results.json";

        try (InputStream inputStream = new ClassPathResource(filePath).getInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(inputStream);

            int totalVotes = calculateTotalVotes(root);

            Map<String, Object> response = new HashMap<>();
            response.put("year", year);
            response.put("totalVotes", totalVotes);

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: resultatenbestand niet gevonden of een fout opgetreden.");
        }
    }

    @GetMapping("/total-votes-all")
    public ResponseEntity<Object> getTotalVotesAll(
            @RequestParam(value = "years", required = false) List<Integer> years) {

        if (years == null || years.isEmpty()) {
            years = Arrays.asList(2023, 2021, 2017, 2012, 2010);
        }

        Map<Integer, Integer> totalVotesPerYear = new HashMap<>();

        for (Integer year : years) {
            String filePath = "ParsedJson/" + year + "/tellingen_results.json";

            try (InputStream inputStream = new ClassPathResource(filePath).getInputStream()) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode root = objectMapper.readTree(inputStream);

                int totalVotes = calculateTotalVotes(root);
                totalVotesPerYear.put(year, totalVotes);
            } catch (IOException e) {
                totalVotesPerYear.put(year, 0); // Set to 0 if the file is not found or another error occurs
            }
        }

        return ResponseEntity.ok(totalVotesPerYear);
    }

    @GetMapping("/city-total-votes")
    public ResponseEntity<Object> getCityTotalVotes(
            @RequestParam(value = "year", required = false, defaultValue = "2023") Integer year) {

        String filePath = "ParsedJson/" + year + "/tellingen_results.json";

        try (InputStream inputStream = new ClassPathResource(filePath).getInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(inputStream);

            List<Map<String, Object>> cityVotes = calculateCityTotalVotes(root);

            Map<String, Object> response = new HashMap<>();
            response.put("year", year);
            response.put("cities", cityVotes);

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: resultatenbestand niet gevonden of een fout opgetreden.");
        }
    }
    private int calculateTotalVotes(JsonNode root) {
        List<Map<String, Object>> partyVotes = new ArrayList<>();

        for (JsonNode transaction : root) {
            JsonNode contests = transaction.path("count").path("election").path("contests").path("contests");
            partyVotes.addAll(getPartyVotes(contests));
        }
        return partyVotes.stream()
                .mapToInt(party -> (int) party.get("validVotes"))
                .sum();
    }


    private List<Map<String, Object>> calculateCityTotalVotes(JsonNode root) {
        List<Map<String, Object>> cityVotes = new ArrayList<>();

        for (JsonNode transaction : root) {
            String cityName = getCityName(transaction);
            if (cityName != null && !cityName.isEmpty()) {
                List<Map<String, Object>> partyVotes = getPartyVotes(transaction.path("count").path("election").path("contests").path("contests"));

                int totalVotes = partyVotes.stream()
                        .mapToInt(party -> (int) party.get("validVotes"))
                        .sum();

                Map<String, Object> cityResult = new HashMap<>();
                cityResult.put("cityName", cityName);
                cityResult.put("totalVotes", totalVotes);
                cityVotes.add(cityResult);
            }
        }

        return cityVotes;
    }

    private List<Map<String, Object>> getPartyVotes(JsonNode contests) {
        List<Map<String, Object>> partyVotes = new ArrayList<>();

        for (JsonNode contest : contests) {
            JsonNode selections = contest.path("totalVotes").path("selections");
            for (JsonNode selection : selections) {
                String partyName = selection.path("affiliationIdentifier").path("registeredName").asText();
                int validVotes = selection.path("validVotes").asInt(0);

                if (!partyName.isEmpty()) {
                    Map<String, Object> partyData = new HashMap<>();
                    partyData.put("partyName", partyName);
                    partyData.put("validVotes", validVotes);
                    partyVotes.add(partyData);
                }
            }
        }
        partyVotes.sort((p1, p2) -> Integer.compare((int) p2.get("validVotes"), (int) p1.get("validVotes")));
        return partyVotes;

    }
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
        List<Map<String, Object>> partyVotes = new ArrayList<>();

        for (JsonNode selection : selections) {
            String partyName = selection.path("affiliationIdentifier").path("registeredName").asText();
            int validVotes = selection.path("validVotes").asInt(0);

            if (partyName.isEmpty()) continue;

            if (parties == null || parties.contains(partyName)) {
                Map<String, Object> partyData = new HashMap<>();
                partyData.put("partyName", partyName);
                partyData.put("validVotes", validVotes);
                partyVotes.add(partyData);
            }
        }

        partyVotes.sort((p1, p2) -> Integer.compare((int) p2.get("validVotes"), (int) p1.get("validVotes")));

        if (!partyVotes.isEmpty()) {
            partyVotes.removeFirst();
        }

        String leadingParty = null;
        int maxVotes = 0;

        for (Map<String, Object> partyData : partyVotes) {
            int validVotes = (int) partyData.get("validVotes");
            if (validVotes > maxVotes) {
                leadingParty = (String) partyData.get("partyName");
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
