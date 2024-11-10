package com.example.parser.model.election;

import com.example.parser.model.affiliation.ManagingAuthority;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlRootElement(name = "EML", namespace = "urn:oasis:names:tc:evs:schema:eml")
@XmlAccessorType(XmlAccessType.FIELD)
public class ElectionResult {

    @XmlElement(name = "TransactionId", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private int transactionId;

    @XmlElement(name = "ManagingAuthority", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private ManagingAuthority managingAuthority;

    @XmlElement(name = "Count", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private Count count;
}
