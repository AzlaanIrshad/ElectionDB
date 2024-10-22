package com.example.parser.election.results;

public class PersonName {
    private String firstName;
    private String lastName;
    private String namePrefix;

    // Constructor
    public PersonName(String firstName, String lastName, String namePrefix) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.namePrefix = namePrefix;
    }

    // Getters en Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNamePrefix() {
        return namePrefix;
    }

    public void setNamePrefix(String namePrefix) {
        this.namePrefix = namePrefix;
    }
}
