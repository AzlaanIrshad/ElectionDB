package com.example.parser.controller;

import com.example.parser.election.ElectionData;
import com.example.parser.service.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/elections")
public class ElectionController {

    @Autowired
    private ElectionService electionService;

    @GetMapping("/parse")
    public List<ElectionData> parseElections() {
        System.out.println("Parsing elections triggered");
        return electionService.parseElections();
    }
}
