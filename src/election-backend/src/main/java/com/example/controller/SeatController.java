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

/**
 * Controller voor het berekenen van de zetelverdeling op basis van verkiezingsresultaten.
 */
@RestController
@RequestMapping("/seat-allocation")
public class SeatController {

    private static final int TOTAL_SEATS = 150; // Totaal Zetels

    /**
     * Bereken de zetelverdeling voor opgegeven jaren.
     *
     * @param years Lijst van jaren waarvoor de zetelverdeling berekend moet worden (optioneel).
     * @return ResponseEntity met de zetelverdeling per jaar.
     */
    @GetMapping
    public ResponseEntity<Object> calculateSeats(
            @RequestParam(value = "years", required = false) List<Integer> years) {

        if (years == null || years.isEmpty()) {
            years = List.of(2023);
        }

        Map<String, Map<String, Integer>> seatResultsByYear = new HashMap<>();

        for (Integer year : years) {
            String filePath = "ParsedJson/" + year + "/tellingen_results.json";

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

                int totalVotes = partyVotes.values().stream().mapToInt(Integer::intValue).sum();
                int electoralQuota = totalVotes / TOTAL_SEATS;

                Map<String, Integer> seats = new HashMap<>();
                Map<String, Double> remainders = new HashMap<>();
                for (Map.Entry<String, Integer> entry : partyVotes.entrySet()) {
                    String party = entry.getKey();
                    int votes = entry.getValue();
                    int initialSeats = votes / electoralQuota;
                    seats.put(party, initialSeats);
                    remainders.put(party, (double) votes / (initialSeats + 1));
                }

                int allocatedSeats = seats.values().stream().mapToInt(Integer::intValue).sum();
                while (allocatedSeats < TOTAL_SEATS) {
                    String topParty = remainders.entrySet().stream()
                            .max(Map.Entry.comparingByValue())
                            .get()
                            .getKey();

                    seats.put(topParty, seats.getOrDefault(topParty, 0) + 1);

                    int newSeats = seats.get(topParty);
                    remainders.put(topParty, (double) partyVotes.get(topParty) / (newSeats + 1));

                    allocatedSeats++;
                }

                seatResultsByYear.put(year.toString(), seats);

            } catch (IOException e) {
                String errorMessage = "Error: resultatenbestand niet gevonden voor jaar " + year + ".";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }
        }

        return ResponseEntity.ok(seatResultsByYear);
    }
}
