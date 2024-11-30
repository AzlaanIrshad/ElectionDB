<template>
  <div>
    <h1 class="text-2xl font-bold mb-4">Verkiezingskaart</h1>
    <div class="space-y-2">
      <label
          v-for="(color, party) in partyColors"
          :key="party"
          class="flex items-center"
      >
        <input
            type="checkbox"
            v-model="selectedParties"
            :value="party"
            class="mr-2"
        />
        <span
            :style="{ backgroundColor: color }"
            class="w-4 h-4 rounded-full inline-block mr-4"
        ></span>
        {{ party }}
      </label>
    </div>
    <div id="map" class="w-full h-[600px] mt-4"></div>
  </div>
</template>

<script>
import L from "leaflet";
import "leaflet/dist/leaflet.css";
import config from '../config';

export default {
  name: "ElectionMap",
  data() {
    return {
      map: null,
      markerLayer: null,
      electionData: [],
      selectedParties: [],
      partyColors: {
        "PVV (Partij voor de Vrijheid)": "blue",
        VVD: "orange",
        "GROENLINKS / Partij van de Arbeid (PvdA)": "green",
        D66: "yellow",
      },
    };
  },
  mounted() {
    this.initMap();
    this.fetchElectionResults();
  },
  methods: {
    /**
     * Haalt verkiezingsresultaten op van de backend en voegt markers toe aan de kaart.
     */
    async fetchElectionResults() {
      try {
        const year = 2023;
        const partiesQuery = this.selectedParties.join(",");
        const response = await fetch(
            `${config.apiBaseUrl}/api/map-results?year=${year}&parties=${encodeURIComponent(partiesQuery)}`
        );
        if (!response.ok) throw new Error("Ophalen van verkiezingsresultaten mislukt");
        this.electionData = await response.json();
        this.addMarkers();
      } catch (err) {
        console.error("Fout bij het ophalen van verkiezingsresultaten:", err);
      }
    },
    initMap() {
      this.map = L.map("map").setView([52.3676, 4.9041], 7); // Startpunt op Nederland
      L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
        attribution:
            '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> bijdragers',
      }).addTo(this.map);
      this.markerLayer = L.layerGroup().addTo(this.map);
    },
    addMarkers() {
      if (!this.map || !this.markerLayer) return;

      this.markerLayer.clearLayers();

      this.electionData.forEach((transaction) => {
        const cityName = transaction.cityName;
        if (!cityName) return;

        const leadingParty = transaction.leadingParty;
        const votes = transaction.votes;

        if (!leadingParty || (this.selectedParties.length && !this.selectedParties.includes(leadingParty))) {
          return;
        }

        const popupText = `<b>${cityName}</b><br>Leidende Partij: ${leadingParty}<br>Stemmen: ${votes}`;
        const color = this.partyColors[leadingParty] || "gray";
        const [lat, lng] = this.getCoordinatesForCity(cityName);

        if (!lat || !lng) return;

        L.circleMarker([lat, lng], {
          color,
          radius: 10,
          fillOpacity: 0.4,
        })
            .bindPopup(popupText)
            .addTo(this.markerLayer);
      });
    },
    /**
     * Haalt coördinaten op voor een gegeven stadsnaam.
     * @param {string} cityName - De naam van de stad.
     * @returns {Array} - Array met breedte- en lengtegraad.
     */
    getCoordinatesForCity(cityName) {
      const coordinates = {
        Amsterdam: [52.3676, 4.9041],
        Arnhem: [51.9851, 5.8987],
        Assen: [53.0000, 6.5667],
        Bonaire: [12.1784, -68.2385],
        "Den Helder": [52.9599, 4.7590],
        Dordrecht: [51.8133, 4.6901],
        Groningen: [53.2194, 6.5665],
        Haarlem: [52.3874, 4.6462],
        Leeuwarden: [53.2012, 5.7999],
        Leiden: [52.1601, 4.4970],
        Lelystad: [52.5185, 5.4714],
        Maastricht: [50.8514, 5.6910],
        Middelburg: [51.4988, 3.6106],
        Nijmegen: [51.8422, 5.8528],
        Rotterdam: [51.9225, 4.47917],
        "'s-Gravenhage": [52.0705, 4.3007],
        "'s-Hertogenbosch": [51.6978, 5.3037],
        Tilburg: [51.5606, 5.0919],
        Utrecht: [52.0907, 5.12142],
        Zwolle: [52.5168, 6.0830],
      };
      return coordinates[cityName] || [52.3676, 4.9041];
    },
  },
  watch: {
    selectedParties() {
      this.fetchElectionResults();
    },
  },
};
</script>
