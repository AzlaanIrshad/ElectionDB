package com.example.parser.model.tellingen;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class ManagingAuthority {

    @XmlElement(name = "AuthorityIdentifier", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private AuthorityIdentifier authorityIdentifier;

    @XmlElement(name = "AuthorityAddress", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private String authorityAddress;
}
