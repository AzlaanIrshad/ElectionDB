package com.example.parser.model.election;

import com.example.parser.model.contest.Contest;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Count {

    @XmlElement(name = "Election", namespace = "urn:oasis:names:tc:evs:schema:eml")
    @JsonProperty("election")
    private Election election;

    @XmlElement(name = "Contests", namespace = "urn:oasis:names:tc:evs:schema:eml")
    @JsonProperty("contests")
    private List<Contest> contests;

    // Getters and setters
    public Election getElection() {
        return election;
    }

    public void setElection(Election election) {
        this.election = election;
    }

    public List<Contest> getContests() {
        return contests;
    }

    public void setContests(List<Contest> contests) {
        this.contests = contests;
    }
}
