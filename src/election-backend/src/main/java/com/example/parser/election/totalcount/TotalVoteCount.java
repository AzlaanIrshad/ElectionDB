package com.example.parser.election.totalcount;

public class TotalVoteCount {
    private int castVotes;
    private int totalCounted;
    private int rejectedVotes;
    private int validVotes;

    // Constructor
    public TotalVoteCount(int castVotes, int totalCounted, int rejectedVotes, int validVotes) {
        this.castVotes = castVotes;
        this.totalCounted = totalCounted;
        this.rejectedVotes = rejectedVotes;
        this.validVotes = validVotes;
    }

    // Getters en Setters
    public int getCastVotes() {
        return castVotes;
    }

    public void setCastVotes(int castVotes) {
        this.castVotes = castVotes;
    }

    public int getTotalCounted() {
        return totalCounted;
    }

    public void setTotalCounted(int totalCounted) {
        this.totalCounted = totalCounted;
    }

    public int getRejectedVotes() {
        return rejectedVotes;
    }

    public void setRejectedVotes(int rejectedVotes) {
        this.rejectedVotes = rejectedVotes;
    }

    public int getValidVotes() {
        return validVotes;
    }

    public void setValidVotes(int validVotes) {
        this.validVotes = validVotes;
    }
}
