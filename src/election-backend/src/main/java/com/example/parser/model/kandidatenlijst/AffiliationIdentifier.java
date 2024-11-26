package com.example.parser.model.kandidatenlijst;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class AffiliationIdentifier {

    @XmlAttribute(name = "Id")
    private String id;

    @XmlElement(name = "RegisteredName", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private String registeredName;
}
