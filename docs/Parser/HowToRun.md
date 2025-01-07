# How to Run the Election Parser

Volg deze stappen om de Election Parser te gebruiken en verkiezingsdata te verwerken.

---

## **1. Data downloaden**
1. Ga naar de officiële datasetpagina:  
   [https://data.overheid.nl/dataset/verkiezingsuitslag-tweede-kamer-2023#panel-resources](https://data.overheid.nl/dataset/verkiezingsuitslag-tweede-kamer-2023#panel-resources)

2. Download de ZIP-bestanden met de verkiezingsuitslag XML-data.

---

## **2. Data voorbereiden**
1. **Unzip de bestanden**: Pak de gedownloade ZIP-bestanden uit.  
   Elke map zal de XML-bestanden bevatten die de verkiezingsdata vertegenwoordigen.

2. **Mapstructuur aanmaken**:
    - Maak in je projectmap de volgende structuur:
      ```
      src/main/resources/ElectionResults/{jaar}/
      ```
      Waarbij `{jaar}` het verkiezingsjaar is (bijvoorbeeld `2023`).

3. **Kopieer de bestanden**: Verplaats de uitgepakte mappen met XML-bestanden naar de juiste jaarmap.  
   Bijvoorbeeld:
   ```
   src/main/resources/ElectionResults/2023/
   ```

   De XML-bestanden moeten direct in de submappen van de jaarmap staan.

---

## **3. Applicatie runnen**
1. **Start de applicatie**:  
   Zorg ervoor dat je de applicatie draait vanuit een IDE (zoals IntelliJ) of een terminal met een Java-buildsysteem zoals Maven.

2. **Controleer de logica**:  
   De parser leest automatisch de bestanden uit de map `src/main/resources/ElectionResults/{jaar}` en verwerkt deze.

3. **Resultaten opslaan**:  
   De geparste JSON-bestanden worden opgeslagen in de volgende map:
   ```
   src/main/resources/ParsedJson/{jaar}/
   ```
   Bijvoorbeeld:
   ```
   src/main/resources/ParsedJson/2023/
   ```

---

## **Opmerkingen**
- Zorg ervoor dat je de `application.properties` of andere configuratiebestanden correct hebt ingesteld als je paden of logica aanpast.
- De parser ondersteunt momenteel meerdere jaren. Zorg dat de mapstructuur en data correct zijn voor het gewenste jaar.
