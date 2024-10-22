package com.example.parser.election.votecount;

public class PollingStation {
    private String id;
    private String name;
    private String location;

    // Constructor
    public PollingStation(String id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
