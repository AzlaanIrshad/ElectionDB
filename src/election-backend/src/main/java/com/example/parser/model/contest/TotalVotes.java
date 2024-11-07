package com.example.parser.model.contest;

import com.example.parser.model.misc.Selection;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
class TotalVotes {
    @XmlElement(name = "Selection", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private List<Selection> selections;

    public List<Selection> getSelections() {
        return selections;
    }

    public void setSelections(List<Selection> selections) {
        this.selections = selections;
    }
}
