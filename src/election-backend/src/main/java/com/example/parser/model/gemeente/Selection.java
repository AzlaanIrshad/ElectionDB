package com.example.parser.model.gemeente;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.xml.bind.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Selection {

    @XmlElement(name = "AffiliationIdentifier", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private AffiliationIdentifier affiliationIdentifier;

    @XmlElement(name = "Candidate", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private Candidate candidate;

    @XmlElement(name = "ValidVotes", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private Integer validVotes;
}
