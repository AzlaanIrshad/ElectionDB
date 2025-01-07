package com.example.controller;

import com.example.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/2023")
    public ResponseEntity<Object> searchParties(@RequestParam(value = "query") String query) {
        try {
            var parties = searchService.searchParties(query);
            return ResponseEntity.ok(parties);
        } catch (IOException e) {
            String errorMessage = "Error: resultatenbestand niet gevonden voor 2023.";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }
}
