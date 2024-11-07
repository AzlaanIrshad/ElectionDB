package com.example.parser.model.misc;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlAccessorType(XmlAccessType.FIELD)
public class CanonicalizationMethod {

    @XmlElement(name = "Algorithm", namespace = "http://www.w3.org/2000/09/xmldsig#")
    @JsonProperty("algorithm")
    private String algorithm;

    // Getter and setter
    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }
}
