<template>
  <div class="p-4">
    <h1 class="text-2xl font-bold mb-4 text-center text-gray-800 dark:text-gray-100">Stad Statistieken: {{ cityName }}</h1>

    <div v-if="cityData" class="bg-white dark:bg-gray-800 shadow-lg p-4 rounded-lg">
      <h2 class="text-xl font-semibold text-gray-800 dark:text-gray-100 mb-4">Verkiezingsresultaten voor {{ cityName }}</h2>

      <div class="space-y-2">
        <div>
          <h3 class="font-semibold text-gray-800 dark:text-gray-100">Leidende Partij:</h3>
          <p>{{ cityData.leadingParty }}</p>
        </div>

        <div>
          <h3 class="font-semibold text-gray-800 dark:text-gray-100">Aantal Stemmen voor Leidende Partij:</h3>
          <p>{{ cityData.votes }}</p>
        </div>
      </div>

      <div class="mt-2">
        <h3 class="font-semibold text-gray-800 dark:text-gray-100">Totaal Aantal Stemmen in {{ cityName }}:</h3>
        <p>{{ cityTotalVotes !== null ? cityTotalVotes : 'Niet beschikbaar' }}</p>
      </div>

      <div class="mt-2">
        <h3 class="font-semibold text-gray-800 dark:text-gray-100">Totaal Aantal Stemmen (Nederland):</h3>
        <p>{{ totalVotes[selectedYear] || 'Niet beschikbaar' }}</p>
      </div>

      <div class="mb-6 mt-2">
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
    </div>

    <div v-else class="text-center mt-4 text-gray-500 dark:text-gray-300">
      <p>Geen gegevens beschikbaar voor deze stad.</p>
    </div>
  </div>
</template>

<script>
import config from "../config";

export default {
  name: "CityStatistiekPage",
  data() {
    return {
      cityData: null,
      cityTotalVotes: null,
      totalVotes: {},
      cityName: this.$route.params.cityName,
      selectedYear: 2023,
      availableYears: [2023, 2021, 2017, 2012, 2010],
    };
  },
  mounted() {
    this.fetchCityData();
    this.fetchTotalVotes();
    this.fetchCityTotalVotes();
  },
  watch: {
    "$route.params.cityName"() {
      this.cityName = this.$route.params.cityName;
      this.fetchCityData();
    },
    selectedYear() {
      this.fetchCityData();
      this.fetchCityTotalVotes();
    },
  },
  methods: {
    async fetchCityData() {
      try {
        const response = await fetch(
            `${config.apiBaseUrl}/api/map-results?year=${this.selectedYear}`
        );

        if (!response.ok) throw new Error("Fout bij het ophalen van stadsgegevens");

        const data = await response.json();
        this.cityData = data.find(city => city.cityName === this.cityName) || null;
      } catch (error) {
        console.error("Fout bij het ophalen van stadstatistieken:", error);
      }
    },
    async fetchCityTotalVotes() {
      try {
        const response = await fetch(
            `${config.apiBaseUrl}/api/map-results/city-total-votes?year=${this.selectedYear}`
        );

        if (!response.ok) throw new Error("Fout bij het ophalen van stemmen per stad");

        const data = await response.json();
        const cityVotes = data.cities.find(city => city.cityName === this.cityName) || null;
        this.cityTotalVotes = cityVotes ? cityVotes.totalVotes : null;
      } catch (error) {
        console.error("Fout bij het ophalen van stadsstemmen:", error);
      }
    },
    async fetchTotalVotes() {
      try {
        const response = await fetch(
            `${config.apiBaseUrl}/api/map-results/total-votes-all`
        );

        if (!response.ok) throw new Error("Fout bij het ophalen van totale stemmen");

        this.totalVotes = await response.json();
      } catch (error) {
        console.error("Fout bij het ophalen van totale stemmen:", error);
      }
    },
    onYearChange() {
      console.log(`Jaar gewijzigd naar: ${this.selectedYear}`);
    },
  },
};
</script>