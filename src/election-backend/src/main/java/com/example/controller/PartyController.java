package com.example.controller;

import com.example.service.PartyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/party-result")
public class PartyController {

    private final PartyService partyService;

    public PartyController(PartyService partyService) {
        this.partyService = partyService;
    }

    @GetMapping("/{partyId}")
    public ResponseEntity<Object> getSpecifiedParty(
            @PathVariable String partyId,
            @RequestParam(value = "years", required = false) List<Integer> years) {

        if (years == null || years.isEmpty()) {
            years = List.of(2023);
        }

        Map<String, Object> results = partyService.getPartyResults(partyId, years);
        return ResponseEntity.ok(results);
    }
}
