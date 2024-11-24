package com.example.parser.model.kandidatenlijst;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Candidate {

    @XmlElement(name = "CandidateIdentifier", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private CandidateIdentifier candidateIdentifier;

    @XmlElement(name = "CandidateFullName", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private CandidateFullName candidateFullName;

    @XmlElement(name = "Gender", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private String gender;

    @XmlElement(name = "QualifyingAddress", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private QualifyingAddress qualifyingAddress;
}
