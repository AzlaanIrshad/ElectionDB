package com.example.parser.election;

import java.util.ArrayList;
import java.util.List;

public class Party {

    private String name;
    private int totalVotes;
    private List<Candidate> candidates;

    public Party() {
        this.candidates = new ArrayList<>();
    }

    public Party(String name, int totalVotes) {
        this.name = name;
        this.totalVotes = totalVotes;
        this.candidates = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(int totalVotes) {
        this.totalVotes = totalVotes;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    public void addCandidate(Candidate candidate) {
        this.candidates.add(candidate);
    }
}
