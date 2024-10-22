package com.example.parser.election.canidate;

public class Candidate {
    private String id;
    private String fullName;

    // Constructor
    public Candidate(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    // Getters en Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
