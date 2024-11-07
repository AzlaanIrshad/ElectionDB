package com.example.parser.model.candidate;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CandidateFullName", namespace = "urn:oasis:names:tc:ciq:xsdschema:xNL:2.0")
public class CandidateFullName {

    @XmlElement(name = "PersonName", namespace = "urn:oasis:names:tc:ciq:xsdschema:xNL:2.0")
    @JsonProperty("person_name")
    private PersonName personName;

    // Getters en Setters
    public PersonName getPersonName() {
        return personName;
    }

    public void setPersonName(PersonName personName) {
        this.personName = personName;
    }
}
