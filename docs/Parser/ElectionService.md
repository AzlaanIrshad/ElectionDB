# Documentatie: ElectionService

## Overzicht
De **ElectionService** is verantwoordelijk voor het coördineren van de verwerking van verkiezingsgegevens, inclusief het parsen van XML-bestanden en het genereren van JSON-bestanden. Het biedt een centrale service voor het uitvoeren van taken zoals het ophalen van tellingen, het verwerken van kandidatenlijsten en het schrijven van JSON-uitvoer.

---

## Kernfunctionaliteit

### Methode: `parseXmlFilesToJson(int year)`
- **Beschrijving**:
  Voert het volledige proces uit van het verwerken van XML-bestanden naar JSON-uitvoer voor een specifiek jaar. Dit omvat:
    - Het parsen van tellingen.
    - Het verzamelen van resultaten.
    - Het schrijven van tellingen en kandidatenlijsten naar JSON-bestanden.
- **Parameters**:
    - `int year`: Het jaar waarvoor de bestanden worden verwerkt.
- **Werking**:
    1. Start een threadpool met 5 threads via een `ExecutorService`.
    2. Roept de `processFiles`-methode van de `TellingFileProcessor` aan om alle tellingenbestanden te parsen.
    3. Verzamelt de resultaten van de verwerking met de methode `collectResults`.
    4. Schrijft de resultaten naar JSON-bestanden met behulp van de services:
        - `TellingenJsonWriterService` voor tellingen.
        - `KandidatenJsonWriterService` voor kandidatenlijsten.
    5. Zorgt voor een correcte afsluiting van de `ExecutorService`.

---

### Methode: `collectResults(List<Future<ElectionResult>> futures)`
- **Beschrijving**:
  Verzamelt de resultaten van de asynchrone verwerking van XML-bestanden.
- **Parameters**:
    - `List<Future<ElectionResult>> futures`: Een lijst van `Future`-objecten die het resultaat van de XML-verwerking vertegenwoordigen.
- **Werking**:
    1. Loopt door alle `Future`-objecten en probeert de resultaten op te halen.
    2. Logt fouten als een `Future` geen geldig resultaat retourneert.
- **Retourneert**: `List<ElectionResult>`  
  Een lijst van succesvolle parsingresultaten.

---

## Componenten en Afhankelijkheden

### 1. **TellingFileProcessor**
- Verantwoordelijk voor het parsen van XML-bestanden met tellingen.
- Gebruik de methode `processFiles(ExecutorService executor, int year)` om asynchrone verwerking mogelijk te maken.

### 2. **TellingenJsonWriterService**
- Schrijft de geparste tellingenresultaten naar een JSON-bestand.

### 3. **KandidatenJsonWriterService**
- Schrijft de geparste kandidatenlijstenresultaten naar een JSON-bestand.

### 4. **ExecutorService**
- Beheert multithreaded verwerking van XML-bestanden om efficiëntie te verbeteren.

---

## Gebruik

### Voorbeeldgebruik in een Spring Boot Applicatie
```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ElectionRunner implements CommandLineRunner {

    @Autowired
    private ElectionService electionService;

    @Override
    public void run(String... args) {
        int year = 2023;
        electionService.parseXmlFilesToJson(year);
    }
}
