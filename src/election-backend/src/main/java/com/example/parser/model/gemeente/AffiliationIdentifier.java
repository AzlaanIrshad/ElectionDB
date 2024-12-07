package com.example.parser.model.gemeente;

import jakarta.xml.bind.annotation.*;
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
