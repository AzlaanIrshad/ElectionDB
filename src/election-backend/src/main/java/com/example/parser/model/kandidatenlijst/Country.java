package com.example.parser.model.kandidatenlijst;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Country {

    @XmlElement(name = "CountryNameCode", namespace = "urn:oasis:names:tc:ciq:xsdschema:xAL:2.0")
    private String countryNameCode;

    @XmlElement(name = "Locality", namespace = "urn:oasis:names:tc:ciq:xsdschema:xAL:2.0")
    private Locality locality;
}
