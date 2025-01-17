package com.example.parser.model.kandidatenlijst;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonName {

    @XmlElement(name = "NameLine", namespace = "urn:oasis:names:tc:ciq:xsdschema:xNL:2.0")
    private String nameLine;

    @XmlElement(name = "FirstName", namespace = "urn:oasis:names:tc:ciq:xsdschema:xNL:2.0")
    private String firstName;

    @XmlElement(name = "LastName", namespace = "urn:oasis:names:tc:ciq:xsdschema:xNL:2.0")
    private String lastName;
}
