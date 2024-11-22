package com.example.parser.model.kandidatenlijst;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Election {

    @XmlElement(name = "ElectionIdentifier", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private ElectionIdentifier electionIdentifier;
}
