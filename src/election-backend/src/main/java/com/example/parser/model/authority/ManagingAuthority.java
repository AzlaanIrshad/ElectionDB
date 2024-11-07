package com.example.parser.model.authority;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ManagingAuthority {

    @XmlElement(name = "AuthorityIdentifier", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private AuthorityIdentifier authorityIdentifier;

    @XmlElement(name = "AuthorityAddress", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private String authorityAddress;

    public AuthorityIdentifier getAuthorityIdentifier() {
        return authorityIdentifier;
    }

    public void setAuthorityIdentifier(AuthorityIdentifier authorityIdentifier) {
        this.authorityIdentifier = authorityIdentifier;
    }

    public String getAuthorityAddress() {
        return authorityAddress;
    }

    public void setAuthorityAddress(String authorityAddress) {
        this.authorityAddress = authorityAddress;
    }
}
