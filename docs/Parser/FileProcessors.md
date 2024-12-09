# Documentatie: TellingFileProcessor en KandidatenlijstenFileProcessor

## Overzicht
De **TellingFileProcessor** en **KandidatenlijstenFileProcessor** zijn verantwoordelijk voor het ophalen en verwerken van XML-bestanden met betrekking tot verkiezingsuitslagen en kandidatenlijsten. Deze services worden gebruikt om bestanden te zoeken en te verwerken uit specifieke mappen, afhankelijk van het jaar van de verkiezingen.

---

## Klasse: TellingFileProcessor

### Beschrijving
De **TellingFileProcessor** zoekt en verzamelt XML-bestanden met tellingen van verkiezingsuitslagen uit verschillende mapstructuren. Deze structuur is afhankelijk van het jaar waarvoor de tellingen worden verwerkt.

### Belangrijkste Methoden

#### `getFiles(int year)`
- **Doel**: Zoekt en retourneert een lijst van telling-bestanden voor een specifiek jaar.
- **Werking**:
    - Controleert in verschillende directories afhankelijk van het jaar:
        - **2023 en 2021**: Bestanden in specifieke mappen met een naam die begint met `Telling_TK<jaar>` en eindigt op `.eml.xml` of `.xml`.
        - **2017, 2012 en 2010**: Bestanden met andere naamgeving, afhankelijk van de directory.
    - Logt elk gevonden bestand en het totaal aantal gevonden bestanden.

#### `getMatchingFiles(String directoryPath, int year)`
- **Doel**: Filtert en retourneert bestanden die voldoen aan de naamgevingscriteria in een opgegeven directory.
- **Werking**:
    - Controleert of de directory bestaat.
    - Filtert bestanden op basis van een naamgeving en extensie afhankelijk van het jaar.
    - Logt ontbrekende directories of bestanden die worden toegevoegd.

#### `getLogger()`
- Retourneert de **Logger**-instantie voor logboeken.

### Logboeken
- Logt waarschuwingen als een directory niet wordt gevonden.
- Logt informatie over gevonden bestanden en het totaal aantal bestanden.

---

## Klasse: KandidatenlijstenFileProcessor

### Beschrijving
De **KandidatenlijstenFileProcessor** zoekt en verzamelt XML-bestanden met kandidatenlijsten uit verschillende mappen, afhankelijk van het jaar van de verkiezingen.

### Belangrijkste Methode

#### `getKandidatenlijstenFiles(int year)`
- **Doel**: Zoekt en retourneert een lijst van kandidatenlijsten-bestanden voor een specifiek jaar.
- **Werking**:
    - Controleert in verschillende directories afhankelijk van het jaar:
        - **2023**: Mappen zoals `Kandidatenlijsten`.
        - **2021 en eerdere jaren**: EML-bestanden met kandidatenlijsten.
    - Logt elk gevonden bestand en het totaal aantal gevonden bestanden.

#### `getFiles(String directoryPath, int year)`
- **Doel**: Filtert en retourneert bestanden die voldoen aan de naamgevingscriteria in een opgegeven directory.
- **Werking**:
    - Controleert of de directory bestaat.
    - Filtert bestanden op basis van naamgeving (`Kandidatenlijsten_TK<jaar>_`) en de extensie `.xml`.
    - Logt ontbrekende directories of bestanden die worden toegevoegd.

### Logboeken
- Logt waarschuwingen als een directory niet wordt gevonden.
- Logt informatie over gevonden bestanden en het totaal aantal bestanden.

---

## Gebruikte Mappenstructuur

De mappen worden dynamisch gegenereerd op basis van het verkiezingsjaar. Hieronder enkele voorbeelden:

- **2023**
    - `src/election-backend/src/main/resources/ElectionResults/2023/Verkiezingsuitslag Tweede Kamer 2023 (Deel 1)`
    - `Kandidatenlijsten` submap voor kandidatenlijsten.
- **2021**
    - `src/election-backend/src/main/resources/ElectionResults/2021/EML_bestanden_TK2021_deel_1`
- **2017, 2012, 2010**
    - Directories zoals `CSB_uitslag` of `EML_bestanden_TK<jaar>`.

---