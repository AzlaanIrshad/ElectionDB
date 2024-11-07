package com.example.parser.model.election;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
class ElectionIdentifier {
    @XmlAttribute(name = "Id")
    private String id;

    @XmlElement(name = "ElectionName", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private String electionName;

    @XmlElement(name = "ElectionCategory", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private String electionCategory;

    @XmlElement(name = "ElectionSubcategory", namespace = "http://www.kiesraad.nl/extensions")
    private String electionSubcategory;

    @XmlElement(name = "ElectionDate", namespace = "http://www.kiesraad.nl/extensions")
    private String electionDate;

    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getElectionName() {
        return electionName;
    }

    public void setElectionName(String electionName) {
        this.electionName = electionName;
    }

    public String getElectionCategory() {
        return electionCategory;
    }

    public void setElectionCategory(String electionCategory) {
        this.electionCategory = electionCategory;
    }

    public String getElectionSubcategory() {
        return electionSubcategory;
    }

    public void setElectionSubcategory(String electionSubcategory) {
        this.electionSubcategory = electionSubcategory;
    }

    public String getElectionDate() {
        return electionDate;
    }

    public void setElectionDate(String electionDate) {
        this.electionDate = electionDate;
    }
}
