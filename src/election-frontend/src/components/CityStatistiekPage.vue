<template>
  <div class="p-4">
    <h1 class="text-2xl font-bold mb-4 text-center text-gray-800 dark:text-gray-100">Stad Statistieken: {{ cityName }}</h1>

    <div v-if="cityData" class="bg-white dark:bg-gray-800 shadow-lg p-4 rounded-lg">
      <h2 class="text-xl font-semibold text-gray-800 dark:text-gray-100 mb-4">Verkiezingsresultaten voor {{ cityName }}</h2>

      <div class="space-y-4">
        <div>
          <h3 class="font-semibold text-gray-800 dark:text-gray-100">Leidende Partij:</h3>
          <p>{{ cityData }}</p>
        </div>

        <div>
          <h3 class="font-semibold text-gray-800 dark:text-gray-100">Aantal Stemmen:</h3>
          <p>{{ cityData }}</p>
        </div>

        <div>
          <h3 class="font-semibold text-gray-800 dark:text-gray-100">Opkomstpercentage:</h3>
          <p>{{ cityData }}%</p>
        </div>

        <div>
          <h3 class="font-semibold text-gray-800 dark:text-gray-100">Aantal Zetels:</h3>
          <p>{{ cityData }}</p>
        </div>
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
      cityName: this.$route.params.cityName,
    };
  },
  mounted() {
    this.fetchCityData();
  },
  watch: {
    "$route.params.cityName"() {
      this.cityName = this.$route.params.cityName;
      this.fetchCityData();
    },
  },
  methods: {
    async fetchCityData() {
      try {
        //const response = await fetch(`${config.apiBaseUrl}/api/map-results`);
        if (!response.ok) throw new Error("Fout bij het ophalen van stadsgegevens");

        const data = await response.json();
        this.cityData = data;
      } catch (error) {
        console.error("Fout bij het ophalen van stadstatistieken:", error);
      }
    },
  },
};
</script>