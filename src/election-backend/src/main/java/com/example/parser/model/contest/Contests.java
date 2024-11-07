package com.example.parser.model.contest;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Contests {
    @XmlElement(name = "Contest", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private List<Contest> contests;

    public List<Contest> getContests() {
        return contests;
    }

    public void setContests(List<Contest> contests) {
        this.contests = contests;
    }
}
