package com.example.parser.election;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "Contest")
public class Contest {

    private String contestIdentifier;
    private List<Candidate> candidates = new ArrayList<>();

    public Contest() {}

    @XmlElement(name = "ContestIdentifier")
    public String getContestIdentifier() {
        return contestIdentifier;
    }

    public void setContestIdentifier(String contestIdentifier) {
        this.contestIdentifier = contestIdentifier;
    }

    @XmlElement(name = "Candidates")
    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }
}
