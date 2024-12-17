# Oege Database en Hosting

De **Oege Database** is een MySQL-database die wordt beheerd door de **Hogeschool van Amsterdam (HvA)**. Deze database wordt gebruikt voor het opslaan van gegevens, zoals verkiezingsresultaten en gebruikersinformatie, die jouw project verwerkt.

## Wat is de Oege Database?

De Oege-database is een MySQL-database die toegankelijk is voor HvA-studenten voor hun projecten. Deze database kan worden gebruikt om data op te slaan en op te halen, en wordt toegankelijk via de **OEGE-omgeving**.

## Verbinding Maken met de Oege Database

De verbinding met de Oege-database wordt ingesteld via de `application.properties`-configuratie in je Spring Boot-applicatie. Hier geef je de gegevens op voor de database-verbinding, zoals de URL en gebruikersnaam.

### Onze `application.properties`

```properties
# Oege Database Configuration
spring.datasource.url=jdbc:mysql://oege.ie.hva.nl:3306/zkaradue2
spring.datasource.username=karadue2
spring.datasource.password=[secret]
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

## Hosting via Termius

Je kunt je applicatie hosten op de server van de Oege-database via **Termius**. Termius is een applicatie waarmee je verbinding kunt maken met servers via SSH en SFTP.

### Stappen om je applicatie te hosten via Termius:

1. **Log in op de server via Termius**  
   Gebruik de volgende gegevens om in te loggen:
    - **Host**: `oege.ie.hva.nl`
    - **Gebruikersnaam**: `karadue2`
    - **Wachtwoord**: [secret]

2. **Upload de bestanden naar de server**  
   Gebruik SFTP in Termius om je bestanden te uploaden:
    - **Backend**: Upload het `.jar`-bestand naar `/Html/backend/`.
    - **Frontend**: Upload de bestanden uit de `dist`-map naar `/Html/frontend/`.

3. **Start de servers**  
   Zodra de bestanden zijn geüpload, kun je de servers starten:
    - Ga naar de map `/Html/`.
    - Geef uitvoerrechten aan het startscript:
      ```bash
      chmod +x start-servers.sh
      ```
    - Start de servers met het startscript:
      ```bash
      ./start-servers.sh
      ```

4. **Controleer of alles werkt**  
   Bezoek de volgende URL's om te controleren of de applicatie goed draait:
    - **API**: [http://oege.ie.hva.nl:8000/](http://oege.ie.hva.nl:8000/)
    - **Website**: [http://oege.ie.hva.nl:8001/](http://oege.ie.hva.nl:8001/)

## Conclusie

Met de Oege-database kun je eenvoudig gegevens opslaan en ophalen voor je project. Door verbinding te maken met de database en gebruik te maken van Termius om de applicatie te hosten, kun je de applicatie zowel lokaal als op de server draaien. Zorg ervoor dat je de juiste configuratie hebt en volg de stappen voor het deployen en hosten van je project op de Oege-server.