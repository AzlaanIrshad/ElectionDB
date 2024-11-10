package com.example.parser.model.election;

import com.example.parser.model.affiliation.AffiliationIdentifier;
import com.example.parser.model.candidate.Candidate;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Selection {

    @XmlElement(name = "AffiliationIdentifier", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private AffiliationIdentifier affiliationIdentifier;

    @XmlElement(name = "ValidVotes", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private int validVotes;

    @XmlElement(name = "Candidate", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private Candidate candidate;
}
