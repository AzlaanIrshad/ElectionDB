package entity;

public class ElectionResult {
    private Candidate candidate;
    private int votes;

    public ElectionResult(Candidate candidate, int votes) {
        this.candidate = candidate;
        this.votes = votes;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public int getVotes() {
        return votes;
    }

    @Override
    public String toString() {
        return "ElectionResult{" +
                "candidate=" + candidate +
                ", votes=" + votes +
                '}';
    }
}
