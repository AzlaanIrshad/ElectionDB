package com.example.parser.model.election;

import com.example.parser.model.affiliation.AffiliationIdentifier;
import com.example.parser.model.candidate.Candidate;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElements;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Selection {

    @XmlElements({
            @XmlElement(name = "AffiliationIdentifier", type = AffiliationIdentifier.class, namespace = "urn:oasis:names:tc:evs:schema:eml"),
            @XmlElement(name = "Candidate", type = Candidate.class, namespace = "urn:oasis:names:tc:evs:schema:eml")
    })
    private Object identifier;

    @XmlElement(name = "ValidVotes", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private int validVotes;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public AffiliationIdentifier getAffiliationIdentifier() {
        return identifier instanceof AffiliationIdentifier ? (AffiliationIdentifier) identifier : null;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Candidate getCandidate() {
        return identifier instanceof Candidate ? (Candidate) identifier : null;
    }
}
