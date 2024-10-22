package com.example.parser.election.canidate;

public class CandidateData {
    private String candidateIdentifier;
    private String affiliation;
    private String fullName;
    private String qualifyingAddress;

    // Constructor
    public CandidateData(String candidateIdentifier, String affiliation, String fullName, String qualifyingAddress) {
        this.candidateIdentifier = candidateIdentifier;
        this.affiliation = affiliation;
        this.fullName = fullName;
        this.qualifyingAddress = qualifyingAddress;
    }

    // Getters en Setters
    public String getCandidateIdentifier() {
        return candidateIdentifier;
    }

    public void setCandidateIdentifier(String candidateIdentifier) {
        this.candidateIdentifier = candidateIdentifier;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getQualifyingAddress() {
        return qualifyingAddress;
    }

    public void setQualifyingAddress(String qualifyingAddress) {
        this.qualifyingAddress = qualifyingAddress;
    }
}