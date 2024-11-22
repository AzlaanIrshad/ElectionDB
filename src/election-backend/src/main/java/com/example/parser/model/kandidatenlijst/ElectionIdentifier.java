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
public class ElectionIdentifier {

    @XmlAttribute(name = "Id")
    private String id;

    @XmlElement(name = "ElectionName", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private String electionName;

    @XmlElement(name = "ElectionCategory", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private String electionCategory;

    @XmlElement(name = "ElectionSubcategory", namespace = "http://www.kiesraad.nl/extensions")
    private String electionSubcategory;

    @XmlElement(name = "ElectionDate", namespace = "http://www.kiesraad.nl/extensions")
    private String electionDate;
}
