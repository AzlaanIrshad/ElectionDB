package com.example.parser.model.gemeente;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Contests {

    @XmlElement(name = "Contest", namespace = "urn:oasis:names:tc:evs:schema:eml")
    private List<Contest> contests;
}
