package com.example.parser.election.canidate;

public class ListData {
    private String listName;
    private int numberOfCandidates;

    // Constructor
    public ListData(String listName, int numberOfCandidates) {
        this.listName = listName;
        this.numberOfCandidates = numberOfCandidates;
    }

    // Getters en Setters
    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public int getNumberOfCandidates() {
        return numberOfCandidates;
    }

    public void setNumberOfCandidates(int numberOfCandidates) {
        this.numberOfCandidates = numberOfCandidates;
    }
}
