package com.example.parser.election.electiondefinition;

import java.util.List;

public class RegisteredParties {
    private List<RegisteredParty> parties;

    // Constructor
    public RegisteredParties(List<RegisteredParty> parties) {
        this.parties = parties;
    }

    // Getter en Setter
    public List<RegisteredParty> getParties() {
        return parties;
    }

    public void setParties(List<RegisteredParty> parties) {
        this.parties = parties;
    }
}
