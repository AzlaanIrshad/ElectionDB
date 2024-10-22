package com.example.parser.election.results;

import java.util.List;

public class ElectionResultData {
    private String electionType;
    private List<AffiliationIdentifier> affiliations;
    private List<CandidateFullName> candidates;
    private List<Result> results;

    // Constructor
    public ElectionResultData(String electionType, List<AffiliationIdentifier> affiliations, List<CandidateFullName> candidates, List<Result> results) {
        this.electionType = electionType;
        this.affiliations = affiliations;
        this.candidates = candidates;
        this.results = results;
    }

    // Getters en Setters
    public String getElectionType() {
        return electionType;
    }

    public void setElectionType(String electionType) {
        this.electionType = electionType;
    }

    public List<AffiliationIdentifier> getAffiliations() {
        return affiliations;
    }

    public void setAffiliations(List<AffiliationIdentifier> affiliations) {
        this.affiliations = affiliations;
    }

    public List<CandidateFullName> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<CandidateFullName> candidates) {
        this.candidates = candidates;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
