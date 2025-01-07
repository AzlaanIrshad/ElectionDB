package com.example.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class CandidateService {

    public JsonNode getCandidatesFromFile(int year) throws IOException {
        String filePath = "ParsedJson/" + year + "/kandidatenlijsten_results.json";
        try (InputStream inputStream = new ClassPathResource(filePath).getInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(inputStream);
        }
    }

    public JsonNode findCandidateById(JsonNode root, int candidateId) {
        for (JsonNode transaction : root) {
            JsonNode contests = transaction.path("candidateList").path("election").path("contests");
            for (JsonNode contest : contests) {
                JsonNode affiliations = contest.path("affiliations");
                for (JsonNode affiliation : affiliations) {
                    JsonNode candidates = affiliation.path("candidates");
                    for (JsonNode candidate : candidates) {
                        int id = candidate.path("candidateIdentifier").path("id").asInt();
                        if (id == candidateId) {
                            return candidate;
                        }
                    }
                }
            }
        }
        return null;
    }
}