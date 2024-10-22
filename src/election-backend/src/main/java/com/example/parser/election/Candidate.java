package com.example.parser.election;

public class Candidate {

    private String shortCode;
    private int validVotes;
    private String name;

    public Candidate() {}

    public Candidate(String shortCode, int validVotes) {
        this.shortCode = shortCode;
        this.validVotes = validVotes;
    }

    public Candidate(String shortCode, int validVotes, String name) {
        this.shortCode = shortCode;
        this.validVotes = validVotes;
        this.name = name;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public int getValidVotes() {
        return validVotes;
    }

    public void setValidVotes(int validVotes) {
        this.validVotes = validVotes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVotes() {
        return validVotes;
    }

    public void setVotes(int votes) {
        this.validVotes = votes;
    }
}
