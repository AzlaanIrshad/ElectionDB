# **🌟 Handmatige Deploy Handleiding**

---

## **1️⃣ Build de applicatie lokaal**

🚀 **Voer de build uit:**
```bash
npm run build
```

✔️ Dit bouwt **zowel de backend als de frontend**:
- **📂 Backend**: Het `.jar`-bestand in de map `target`.
- **📂 Frontend**: De statische bestanden in de map `dist`.

---

## **2️⃣ Log in op de server via Termius**

🔑 **Inloggegevens**:
- **Host**: `oege.ie.hva.nl`
- **Gebruikersnaam**: `karadue2`
- **Wachtwoord**: `JzUlK+YB+$/xkt`

📡 **Wat te doen?**
- Gebruik SFTP om bestanden te uploaden.
- Open de terminal voor servercommando's.

---

## **3️⃣ Upload de bestanden naar de server**

### **📤 Stap 3.1: Gebruik SFTP in Termius**
1. **📁 Navigeer naar de map:**
   ```plaintext
   /Html/
   ```

2. Open de submappen:
   - **⚙️ Backend**: `/Html/backend/`
   - **🎨 Frontend**: `/Html/frontend/`

3. **Upload je bestanden**:
   - **Backend**: Upload het `.jar`-bestand uit de `target`-map naar `/Html/backend/`.
   - **Frontend**: Upload alle bestanden uit de `dist`-map naar `/Html/frontend/`.

---

## **4️⃣ Start de servers**

🖥️ Open de terminal en voer de volgende stappen uit:

1. **📂 Ga naar de hoofdmappen**:
   ```bash
   cd ~/Html/
   ```

2. **🔒 Geef uitvoerrechten aan het startscript**:
   ```bash
   chmod +x start-servers.sh
   ```

3. **▶️ Start de servers met het startscript**:
   ```bash
   ./start-servers.sh
   ```

---

## **5️⃣ Controleer of alles werkt**

✅ Controleer of de applicatie correct draait:
- **API**: [🌐 http://oege.ie.hva.nl:8000/](http://oege.ie.hva.nl:8000/)
- **Website**: [🌐 http://oege.ie.hva.nl:8001/](http://oege.ie.hva.nl:8001/)

---

## **⚠️ Veelgemaakte problemen**

### ❌ **Geen toegang tot de server**
➡️ Controleer je inloggegevens en internetverbinding.

### ❌ **Server start niet**
➡️ Controleer of alle bestanden correct zijn geüpload. Zorg dat het `start-servers.sh`-script uitvoerrechten heeft.

### ❌ **Applicatie werkt niet correct**
➡️ Controleer de serverlogs voor foutmeldingen.

### ❌ **Problemen met de poort**
Soms krijg je foutmeldingen zoals:
```plaintext
Description:  
Web server failed to start. Port 8000 was already in use.

Action:  
Identify and stop the process that's listening on port 8000 or configure this application to listen on another port.
```

✔️ **Hoe los je dit op?**
1. Controleer welke processen de poorten 8000 of 8001 gebruiken:
   ```bash
   lsof -i :8000
   lsof -i :8001
   ```

2. Stop het proces dat de poort blokkeert:
   ```bash
   kill -9 <PID>
   ```

3. Start de server opnieuw.

---