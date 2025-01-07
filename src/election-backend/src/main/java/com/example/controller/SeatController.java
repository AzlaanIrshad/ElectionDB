package com.example.controller;

import com.example.service.SeatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Controller voor het berekenen van de zetelverdeling op basis van verkiezingsresultaten.
 */
@RestController
@RequestMapping("/seat-allocation")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

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

        try {
            Map<String, Map<String, Integer>> seatResultsByYear = seatService.calculateSeats(years);
            return ResponseEntity.ok(seatResultsByYear);
        } catch (IOException e) {
            String errorMessage = "Error: resultatenbestand niet gevonden voor opgegeven jaren.";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }
}