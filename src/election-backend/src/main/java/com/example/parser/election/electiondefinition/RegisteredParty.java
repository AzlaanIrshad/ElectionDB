package com.example.parser.election.electiondefinition;

public class RegisteredParty {
    private String id;
    private String registeredAppellation;
    private String committeeCategory;

    // Constructor
    public RegisteredParty(String id, String registeredAppellation, String committeeCategory) {
        this.id = id;
        this.registeredAppellation = registeredAppellation;
        this.committeeCategory = committeeCategory;
    }

    // Getters en Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegisteredAppellation() {
        return registeredAppellation;
    }

    public void setRegisteredAppellation(String registeredAppellation) {
        this.registeredAppellation = registeredAppellation;
    }

    public String getCommitteeCategory() {
        return committeeCategory;
    }

    public void setCommitteeCategory(String committeeCategory) {
        this.committeeCategory = committeeCategory;
    }
}
