<template>
  <div class="p-8 max-w-4xl mx-auto bg-white dark:bg-gray-800 rounded-lg shadow-lg">
    <!-- Header -->
    <h1 class="text-3xl font-bold mb-6 text-center text-blue-600 dark:text-blue-400">
       Vergelijking van steden en verkiezingsresultaat
    </h1>

    <!-- Year Selector -->
    <div class="mb-6">
      <label for="year" class="block text-lg font-semibold mb-2 text-gray-700 dark:text-gray-300">
        Selecteer Jaar:
      </label>
      <select
          id="year"
          v-model="selectedYear"
          class="w-full border border-gray-300 dark:border-gray-600 rounded-lg px-4 py-2 text-gray-700 dark:text-gray-300 bg-white dark:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
      >
        <option v-for="year in years" :key="year" :value="year">
          {{ year }}
        </option>
      </select>
    </div>

    <!-- Fetch Button -->
    <div class="flex justify-center">
      <button
          @click="fetchCities"
          :disabled="isLoading"
          class="bg-blue-500 text-white font-medium px-6 py-3 rounded-lg shadow-lg hover:bg-blue-600 dark:bg-blue-700 dark:hover:bg-blue-800 transition disabled:bg-gray-400 disabled:cursor-not-allowed"
      >
        <span v-if="isLoading">Laden...</span>
        <span v-else>Zoek Steden</span>
      </button>
    </div>

    <!-- Error Message -->
    <div v-if="errorMessage" class="text-red-500 dark:text-red-400 mt-6 text-center">
      {{ errorMessage }}
    </div>

    <!-- Results Section -->
    <div v-if="closestCities.length || farthestCities.length || percentageDeviations.length" class="mt-8">
      <h2 class="text-2xl font-semibold text-gray-800 dark:text-gray-200 mb-4 text-center">
        Resultaten voor Jaar {{ selectedYear }}
      </h2>

      <!-- Closest Cities -->
      <div>
        <h3 class="text-xl font-bold text-blue-500 dark:text-blue-400 mb-2">
          Top 3 Dichtstbijzijnde Steden
        </h3>
        <ol class="bg-gray-100 dark:bg-gray-700 rounded-lg shadow-lg p-6">
          <li
              v-for="(city, index) in closestCities"
              :key="index"
              class="flex items-center justify-between bg-white dark:bg-gray-800 rounded-lg shadow-sm px-4 py-3 mb-4 border border-gray-200 dark:border-gray-600"
          >
            <div>
              <span class="text-lg font-bold text-blue-500 dark:text-blue-400">
                #{{ index + 1 }}: {{ city.cityName }}
              </span>
            </div>
          </li>
        </ol>
      </div>

      <!-- Farthest Cities -->
      <div class="mt-8">
        <h3 class="text-xl font-bold text-red-500 dark:text-red-400 mb-2">
          Top 3 Verste Steden
        </h3>
        <ol class="bg-gray-100 dark:bg-gray-700 rounded-lg shadow-lg p-6">
          <li
              v-for="(city, index) in farthestCities"
              :key="index"
              class="flex items-center justify-between bg-white dark:bg-gray-800 rounded-lg shadow-sm px-4 py-3 mb-4 border border-gray-200 dark:border-gray-600"
          >
            <div>
              <span class="text-lg font-bold text-red-500 dark:text-red-400">
                #{{ index + 1 }}: {{ city.cityName }}
              </span>
            </div>
          </li>
        </ol>
      </div>

      <!-- Percentage Deviations -->
      <div class="mt-8">
        <h3 class="text-xl font-bold text-purple-500 dark:text-purple-400 mb-2">
          Percentage Afwijkingen
        </h3>
        <ol class="bg-gray-100 dark:bg-gray-700 rounded-lg shadow-lg p-6">
          <li
              v-for="(city, index) in percentageDeviations"
              :key="index"
              class="flex items-center justify-between bg-white dark:bg-gray-800 rounded-lg shadow-sm px-4 py-3 mb-4 border border-gray-200 dark:border-gray-600"
          >
            <div>
              <span class="text-lg font-bold text-purple-500 dark:text-purple-400">
                {{ city.cityName }}
              </span>
              <p class="text-sm text-gray-600 dark:text-gray-400">
                Afwijking: {{ city.percentageDeviation.toFixed(2) }}%
              </p>
            </div>
          </li>
        </ol>
      </div>
    </div>
  </div>
</template>

<script>
import config from "../config";

export default {
  data() {
    return {
      years: [2023, 2021, 2017, 2012, 2010],
      selectedYear: 2023,
      closestCities: [],
      farthestCities: [],
      percentageDeviations: [],
      isLoading: false,
      errorMessage: null,
    };
  },
  methods: {
    async fetchCities() {
      this.isLoading = true;
      this.errorMessage = null;
      this.closestCities = [];
      this.farthestCities = [];
      this.percentageDeviations = [];

      try {
        const closestResponse = await fetch(
            `${config.apiBaseUrl}/api/comparison/closest-cities?year=${this.selectedYear}`
        );
        if (!closestResponse.ok) {
          throw new Error(
              `API Error: ${closestResponse.status} - ${closestResponse.statusText}`
          );
        }
        this.closestCities = await closestResponse.json();

        const farthestResponse = await fetch(
            `${config.apiBaseUrl}/api/comparison/farthest-cities?year=${this.selectedYear}`
        );
        if (!farthestResponse.ok) {
          throw new Error(
              `API Error: ${farthestResponse.status} - ${farthestResponse.statusText}`
          );
        }
        this.farthestCities = await farthestResponse.json();

        const deviationResponse = await fetch(
            `${config.apiBaseUrl}/api/comparison/city-percentage-deviation?year=${this.selectedYear}`
        );
        if (!deviationResponse.ok) {
          throw new Error(
              `API Error: ${deviationResponse.status} - ${deviationResponse.statusText}`
          );
        }
        this.percentageDeviations = await deviationResponse.json();
      } catch (error) {
        console.error("Error fetching cities:", error);
        this.errorMessage = "Failed to fetch cities. Please try again.";
      } finally {
        this.isLoading = false;
      }
    },
  },
};
</script>

<style>
</style>
