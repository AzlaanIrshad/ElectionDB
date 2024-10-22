package com.example.parser.election.electiondefinition;

public class ElectionEvent {
    private String id;
    private String name;

    // Constructor
    public ElectionEvent(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters en Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
