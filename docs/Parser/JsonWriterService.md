# Documentatie: JsonWriterServices

## Overzicht
De **JsonWriterServices** bevatten functionaliteit om verkiezingsdata in XML-formaat te parseren en om te zetten naar JSON. Er zijn twee services:
- **KandidatenJsonWriterService**: Verwerkt kandidatenlijsten en schrijft de resultaten naar een JSON-bestand.
- **TellingenJsonWriterService**: Verwerkt tellingen en schrijft de resultaten naar een JSON-bestand.

---

## KandidatenJsonWriterService

### Beschrijving
Deze service is verantwoordelijk voor het:
1. Lezen en parseren van XML-bestanden met kandidatenlijsten.
2. Omzetten van de data naar objecten van het type `KandidatenResult`.
3. Schrijven van de resultaten naar een JSON-bestand.

### Belangrijkste Methoden

#### `writeKandidatenResultsToJson(int year)`
- **Beschrijving**: Voert het volledige proces uit voor het specifieke jaar.
- **Werking**:
    1. Ophalen van kandidatenlijsten-bestanden via `KandidatenlijstenFileProcessor`.
    2. Parsen van de bestanden naar objecten van type `KandidatenResult`.
    3. Schrijven van de geparste resultaten naar een JSON-bestand.
- **JSON-uitvoerpad**:  
  `src/election-backend/src/main/resources/ParsedJson/<jaar>/kandidatenlijsten_results.json`

#### `parseKandidatenFiles(List<File> files)`
- **Beschrijving**: Parseert de kandidatenlijsten XML-bestanden naar `KandidatenResult`-objecten.
- **Retourneert**: Een lijst van `KandidatenResult`-objecten.
- **Foutenafhandeling**: Logt fouten als parsing mislukt of als bestanden lege data bevatten.

#### `writeJsonToFile(List<KandidatenResult> results, String jsonOutputPath)`
- **Beschrijving**: Schrijft een lijst van `KandidatenResult`-objecten naar een JSON-bestand.
- **Foutenafhandeling**: Logt fouten als het schrijven naar het bestand mislukt.

---

## TellingenJsonWriterService

### Beschrijving
Deze service is verantwoordelijk voor het:
1. Lezen en parseren van XML-bestanden met tellingen.
2. Omzetten van de data naar objecten van het type `ElectionResult`.
3. Schrijven van de resultaten naar een JSON-bestand.

### Belangrijkste Methoden

#### `writeTellingResultsToJson(int year)`
- **Beschrijving**: Voert het volledige proces uit voor het specifieke jaar.
- **Werking**:
    1. Ophalen van tellingen-bestanden via `TellingFileProcessor`.
    2. Parsen van de bestanden naar objecten van type `ElectionResult`.
    3. Schrijven van de geparste resultaten naar een JSON-bestand.
- **JSON-uitvoerpad**:  
  `src/election-backend/src/main/resources/ParsedJson/<jaar>/tellingen_results.json`

#### `parseTellingFiles(List<File> files)`
- **Beschrijving**: Parseert de tellingen XML-bestanden naar `ElectionResult`-objecten.
- **Retourneert**: Een lijst van `ElectionResult`-objecten.
- **Foutenafhandeling**: Logt fouten als parsing mislukt of als bestanden lege data bevatten.

#### `writeJsonToFile(List<ElectionResult> results, String jsonOutputPath)`
- **Beschrijving**: Schrijft een lijst van `ElectionResult`-objecten naar een JSON-bestand.
- **Foutenafhandeling**: Logt fouten als het schrijven naar het bestand mislukt.

---

## Gemeenschappelijke Functionaliteit

### Logging
Beide services loggen het volgende:
1. **Bestanden gevonden**:
    - Aantal bestanden dat wordt verwerkt.
    - Specifieke bestanden die worden geparsed.
2. **Parsing-resultaten**:
    - Succesvol geparste bestanden.
    - Fouten of lege data in bestanden.
3. **JSON-uitvoer**:
    - Succesvol geschreven JSON-bestand.
    - Fouten bij het schrijven naar het bestand.

### Foutenafhandeling
- Beide services gebruiken `try-catch` om fouten te loggen bij het verwerken van individuele bestanden.
- Als parsing mislukt, blijft het proces doorgaan voor andere bestanden.

---

## Mapstructuur

- **Input-bestanden**:  
  Gevonden in de volgende mapstructuur:

---

## Afhankelijkheden

- **JAXB**: Voor het unmarshallen (parsen) van XML-bestanden.
- **Jackson**: Voor het serialiseren van Java-objecten naar JSON.
- **KandidatenlijstenFileProcessor**: Voor het ophalen van kandidatenlijsten-bestanden.
- **TellingFileProcessor**: Voor het ophalen van tellingen-bestanden.

- **JSON-uitvoer**:  
  Geplaatst in de volgende mapstructuur:
