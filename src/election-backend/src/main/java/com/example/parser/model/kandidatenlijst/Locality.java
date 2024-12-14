package com.example.parser.model.kandidatenlijst;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Locality {

    @XmlElement(name = "LocalityName", namespace = "urn:oasis:names:tc:ciq:xsdschema:xAL:2.0")
    private String localityName = "niet gespecificeerd";

    public void setLocalityName(String localityName) {
        if (localityName == null || localityName.isEmpty()) {
            this.localityName = "niet gespecificeerd";
        } else {
            this.localityName = localityName;
        }
    }
}
