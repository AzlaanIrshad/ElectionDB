package com.example.parser.model.gemeente;

import jakarta.xml.bind.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Candidate {

    @XmlElement(name = "CandidateIdentifier", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private CandidateIdentifier candidateIdentifier;
}
