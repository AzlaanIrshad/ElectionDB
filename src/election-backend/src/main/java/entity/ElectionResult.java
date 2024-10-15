package main.java.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "election_results")
public class ElectionResult implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @Column(name = "region", nullable = false)
    private String region;

    @Column(name = "total_votes", nullable = false)
    private int totalVotes;

    public ElectionResult() {
    }

    public ElectionResult(Candidate candidate, String region, int totalVotes) {
        this.candidate = candidate;
        this.region = region;
        this.totalVotes = totalVotes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(int totalVotes) {
        this.totalVotes = totalVotes;
    }
}
