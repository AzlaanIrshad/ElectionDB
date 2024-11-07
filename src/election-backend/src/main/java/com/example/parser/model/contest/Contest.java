package com.example.parser.model.contest;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Contest {

    @XmlElement(name = "ContestIdentifier", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private ContestIdentifier contestIdentifier;

    @XmlElement(name = "TotalVotes", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private TotalVotes totalVotes;

    // Getters and Setters
    public ContestIdentifier getContestIdentifier() {
        return contestIdentifier;
    }

    public void setContestIdentifier(ContestIdentifier contestIdentifier) {
        this.contestIdentifier = contestIdentifier;
    }

    public TotalVotes getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(TotalVotes totalVotes) {
        this.totalVotes = totalVotes;
    }
}
