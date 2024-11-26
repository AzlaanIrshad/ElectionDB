package com.example.parser.model.kandidatenlijst;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Affiliation {

    @XmlElement(name = "AffiliationIdentifier", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private AffiliationIdentifier affiliationIdentifier;

    @XmlElement(name = "Type", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private String type;

    @XmlElement(name = "Candidate", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private List<Candidate> candidates;
}
