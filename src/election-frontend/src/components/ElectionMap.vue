<template>
  <div class="flex flex-col lg:flex-row">
    <!-- Checkboxes sectie: Alleen zichtbaar als we niet op de CityStatistiekPage zijn -->
    <div v-if="!isCityStatistiekPage" class="w-full lg:w-1/4 p-4 border-b lg:border-b-0 lg:border-r border-gray-300 bg-gray-50 dark:bg-gray-800 rounded-lg">
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

    <!-- Kaart sectie: Alleen zichtbaar als we niet op de CityStatistiekPage zijn -->
    <div v-if="!isCityStatistiekPage" class="w-full lg:w-3/4 p-4">
      <h1 class="text-xl font-bold mb-4 text-center text-gray-800 dark:text-gray-100">Verkiezingsstatistieken Steden</h1>
      <div
          :key="mapKey"
          id="map"
          class="w-full h-[400px] sm:h-[500px] lg:h-[600px] border border-gray-300 rounded shadow-md"
      ></div>
    </div>
<!-- FreakBob -->
    <router-view />
  </div>
</template>

<script>
import L from "leaflet";
import "leaflet/dist/leaflet.css";
import config from "../config";
import cityCoordinates from "../util/cityCoordinates";

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
      availableYears: [2023, 2021, 2017, 2012, 2010],
      predefinedColors: ["blue", "green", "yellow", "orange", "purple", "red"],
      mapKey: 0,
    };
  },
  computed: {
    isCityStatistiekPage() {
      return this.$route.name === "city-statistieken-per-stemlocatie";
    },
  },
  mounted() {
    if (!this.isCityStatistiekPage) {
      this.$nextTick(() => {
        if (document.getElementById("map")) {
          this.initMap();
          this.fetchElectionResults();
        }
      });
    }
  },
  beforeDestroy() {
    if (this.map) {
      this.map.remove();
    }
  },
  watch: {
    selectedParties() {
      this.addMarkers();
    },
    // dit is om kaart te refreshen
    $route(to, from) {
      if (!this.isCityStatistiekPage) {
        this.mapKey++;
        this.$nextTick(() => {
          if (document.getElementById("map")) {
            this.initMap();
            this.fetchElectionResults();
          }
        });
      }
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
      this.markerLayer.clearLayers();

      this.electionData.forEach((transaction) => {
        const cityName = transaction.cityName;
        const leadingParty = transaction.leadingParty;
        const votes = transaction.votes;

        if (cityName && (this.selectedParties.length === 0 || this.selectedParties.includes(leadingParty))) {
          const popupText = `
            <b>${cityName}</b><br>
            Leidende Partij: ${leadingParty}<br>
            Stemmen: ${votes}<br>
            <button
              class="to-info-button mt-2 px-4 py-2 bg-blue-500 text-white border-none rounded cursor-pointer"
            >
              Toon Stad Informatie
            </button>
          `;
          const color = this.partyColors[leadingParty] || "gray";
          const [lat, lng] = this.getCoordinatesForCity(cityName);

          if (!lat || !lng) return;

          const marker = L.marker([lat, lng], { icon: this.createIcon(color) }).bindPopup(popupText).addTo(this.markerLayer);

          marker.on("popupopen", (event) => {
            const button = event.popup._contentNode.querySelector(".to-info-button");
            if (button) {
              button.addEventListener("click", () => {
                this.showCityInfo(cityName);
              });
            }
          });
        }
      });
    },
    showCityInfo(cityName) {
      try {
        this.$router.push({
          name: "city-statistieken-per-stemlocatie",
          params: { cityName },
        });
        console.log("Navigeren naar stad:", cityName);
      } catch (error) {
        console.error("Navigatiefout:", error);
      }
    },

    createIcon(color) {
      return L.divIcon({
        className: "custom-icon",
        html: `<div style="background-color: ${color}; width: 12px; height: 12px; border-radius: 50%;"></div>`,
      });
    },
    getCoordinatesForCity(cityName) {
      if (!cityCoordinates[cityName]) {
        console.warn(`Geen coördinaten beschikbaar voor: ${cityName}`);
      }
      return cityCoordinates[cityName] || null;
    },
    onYearChange() {
      this.fetchElectionResults();
    },
  },
};
</script>
