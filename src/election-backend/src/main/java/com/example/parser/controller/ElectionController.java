package com.example.parser.controller;

import com.example.parser.model.ElectionResult;
import com.example.parser.service.ElectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/election")
public class ElectionController {

    private static final Logger logger = LoggerFactory.getLogger(ElectionController.class);
    private final ElectionService electionService;

    @Autowired
    public ElectionController(ElectionService electionService) {
        this.electionService = electionService;
        logger.info("ElectionController initialized.");
    }

    @GetMapping("/result")
    public ElectionResult getElectionResult() {
        logger.info("Received request for election results.");

        ElectionResult electionResult = electionService.parseXmlToJson();

        if (electionResult == null) {
            logger.warn("No valid election result data found. Responding with empty data.");
        } else {
            logger.info("Election results retrieved successfully. Sending response.");
        }

        return electionResult;
    }
}
