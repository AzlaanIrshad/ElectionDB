package com.example.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SeatService {

    private static final int TOTAL_SEATS = 150; // Totaal Zetels

    /**
     * Bereken de zetelverdeling voor de opgegeven jaren.
     *
     * @param years Lijst van jaren waarvoor de zetelverdeling berekend moet worden.
     * @return Map van zetelverdelingen per jaar.
     * @throws IOException als er een probleem is met het lezen van de bestanden.
     */
    public Map<String, Map<String, Integer>> calculateSeats(List<Integer> years) throws IOException {
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

            }
        }

        return seatResultsByYear;
    }
}