package com.example.parser.model.gemeente;

import jakarta.xml.bind.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class CandidateIdentifier {

    @XmlAttribute(name = "Id")
    private String id;
}
