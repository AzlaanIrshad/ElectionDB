# Documentatie: FileProcessingService

## Overzicht
De **FileProcessingService** is een abstracte serviceklasse die herbruikbare functionaliteit biedt voor het verwerken van XML-bestanden. Deze klasse ondersteunt parallelle verwerking door middel van een `ExecutorService` en biedt een gestandaardiseerde structuur voor het ophalen en parsen van XML-bestanden.

---

## Kernfunctionaliteit

### Abstracte Methoden
De volgende methoden moeten door een subclass worden geïmplementeerd:

#### `getFiles(int year)`
- **Beschrijving**:
  Retourneert een lijst van XML-bestanden voor het opgegeven jaar.
- **Retourneert**: `List<File>`  
  Een lijst van te verwerken bestanden.

#### `getLogger()`
- **Beschrijving**:
  Geeft een `Logger`-instantie terug voor het loggen van activiteiten en fouten.
- **Retourneert**: `Logger`  
  De logger die door de subclass wordt gebruikt.

---

### Methode: `processFiles(ExecutorService executor, int year)`
- **Beschrijving**:
  Verwerkt een lijst van XML-bestanden in parallel met behulp van een `ExecutorService`. Elk bestand wordt verwerkt door een afzonderlijke thread.
- **Parameters**:
    - `ExecutorService executor`: De uitvoeringsservice voor het beheren van threads.
    - `int year`: Het jaar waarvoor bestanden worden verwerkt.
- **Werking**:
    1. Roept de methode `getFiles(year)` aan om de lijst van bestanden op te halen.
    2. Voor elk bestand wordt een parsing-taak ingediend bij de `ExecutorService`.
    3. De taken worden verzameld als `Future<ElectionResult>`-objecten.
- **Retourneert**: `List<Future<ElectionResult>>`  
  Een lijst van futures die elk een parsingresultaat vertegenwoordigen.

---

### Methode: `parseXmlFile(File xmlFile)`
- **Beschrijving**:
  Parseert een individueel XML-bestand naar een object van het type `ElectionResult` met behulp van JAXB.
- **Parameters**:
    - `File xmlFile`: Het XML-bestand dat wordt geparsed.
- **Werking**:
    1. Logt de naam van het bestand dat wordt geparsed.
    2. Gebruikt JAXB om het XML-bestand om te zetten naar een `ElectionResult`-object.
    3. Logt eventuele fouten als parsing mislukt.
- **Retourneert**:
    - `ElectionResult`: Het geparste object, of `null` als parsing mislukt.

---

## Gebruik

### Subclass-implementatie
Een subclass moet de abstracte methoden `getFiles()` en `getLogger()` implementeren. Bijvoorbeeld:

```java
@Service
public class TellingenFileProcessingService extends FileProcessingService {

    @Autowired
    private TellingFileProcessor tellingFileProcessor;

    private static final Logger logger = LoggerFactory.getLogger(TellingenFileProcessingService.class);

    @Override
    protected List<File> getFiles(int year) {
        return tellingFileProcessor.getFiles(year);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
