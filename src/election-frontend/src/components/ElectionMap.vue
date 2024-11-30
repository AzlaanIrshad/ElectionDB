<template>
  <div class="flex flex-col lg:flex-row">
    <!-- Checkboxes sectie -->
    <div class="w-full lg:w-1/4 p-4 border-b lg:border-b-0 lg:border-r border-gray-300 bg-gray-50 dark:bg-gray-800">
      <!-- Dropdown voor jaartal -->
      <div class="mb-4">
        <label for="year" class="block font-bold mb-2 text-gray-800 dark:text-gray-100">Selecteer Jaar:</label>
        <select
            id="year"
            v-model="selectedYear"
            @change="onYearChange"
            class="bg-white dark:bg-gray-900 border border-gray-300 dark:border-gray-700 rounded px-2 py-1 w-full"
        >
          <option
              v-for="year in availableYears"
              :key="year"
              :value="year"
          >
            {{ year }}
          </option>
        </select>
      </div>

      <h2 class="text-lg font-bold mb-4 text-gray-800 dark:text-gray-100">Selecteer Partijen</h2>
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
    </div>

    <!-- Kaart sectie -->
    <div class="w-full lg:w-3/4 p-4">
      <h1 class="text-xl font-bold mb-4 text-center text-gray-800 dark:text-gray-100">Verkiezingsstatistieken Steden</h1>
      <div
          id="map"
          class="w-full h-[400px] sm:h-[500px] lg:h-[600px] border border-gray-300 rounded shadow-md"
      ></div>
    </div>
  </div>
</template>

<script>
import L from "leaflet";
import "leaflet/dist/leaflet.css";
import config from "../config";

export default {
  name: "ElectionMap",
  data() {
    return {
      map: null,
      markerLayer: null,
      electionData: [],
      selectedParties: [],
      partyColors: {},
      selectedYear: 2023,
      availableYears: [2023, 2021],
      predefinedColors: ["blue", "green", "yellow", "orange", "purple", "red"],
    };
  },
  mounted() {
    this.initMap();
    this.fetchElectionResults();
  },
  watch: {
    selectedParties() {
      this.addMarkers();
    },
  },
  methods: {
    async fetchElectionResults() {
      try {
        const response = await fetch(`${config.apiBaseUrl}/api/map-results?year=${this.selectedYear}`);
        if (!response.ok) throw new Error("Ophalen van verkiezingsresultaten mislukt");

        this.electionData = await response.json();

        this.selectedParties = [];
        this.partyColors = {};

        this.generatePartyColors();

        this.addMarkers();
      } catch (err) {
        console.error("Fout bij het ophalen van verkiezingsresultaten:", err);
      }
    },
    generatePartyColors() {
      const uniqueParties = [...new Set(this.electionData.map((item) => item.leadingParty))];
      let colorIndex = 0;
      uniqueParties.forEach((party) => {
        if (!this.partyColors[party]) {
          this.partyColors[party] = this.predefinedColors[colorIndex % this.predefinedColors.length];
          colorIndex++;
        }
      });
    },
    initMap() {
      this.map = L.map("map").setView([52.3676, 4.9041], 7);
      L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>',
      }).addTo(this.map);
      this.markerLayer = L.layerGroup().addTo(this.map);
    },
    addMarkers() {
      if (!this.map || !this.markerLayer) return;

      this.markerLayer.clearLayers();

      this.electionData.forEach((transaction) => {
        const cityName = transaction.cityName;
        const leadingParty = transaction.leadingParty;
        const votes = transaction.votes;

        if (
            !cityName ||
            (!this.selectedParties.length || this.selectedParties.includes(leadingParty))
        ) {
          const popupText = `<b>${cityName}</b><br>Leidende Partij: ${leadingParty}<br>Stemmen: ${votes}`;
          const color = this.partyColors[leadingParty] || "gray";
          const [lat, lng] = this.getCoordinatesForCity(cityName);

          if (!lat || !lng) return;

          L.marker([lat, lng], { icon: this.createIcon(color) })
              .bindPopup(popupText)
              .addTo(this.markerLayer);
        }
      });
    },
    createIcon(color) {
      return L.divIcon({
        className: "custom-icon",
        html: `<div style="background-color: ${color}; width: 12px; height: 12px; border-radius: 50%;"></div>`,
      });
    },
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
    onYearChange() {
      this.fetchElectionResults();
    },
  },
};
</script>
