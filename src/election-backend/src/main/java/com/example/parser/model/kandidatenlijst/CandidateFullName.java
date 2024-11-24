package com.example.parser.model.kandidatenlijst;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class CandidateFullName {

    @XmlElement(name = "PersonName", namespace = "urn:oasis:names:tc:ciq:xsdschema:xNL:2.0")
    private PersonName personName;
}

