package com.example.parser.model.candidate;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Gender", namespace = "urn:oasis:names:tc:evs:schema:eml")
public class Gender {

    @XmlElement(name = "GenderCode", namespace = "urn:oasis:names:tc:evs:schema:eml")
    @JsonProperty("gender_code")
    private String genderCode;

    // Getters and Setters
    public String getGenderCode() {
        return genderCode;
    }

    public void setGenderCode(String genderCode) {
        this.genderCode = genderCode;
    }
}
