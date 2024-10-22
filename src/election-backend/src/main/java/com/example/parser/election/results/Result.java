package com.example.parser.election.results;

public class Result {
    private String candidateId;
    private int voteCount;

    // Constructor
    public Result(String candidateId, int voteCount) {
        this.candidateId = candidateId;
        this.voteCount = voteCount;
    }

    // Getters en Setters
    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}
