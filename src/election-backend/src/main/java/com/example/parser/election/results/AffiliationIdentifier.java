package com.example.parser.election.results;

public class AffiliationIdentifier {
    private String id;
    private String registeredName;

    // Constructor
    public AffiliationIdentifier(String id, String registeredName) {
        this.id = id;
        this.registeredName = registeredName;
    }

    // Getters en Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegisteredName() {
        return registeredName;
    }

    public void setRegisteredName(String registeredName) {
        this.registeredName = registeredName;
    }
}
