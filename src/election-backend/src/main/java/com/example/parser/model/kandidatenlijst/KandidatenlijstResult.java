package com.example.parser.model.kandidatenlijst;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlRootElement(name = "KandidatenlijstResult", namespace = "urn:oasis:names:tc:evs:schema:eml")
@XmlAccessorType(XmlAccessType.FIELD)
public class KandidatenlijstResult {

    @XmlElement(name = "TransactionId", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private int transactionId;

    @XmlElement(name = "ManagingAuthority", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private ManagingAuthority managingAuthority;

    @XmlElement(name = "Election", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private Election election;
}
