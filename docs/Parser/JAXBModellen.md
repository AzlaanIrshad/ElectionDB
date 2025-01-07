# Documentatie van JAXB Modellen

1. **`com.example.parser.model.tellingen`**
2. **`com.example.parser.model.kandidatenlijst`**

---

## **1. Pakket: `com.example.parser.model.tellingen`**
Dit pakket bevat modellen voor het verwerken van stemresultaten.

---

### **1.1. `Affiliation`**
- **Beschrijving**: Vertegenwoordigt een partij of affiliatie.
- **Velden**:
    - `String id` - **Attribuut**: De unieke ID van de affiliatie.
    - `String registeredName` - Geregistreerde naam van de partij.
- **Annotations**:
    - `@XmlAttribute` en `@XmlElement`

---

### **1.2. `AffiliationIdentifier`**
- **Beschrijving**: Identificeert een partij binnen een verkiezing.
- **Velden**:
    - `String id` - **Attribuut**: De unieke ID van de affiliatie.
    - `String registeredName` - Naam van de partij.

---

### **1.3. `AuthorityIdentifier`**
- **Beschrijving**: Identificeert een autoriteit.
- **Velden**:
    - `String id` - **Attribuut**: ID van de autoriteit.
    - `String value` - De waarde of naam van de autoriteit.

---

### **1.4. `Candidate`**
- **Beschrijving**: Representatie van een kandidaat.
- **Velden**:
    - `CandidateIdentifier candidateIdentifier` - Identificatiegegevens van de kandidaat.

---

### **1.5. `CandidateIdentifier`**
- **Beschrijving**: Identificeert een specifieke kandidaat.
- **Velden**:
    - `String id` - **Attribuut**: De unieke ID van de kandidaat.

---

### **1.6. `Contest`**
- **Beschrijving**: Vertegenwoordigt een verkiezingsonderdeel (bijv. een gemeente of district).
- **Velden**:
    - `ContestIdentifier contestIdentifier` - Identificatiegegevens van het onderdeel.
    - `TotalVotes totalVotes` - Stemresultaten.

---

### **1.7. `ContestIdentifier`**
- **Beschrijving**: Identificeert een verkiezingsonderdeel.
- **Velden**:
    - `String id` - **Attribuut**: Unieke ID van het onderdeel.
    - `String contestName` - Naam van het verkiezingsonderdeel.

---

### **1.8. `Contests`**
- **Beschrijving**: Een lijst van alle verkiezingsonderdelen.
- **Velden**:
    - `List<Contest> contests` - Lijst van verkiezingsonderdelen.

---

### **1.9. `Count`**
- **Beschrijving**: Root-element dat de verkiezingsgegevens omvat.
- **Velden**:
    - `Election election` - Informatie over de verkiezing.

---

### **1.10. `Election`**
- **Beschrijving**: Bevat details over een verkiezing.
- **Velden**:
    - `ElectionIdentifier electionIdentifier` - Identificatie van de verkiezing.
    - `Contests contests` - Lijst van verkiezingsonderdelen.

---

### **1.11. `ElectionIdentifier`**
- **Beschrijving**: Identificeert een verkiezing.
- **Velden**:
    - `String id` - **Attribuut**: ID van de verkiezing.
    - `String electionName` - Naam van de verkiezing.
    - `String electionCategory` - Categorie van de verkiezing.
    - `String electionSubcategory` - Subcategorie (specifiek voor Kiesraad-extensies).
    - `String electionDate` - Datum van de verkiezing.

---

### **1.12. `ElectionResult`**
- **Beschrijving**: Rootklasse die een verkiezingsresultaat vertegenwoordigt.
- **Velden**:
    - `int transactionId` - ID van de transactie.
    - `ManagingAuthority managingAuthority` - Bevoegde autoriteit.
    - `Count count` - Verkiezingsdata.

---

### **1.13. `ManagingAuthority`**
- **Beschrijving**: Informatie over de verantwoordelijke autoriteit.
- **Velden**:
    - `AuthorityIdentifier authorityIdentifier` - Identificatiegegevens van de autoriteit.
    - `String authorityAddress` - Adres van de autoriteit.

---

### **1.14. `Selection`**
- **Beschrijving**: Representatie van een selectie, zoals een partij of kandidaat.
- **Velden**:
    - `Object identifier` - Kan een `AffiliationIdentifier` of een `Candidate` zijn.
    - `int validVotes` - Aantal geldige stemmen.
- **Bijzonderheden**:
    - JSON output filtert automatisch velden met null-waarden door gebruik van `@JsonInclude`.

---

### **1.15. `TotalVotes`**
- **Beschrijving**: Vertegenwoordigt de totale stemmen voor verschillende selecties.
- **Velden**:
    - `List<Selection> selections` - Lijst van selecties (partijen of kandidaten).

---

## **2. Pakket: `com.example.parser.model.kandidatenlijst`**
Dit pakket bevat modellen voor het beheren van kandidatenlijsten. Zelde concepten als in het pakket `tellingen`.
