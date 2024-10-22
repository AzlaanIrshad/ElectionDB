package com.example.parser.election.results;

public class CanonicalizationModule {
    private String method;

    // Constructor
    public CanonicalizationModule(String method) {
        this.method = method;
    }

    // Getter en Setter
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
