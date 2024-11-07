package com.example.parser.model.candidate;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CandidateIdentifier", namespace = "urn:oasis:names:tc:evs:schema:eml")
public class CandidateIdentifier {

    @XmlAttribute(name = "Id")
    @JsonProperty("id")
    private String id;

    // Getter en Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
