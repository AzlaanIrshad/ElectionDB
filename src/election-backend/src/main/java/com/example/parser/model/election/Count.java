package com.example.parser.model.election;

import com.example.parser.model.contest.Contest;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Count {

    @XmlElement(name = "Election", namespace = "urn:oasis:names:tc:evs:schema:eml")
    @JsonProperty("election")
    private Election election;

    @XmlElement(name = "Contests", namespace = "urn:oasis:names:tc:evs:schema:eml")
    @JsonProperty("contests")
    private List<Contest> contests;
}
