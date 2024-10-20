package com.example.controller;

import com.example.entity.Candidate;
import com.example.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CandidateController {

    @Autowired
    private CandidateRepository candidateRepository;

    @GetMapping("/candidates")
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }
}
