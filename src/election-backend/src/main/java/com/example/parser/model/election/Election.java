package com.example.parser.model.election;

import com.example.parser.model.contest.Contests;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Election {

    @XmlElement(name = "ElectionIdentifier", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private ElectionIdentifier electionIdentifier;

    @XmlElement(name = "Contests", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private Contests contests;

    // Getters and setters
    public ElectionIdentifier getElectionIdentifier() {
        return electionIdentifier;
    }

    public void setElectionIdentifier(ElectionIdentifier electionIdentifier) {
        this.electionIdentifier = electionIdentifier;
    }

    public Contests getContests() {
        return contests;
    }

    public void setContests(Contests contests) {
        this.contests = contests;
    }
}
