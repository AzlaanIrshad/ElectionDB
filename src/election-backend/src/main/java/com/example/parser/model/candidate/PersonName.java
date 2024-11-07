package com.example.parser.model.candidate;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonName", namespace = "urn:oasis:names:tc:ciq:xsdschema:xNL:2.0")
public class PersonName {

    @XmlElement(name = "NameLine", namespace = "urn:oasis:names:tc:ciq:xsdschema:xNL:2.0")
    @JsonProperty("initials")
    private String nameLine;

    @XmlElement(name = "FirstName", namespace = "urn:oasis:names:tc:ciq:xsdschema:xNL:2.0")
    @JsonProperty("first_name")
    private String firstName;

    @XmlElement(name = "LastName", namespace = "urn:oasis:names:tc:ciq:xsdschema:xNL:2.0")
    @JsonProperty("last_name")
    private String lastName;
}
