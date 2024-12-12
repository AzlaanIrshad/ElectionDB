# Testen in het Election Project

Dit document legt uit hoe je de tests uitvoert, wat er precies getest wordt, en hoe alles werkt in het Election-project.

## 1. **Hoe je de tests uitvoert**

### Voorbereiding:
- Zorg ervoor dat je **Docker** geïnstalleerd hebt en draaiend is voor de databasecontainer.
- Zorg ervoor dat je de juiste versie van **Node.js** en **npm** hebt geïnstalleerd. In dit project gebruiken we **Node.js v18**.

### Testen uitvoeren:
Om de tests uit te voeren, voer je het volgende commando uit in de root van je project (waar het `package.json` bestand zich bevindt):

```bash
npm run dev
```

Dit commando voert de volgende stappen uit:
1. **Tests** (`dev:test`) worden eerst uitgevoerd.
2. Als de tests succesvol zijn, wordt de database opgestart (`dev:db`).
3. Daarna wordt de API-server gestart (`dev:api`).
4. Tot slot wordt de frontend gestart (`dev:web`).

### Testen zonder de rest van de services:
Als je alleen de tests wilt uitvoeren zonder de andere services (zoals de backend, frontend en database), kun je het volgende commando gebruiken:

```bash
npm run dev:test
```

Dit commando voert alleen de tests uit.

## 2. **Wat wordt getest?**

In dit project worden **JUnit 5 tests** uitgevoerd om de backend-functionaliteit te testen. Dit omvat de volgende types van testen:

### a. **Unit Tests**
Unit tests worden uitgevoerd om de functionaliteit van individuele componenten binnen de backend (zoals services, repositories en andere logica) te verifiëren.

Voorbeeld:
- Het testen van de **logica voor het verwerken van verkiezingsdata** (bijvoorbeeld het converteren van XML naar JSON).
- Validatie van de **wachtwoordhashing** (gebruik van `bcryptjs`).
- Het testen van **authenticatie** en **autorisatie** met JWT tokens.

### b. **Integratie Tests**
Integratietests worden uitgevoerd om de integratie tussen verschillende lagen van de applicatie te controleren, zoals de interactie tussen de API en de database.

Voorbeeld:
- Het testen van de **databaseverbinding**.
- Het testen van **CRUD-operaties** in de database via `sequelize`.
- Het testen van **API-endpoints** om ervoor te zorgen dat ze correct werken met de backend-database.

### c. **Functionele Tests**
Functionele tests worden uitgevoerd om ervoor te zorgen dat de applicatie als geheel goed werkt. Dit omvat het testen van de belangrijkste werkprocessen, zoals:

- Het uitvoeren van verkiezingsresultaten verwerking en het correct retourneren van data via API endpoints.
- Het testen van de interactie tussen frontend en backend (met behulp van mock data in de backend).

### d. **Testen van Externe Services**
Hoewel de tests zich voornamelijk richten op interne backend logica, kunnen er tests zijn die de interactie met externe services simuleren (bijvoorbeeld de interactie met een externe database of externe API's).

### Testen van de frontend:
De frontend zelf heeft geen specifieke tests gedefinieerd in dit project, maar je kunt handmatig de interface testen door de frontend op te starten met het `dev:web` commando en de functionaliteit van de gebruikersinterface te controleren.

## 3. **Hoe werken de tests?**

### Backend Tests:
- De **JUnit 5** tests worden uitgevoerd in de `src/election-backend` map.
- De tests maken gebruik van **Spring Boot** en **JUnit Jupiter** om verschillende componenten van de backend te testen.
- De tests worden automatisch uitgevoerd bij het uitvoeren van het commando `npm run dev:test`.

### Testconfiguratie:
- De tests gebruiken de **`mvn test`** opdracht via Maven om de tests uit te voeren.
- Bij het draaien van de tests wordt gebruikgemaakt van een **in-memory database** (indien geconfigureerd) of een geconfigureerde testdatabase.
- Testresultaten worden weergegeven in de terminal wanneer de tests zijn uitgevoerd.

### Resultaten van de tests:
- Als alle tests slagen, zie je een bericht zoals: `BUILD SUCCESS`.
- Als een test faalt, wordt een foutmelding weergegeven, inclusief de foutlocatie in de testcode.
- In geval van mislukking zal het proces de verdere executie van de backend, frontend en database stoppen.

## 4. **Wat als een test faalt?**

- Als een test faalt, worden de verdere stappen (zoals het opstarten van de database en backend) **niet uitgevoerd**.
- Je kunt de foutmelding in de terminal bekijken om te achterhalen waarom de test is mislukt.
- Het is belangrijk om eerst de mislukte test te onderzoeken en op te lossen voordat je verdergaat met de andere onderdelen van de applicatie.

### Mogelijke oorzaken van mislukte tests:
- Foutieve configuratie van de backend of databaseverbinding.
- Verkeerde invoerdata voor de test.
- Fouten in de businesslogica (zoals ongeldige berekeningen of fouten in gegevensverwerking).

## 5. **Conclusie**

Met de bovenstaande instructies kun je eenvoudig de tests in het Election-project uitvoeren en ervoor zorgen dat de belangrijkste functionaliteiten van de backend en database goed werken. Als alles goed is ingesteld, zou de ontwikkelomgeving na de succesvolle tests automatisch moeten doorlopen naar de andere componenten van de applicatie, zoals de database en de frontend.
