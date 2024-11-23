package com.example.parser.model.kandidatenlijst;

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
public class KandidatenResult {

    @XmlElement(name = "TransactionId", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private int transactionId;

    @XmlElement(name = "ManagingAuthority", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private ManagingAuthority managingAuthority;

    @XmlElement(name = "IssueDate", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private String issueDate;

    @XmlElement(name = "CreationDateTime", namespace = "http://www.kiesraad.nl/extensions")
    private String creationDateTime;

    @XmlElement(name = "CandidateList", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private CandidateList candidateList;
}
