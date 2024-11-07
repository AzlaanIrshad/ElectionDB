package com.example.parser.model.election;

import com.example.parser.model.authority.ManagingAuthority;
import com.example.parser.model.misc.CanonicalizationMethod;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "EML", namespace = "urn:oasis:names:tc:evs:schema:eml")
@XmlAccessorType(XmlAccessType.FIELD)
public class ElectionResult {

    @XmlElement(name = "TransactionId", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private int transactionId;

    @XmlElement(name = "ManagingAuthority", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private ManagingAuthority managingAuthority;

    @XmlElement(name = "Count", namespace = "urn:oasis:names:tc:evs:schema:eml")
    @JsonProperty("count")
    private Count count;

    @XmlElement(name = "CreationDateTime", namespace = "http://www.kiesraad.nl/extensions")
    private String creationDateTime;

    @XmlElement(name = "CanonicalizationMethod", namespace = "http://www.w3.org/2000/09/xmldsig#")
    private CanonicalizationMethod canonicalizationMethod;

    // Getters and setters
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public ManagingAuthority getManagingAuthority() {
        return managingAuthority;
    }

    public void setManagingAuthority(ManagingAuthority managingAuthority) {
        this.managingAuthority = managingAuthority;
    }

    public Count getCount() {
        return count;
    }

    public void setCount(Count count) {
        this.count = count;
    }

    public String getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(String creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public CanonicalizationMethod getCanonicalizationMethod() {
        return canonicalizationMethod;
    }

    public void setCanonicalizationMethod(CanonicalizationMethod canonicalizationMethod) {
        this.canonicalizationMethod = canonicalizationMethod;
    }
}
