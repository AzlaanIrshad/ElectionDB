package com.example.parser.model.misc;

import com.example.parser.model.affiliation.AffiliationIdentifier;
import com.example.parser.model.candidate.Candidate;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Selection {

    @XmlElement(name = "AffiliationIdentifier", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private AffiliationIdentifier affiliationIdentifier;

    @XmlElement(name = "Candidate", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private Candidate candidate;

    @XmlElement(name = "ValidVotes", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private int validVotes;

    public AffiliationIdentifier getAffiliationIdentifier() {
        return affiliationIdentifier;
    }

    public void setAffiliationIdentifier(AffiliationIdentifier affiliationIdentifier) {
        this.affiliationIdentifier = affiliationIdentifier;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public int getValidVotes() {
        return validVotes;
    }

    public void setValidVotes(int validVotes) {
        this.validVotes = validVotes;
    }
}
