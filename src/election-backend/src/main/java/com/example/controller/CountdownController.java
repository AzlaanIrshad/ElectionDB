package com.example.controller;

import com.example.service.CountdownService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/countdown")
public class CountdownController {

    private final CountdownService countdownService;

    public CountdownController(CountdownService countdownService) {
        this.countdownService = countdownService;
    }

    @GetMapping
    public CountdownService.CountdownResponse getCountdownTime() {
        return countdownService.getRemainingTime();
    }
}