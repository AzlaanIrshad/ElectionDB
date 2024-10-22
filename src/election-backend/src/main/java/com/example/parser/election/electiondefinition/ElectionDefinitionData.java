package com.example.parser.election.electiondefinition;

public class ElectionDefinitionData {
    private String electionType;
    private String electionDate;
    private String electionSubcategory;

    // Constructor
    public ElectionDefinitionData(String electionType, String electionDate, String electionSubcategory) {
        this.electionType = electionType;
        this.electionDate = electionDate;
        this.electionSubcategory = electionSubcategory;
    }

    // Getters en Setters
    public String getElectionType() {
        return electionType;
    }

    public void setElectionType(String electionType) {
        this.electionType = electionType;
    }

    public String getElectionDate() {
        return electionDate;
    }

    public void setElectionDate(String electionDate) {
        this.electionDate = electionDate;
    }

    public String getElectionSubcategory() {
        return electionSubcategory;
    }

    public void setElectionSubcategory(String electionSubcategory) {
        this.electionSubcategory = electionSubcategory;
    }
}
