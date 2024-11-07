package com.example.parser.model.candidate;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Candidate", namespace = "urn:oasis:names:tc:evs:schema:eml")
public class Candidate {

    @XmlElement(name = "CandidateIdentifier", namespace = "urn:oasis:names:tc:evs:schema:eml")
    @JsonProperty("identifier")
    private CandidateIdentifier candidateIdentifier;

    @XmlElement(name = "CandidateFullName", namespace = "urn:oasis:names:tc:ciq:xsdschema:xNL:2.0")
    @JsonProperty("full_name")
    private CandidateFullName candidateFullName;

    @XmlElement(name = "Gender", namespace = "urn:oasis:names:tc:evs:schema:eml")
    @JsonProperty("gender")
    private String gender;
}
