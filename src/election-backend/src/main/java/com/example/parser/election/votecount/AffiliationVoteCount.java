package com.example.parser.election.votecount;

public class AffiliationVoteCount {
    private String affiliationId;
    private int voteCount;

    // Constructor
    public AffiliationVoteCount(String affiliationId, int voteCount) {
        this.affiliationId = affiliationId;
        this.voteCount = voteCount;
    }

    // Getters en Setters
    public String getAffiliationId() {
        return affiliationId;
    }

    public void setAffiliationId(String affiliationId) {
        this.affiliationId = affiliationId;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}
