package com.example.parser.election.electiondefinition;

import java.util.List;

public class ElectionTree {
    private List<Election> elections;

    // Constructor
    public ElectionTree(List<Election> elections) {
        this.elections = elections;
    }

    // Getter en Setter
    public List<Election> getElections() {
        return elections;
    }

    public void setElections(List<Election> elections) {
        this.elections = elections;
    }
}
