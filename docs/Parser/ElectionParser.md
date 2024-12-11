# Documentatie: ElectionParser

## Beschrijving
De **ElectionParser** is de hoofdingangspunt van de applicatie. Het is een Spring Boot-toepassing die verkiezingsdata in XML-formaat verwerkt en omzet naar JSON. Deze klasse identificeert de beschikbare verkiezingsjaren door te kijken naar de mappenstructuur en roept de parsing-logica aan via de `ElectionService`.

---

## Belangrijkste Componenten

### Eigenschappen

#### `BASE_PATH`
- **Type**: `String`
- **Beschrijving**: Het basispad waarin verkiezingsgegevens per jaar worden opgeslagen.
- **Waarde**: `"src/election-backend/src/main/resources/ElectionResults"`

---

### Methoden

#### `main(String[] args)`
- **Beschrijving**: Startpunt van de applicatie.
- **Werking**:
    - Start de Spring Boot-toepassing.
    - Roept de `run`-methode aan om het parsingproces uit te voeren.

#### `run(String... args)`
- **Beschrijving**: Behandelt de logica om XML-bestanden te verwerken.
- **Werking**:
    1. Print een bericht dat het parsingproces is gestart.
    2. Roept de methode `getAvailableYears()` aan om de beschikbare verkiezingsjaren te identificeren.
    3. Controleert of er beschikbare jaren zijn:
        - Als er geen jaren zijn, geeft het een melding dat de mapstructuur gecontroleerd moet worden.
        - Anders wordt voor elk gevonden jaar de `ElectionService.parseXmlFilesToJson()`-methode aangeroepen om bestanden voor dat jaar te verwerken.
    4. Print een bericht dat het parsingproces is voltooid.
    5. Sluit de Spring Boot-toepassing na voltooiing van het parsingproces.

#### `getAvailableYears()`
- **Beschrijving**: Identificeert beschikbare verkiezingsjaren door de submappen in de basisdirectory te controleren.
- **Retourneert**: `List<Integer>` - Een lijst met beschikbare jaren.
- **Werking**:
    1. Controleert of de basisdirectory bestaat en een geldige map is.
    2. Itereert door de submappen van de basisdirectory:
        - Probeert elke mapnaam om te zetten naar een integer.
        - Voegt een geldig jaar toe aan de lijst.
        - Logt ongeldige mapnamen (geen jaar).
    3. Retourneert de lijst met geldige jaren.

---

## Logging

- **Start- en eindmeldingen**:
    - "Starting XML to JSON parsing process..."
    - "Parsing process completed."
- **Beschikbare jaren**:
    - Print een lijst met beschikbare jaren of een melding als er geen jaren zijn.
- **Foutmeldingen**:
    - "Ongeldige mapnaam gevonden (geen jaar): <mapnaam>"
    - "De basisdirectory bestaat niet of is geen map: <pad>"

---

## Functionaliteit in Overzicht

1. **Basisfunctionaliteit**:
    - Bepalen welke jaren beschikbaar zijn door submappen in de `BASE_PATH`-directory te controleren.
    - Voor elk gevonden jaar de verkiezingsdata verwerken via de `ElectionService`.
2. **Dynamisch en uitbreidbaar**:
    - Nieuwe jaren kunnen eenvoudig worden toegevoegd door mappen met de naam van het jaar toe te voegen aan de `BASE_PATH`.
3. **Sluit de applicatie automatisch**:
    - Na voltooiing wordt de Spring Boot-toepassing afgesloten via `SpringApplication.exit`.
