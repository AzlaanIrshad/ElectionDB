package com.example.parser.election.canidate;

import java.util.Date;

public class Election {
    private String id;
    private String name;
    private Date date;

    // Constructor
    public Election(String id, String name, Date date) {
        this.id = id;
        this.name = name;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
