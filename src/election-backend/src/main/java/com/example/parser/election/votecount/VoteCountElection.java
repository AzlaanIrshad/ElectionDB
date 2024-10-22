package com.example.parser.election.votecount;

import java.util.List;

public class VoteCountElection {
    private String electionId;
    private List<VoteCount> voteCounts;
    private List<PollingStation> pollingStations;

    // Constructor
    public VoteCountElection(String electionId, List<VoteCount> voteCounts, List<PollingStation> pollingStations) {
        this.electionId = electionId;
        this.voteCounts = voteCounts;
        this.pollingStations = pollingStations;
    }

    // Getters en Setters
    public String getElectionId() {
        return electionId;
    }

    public void setElectionId(String electionId) {
        this.electionId = electionId;
    }

    public List<VoteCount> getVoteCounts() {
        return voteCounts;
    }

    public void setVoteCounts(List<VoteCount> voteCounts) {
        this.voteCounts = voteCounts;
    }

    public List<PollingStation> getPollingStations() {
        return pollingStations;
    }

    public void setPollingStations(List<PollingStation> pollingStations) {
        this.pollingStations = pollingStations;
    }
}
