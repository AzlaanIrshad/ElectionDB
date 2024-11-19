<template>
  <div>
    <h1 class="text-2xl font-bold mb-4">Election Map</h1>
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
            :style="{
            backgroundColor: color,
          }"
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

export default {
  name: "ElectionMap",
  data() {
    return {
      map: null,
      markerLayer: null,
      electionData: [],
      loading: true,
      error: null,
      selectedParties: [],
      partyColors: {
        "PVV (Partij voor de Vrijheid)": "blue",
        "VVD": "orange",
        "D66": "yellow",
        "GROENLINKS / Partij van de Arbeid (PvdA)": "green",
      },
    };
  },
  mounted() {
    this.initMap();
    this.fetchElectionResults();
  },
  methods: {
    /**
     * Fetches election results from the backend and updates the map with markers.
     */
    async fetchElectionResults() {
      this.loading = true;
      this.error = null;
      try {
        const response = await fetch("http://localhost:8080/api/election-results");
        if (!response.ok) {
          throw new Error("Failed to fetch election results");
        }
        const data = await response.json();
        this.electionData = data;
        this.addMarkers();
      } catch (err) {
        this.error = err.message;
        console.error("Error fetching election results:", err);
      } finally {
        this.loading = false;
      }
    },

    /**
     * Initializes the Leaflet map and adds a tile layer.
     */
    initMap() {
      this.map = L.map("map").setView([52.3676, 4.9041], 7);
      L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
        attribution:
            '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
      }).addTo(this.map);
      this.markerLayer = L.layerGroup().addTo(this.map);
    },

    /**
     * Adds markers to the map based on election data and selected parties.
     */
    addMarkers() {
      if (!this.map || !this.markerLayer) {
        console.warn("Map or marker layer is not initialized");
        return;
      }

      this.markerLayer.clearLayers();

      this.electionData.forEach((transaction) => {
        const cityName = transaction?.managingAuthority?.authorityIdentifier?.value;

        if (!cityName) {
          console.warn("City name is undefined in transaction:", transaction);
          return;
        }

        const contests = transaction?.count?.election?.contests?.contests || [];
        let leadingParty = null;

        contests.forEach((contest) => {
          contest?.totalVotes?.selections?.forEach((selection) => {
            const partyName = selection?.affiliationIdentifier?.registeredName;
            const validVotes = selection?.validVotes || 0;

            if (!leadingParty || validVotes > (leadingParty.validVotes || 0)) {
              leadingParty = {
                name: partyName,
                validVotes,
              };
            }
          });
        });

        if (!leadingParty?.name) {
          console.warn("Leading party could not be determined for:", cityName);
          return;
        }

        if (
            this.selectedParties.length &&
            !this.selectedParties.includes(leadingParty.name)
        ) {
          return;
        }

        const popupText = `<b>${cityName}</b><br>Leading Party: ${leadingParty.name}`;
        const color = this.partyColors[leadingParty.name] || "gray";

        const [lat, lng] = this.getCoordinatesForCity(cityName);
        if (!lat || !lng) {
          console.warn("Coordinates not found for city:", cityName);
          return;
        }

        L.circleMarker([lat, lng], {
          color,
          radius: 10,
          fillOpacity: 0.4,
        })
            .bindPopup(popupText)
            .addTo(this.markerLayer);
      });

      this.markerLayer.addTo(this.map);
    },

    /**
     * Retrieves coordinates for a given city name.
     * @param {string} cityName - The name of the city.
     * @returns {Array} - Array containing latitude and longitude.
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
    /**
     * Watches changes to the selected parties and updates the map markers.
     */
    selectedParties() {
      this.addMarkers();
    },
  },
};
</script>