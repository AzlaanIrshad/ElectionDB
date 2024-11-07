package com.example.parser.model.candidate;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CandidateIdentifier")
public class CandidateIdentifier {

    @XmlAttribute(name = "ShortCode")
    @JsonProperty("shortCode")
    private String shortCode;
}
