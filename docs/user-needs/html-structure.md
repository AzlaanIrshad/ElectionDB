# HTML-Structuur en Toegankelijkheidsfuncties

Naast kleurcontrast hebben we ook de **HTML-structuur** van onze website geoptimaliseerd om toegankelijkheid te waarborgen, met name voor blinde en slechtziende gebruikers. Een goed gestructureerde website is essentieel voor het gebruik van schermlezers en andere hulpmiddelen.

---

## **Optimalisatie van HTML-Structuur**

### **Semantische HTML**
We hebben semantische HTML-tags gebruikt om de inhoud van onze website duidelijk te structureren. Voorbeelden hiervan zijn:
- **`<header>`**, **`<nav>`**, **`<main>`**, en **`<footer>`** om de pagina logisch in te delen.
- **`<section>`** en **`<article>`** om contentblokken te groeperen.
- **`<h1>` tot `<h6>`** voor hiërarchie in koppen, zodat schermlezers de structuur begrijpen.

### **Beschrijvende Alt-teksten**
Alle afbeeldingen op onze website bevatten een duidelijke **`alt`-tekst**. Hierdoor kunnen schermlezers de inhoud van de afbeelding beschrijven aan gebruikers:

```html
<img src="verkiezingfoto.jpg" alt="Een kaart van Nederland met alle stemlocaties">
```

### **Formulierelementen**
Onze formulieren bevatten:
- **`<label>`-tags** gekoppeld aan formuliervelden om aan te geven wat een veld inhoudt:

  ```html
  <label for="email">E-mailadres:</label>
  <input type="email" id="email" name="email">
  ```

- **Error-meldingen:** Duidelijke foutmeldingen die door schermlezers worden herkend.

---

## **Toegankelijkheidsfuncties voor Blinden**

### **Toetsenbordnavigatie**
Onze website is volledig navigeerbaar met een toetsenbord. Dit houdt in dat alle interactieve elementen (zoals knoppen en links) logisch kunnen worden bereikt via de **Tab-toets**.

### **Skip-links**
We hebben **skip-links** toegevoegd om gebruikers snel naar belangrijke delen van de pagina te laten springen, bijvoorbeeld:

```html
<a href="#main-content" class="skip-link">Sla navigatie over</a>
```

### **ARIA-labels**
Waar nodig hebben we **ARIA (Accessible Rich Internet Applications)**-attributen gebruikt om extra context te geven aan elementen. Voorbeelden:
- **`aria-label`** om een knop te beschrijven:

  ```html
  <button aria-label="Menu openen">☰</button>
  ```

- **`aria-live`** voor dynamische content, zodat schermlezers wijzigingen kunnen aankondigen.

---


## **Resultaat**

Met deze aanpassingen hebben we onze website toegankelijker gemaakt voor iedereen, 
ongeacht visuele beperkingen. Onze focus op zowel kleurcontrast als HTML-structuur zorgt ervoor dat blinden en slechtzienden de website efficiënt en prettig kunnen gebruiken.

---

## **Bronnen**

- Kennisbank DigiToegankelijkheid. (2023, 2 november). *Kleurcontrast van tekst: WCAG-richtlijnen voor optimale zichtbaarheid.* Geraadpleegd via [kennisbank.digitoegankelijk.nl](https://kennisbank.digitoegankelijk.nl/voldoende-contrast-van-tekst/#:~:text=Tekst%20kleiner%20dan%2018pt%20(24px,teksten%20in%20afbeeldingen%20en%20video's).
