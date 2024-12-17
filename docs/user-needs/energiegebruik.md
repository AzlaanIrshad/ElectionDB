# Energiegebruik Optimalisatie

Dit document beschrijft hoe ons project is geoptimaliseerd voor minimaal energiegebruik en hoe we gebruik maken van efficiënte technieken, zoals de implementatie van donkere modus.

---

## Donkere modus
### Technische kant
Donkere modus stelt gebruikers in staat de website in donkere kleuren te gebruiken, wat energie kan besparen, vooral op OLED-schermen.

We maken gebruik van TailwindCSS voor de implementatie en benutten de ingebouwde dark-mode tools (zie de documentatie: https://tailwindcss.com/docs/dark-mode). Onze configuratie:
- **Modus**: `class`
- **Voordelen**: Gebruikers kunnen handmatig schakelen tussen licht en donker.

De `DarkMode`-class zorgt ervoor dat:
1. De voorkeuren van de gebruiker automatisch worden geladen.
2. De juiste modus wordt ingesteld bij het laden van de pagina.
3. Gebruikers via een knop de modus kunnen aanpassen door de `toggle()`-functie aan te roepen.

*Diagram van onze implementatie:*
![Donkere modus class diagram](../assets/dark-mode-diagram.png)

### Visuele kant
Voorbeelden van onze donkere modus:

![Lichte modus voorbeeld](../assets/light-mode-example.png)
![Donkere modus voorbeeld](../assets/dark-mode-example.png)

---

## Code Optimalisatie
Om energiegebruik te minimaliseren, hebben we onze code geoptimaliseerd:
1. **Minimalistische CSS**:
    - Alleen noodzakelijke styles worden toegepast om de browser minder te belasten.
2. **Efficiënte JavaScript-structuren**:
    - Gebruik van geoptimaliseerde algoritmen en minimale DOM-manipulaties.
3. **Lui Laden (Lazy Loading)**:
    - Alleen benodigde content en scripts worden geladen om energie- en CPU-gebruik te beperken.
4. **Caching**:
    - Gebruik van browser caching om herhaalde dataverzoeken te vermijden.

---

## Conclusie
Door onze focus op donkere modus en code-optimalisaties ondersteunen we een efficiënt energiegebruik, wat zowel de prestaties van onze applicatie verbetert als het energieverbruik van gebruikers verlaagt.

