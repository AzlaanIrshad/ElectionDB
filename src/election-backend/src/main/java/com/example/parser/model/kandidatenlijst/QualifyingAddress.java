package com.example.parser.model.kandidatenlijst;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QualifyingAddress {

    @XmlElement(name = "Locality", namespace = "urn:oasis:names:tc:ciq:xsdschema:xAL:2.0")
    private Locality locality;

    @XmlElement(name = "Country", namespace = "urn:oasis:names:tc:ciq:xsdschema:xAL:2.0", nillable = true)
    private Country country;

    @JsonProperty("countryPresent")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Boolean isCountryPresent() {
        return country != null && country.getCountryNameCode() != null;
    }
}
