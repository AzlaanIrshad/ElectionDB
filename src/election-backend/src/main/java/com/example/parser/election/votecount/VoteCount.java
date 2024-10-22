package com.example.parser.election.votecount;

public class VoteCount {
    private String candidateId;
    private int validVotes;
    private int rejectedVotes;

    // Constructor
    public VoteCount(String candidateId, int validVotes, int rejectedVotes) {
        this.candidateId = candidateId;
        this.validVotes = validVotes;
        this.rejectedVotes = rejectedVotes;
    }

    // Getters en Setters
    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public int getValidVotes() {
        return validVotes;
    }

    public void setValidVotes(int validVotes) {
        this.validVotes = validVotes;
    }

    public int getRejectedVotes() {
        return rejectedVotes;
    }

    public void setRejectedVotes(int rejectedVotes) {
        this.rejectedVotes = rejectedVotes;
    }
}