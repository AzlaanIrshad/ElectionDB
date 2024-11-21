package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.ClassPathResource;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class SearchController {

    @GetMapping("/api/search")
    public List<Map<String, Object>> searchElectionResults(@RequestParam("q") String query) throws Exception {
        Path path = new ClassPathResource("election_results.json").getFile().toPath();
        String jsonContent = Files.readString(path);

        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> electionResults = objectMapper.readValue(jsonContent, new TypeReference<>() {});

        String lowerCaseQuery = query.toLowerCase();
        return electionResults.stream()
                .filter(result -> {
                    String candidateName = (String) result.get("candidateName");
                    String partyName = (String) result.get("partyName");
                    return (candidateName != null && candidateName.toLowerCase().contains(lowerCaseQuery)) ||
                            (partyName != null && partyName.toLowerCase().contains(lowerCaseQuery));
                })
                .collect(Collectors.toList());
    }
}
