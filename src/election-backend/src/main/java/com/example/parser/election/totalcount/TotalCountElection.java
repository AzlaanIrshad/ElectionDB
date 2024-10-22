package com.example.parser.election.totalcount;

import java.util.List;

public class TotalCountElection {
    private String electionId;
    private TotalVoteCount totalVoteCount;
    private List<AffiliationVoteCount> affiliationVoteCounts;
    private List<CandidateVoteCount> candidateVoteCounts;

    // Constructor
    public TotalCountElection(String electionId, TotalVoteCount totalVoteCount, List<AffiliationVoteCount> affiliationVoteCounts, List<CandidateVoteCount> candidateVoteCounts) {
        this.electionId = electionId;
        this.totalVoteCount = totalVoteCount;
        this.affiliationVoteCounts = affiliationVoteCounts;
        this.candidateVoteCounts = candidateVoteCounts;
    }

    // Getters en Setters
    public String getElectionId() {
        return electionId;
    }

    public void setElectionId(String electionId) {
        this.electionId = electionId;
    }

    public TotalVoteCount getTotalVoteCount() {
        return totalVoteCount;
    }

    public void setTotalVoteCount(TotalVoteCount totalVoteCount) {
        this.totalVoteCount = totalVoteCount;
    }

    public List<AffiliationVoteCount> getAffiliationVoteCounts() {
        return affiliationVoteCounts;
    }

    public void setAffiliationVoteCounts(List<AffiliationVoteCount> affiliationVoteCounts) {
        this.affiliationVoteCounts = affiliationVoteCounts;
    }

    public List<CandidateVoteCount> getCandidateVoteCounts() {
        return candidateVoteCounts;
    }

    public void setCandidateVoteCounts(List<CandidateVoteCount> candidateVoteCounts) {
        this.candidateVoteCounts = candidateVoteCounts;
    }
}
