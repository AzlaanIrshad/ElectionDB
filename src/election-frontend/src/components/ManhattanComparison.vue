<template>
  <div class="p-6">
    <h1 class="text-2xl font-bold mb-4">Closest City to Election Results</h1>

    <!-- Year Selector -->
    <div class="mb-4">
      <label for="year" class="block text-lg font-medium">Select Year:</label>
      <select
          id="year"
          v-model="selectedYear"
          class="border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-green-500"
      >
        <option v-for="year in years" :key="year" :value="year">{{ year }}</option>
      </select>
    </div>

    <!-- Fetch Button -->
    <button
        @click="fetchClosestCity"
        :disabled="isLoading"
        class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 transition disabled:bg-gray-400 disabled:cursor-not-allowed"
    >
      <span v-if="isLoading">Loading...</span>
      <span v-else>Find Closest City</span>
    </button>

    <!-- Error Message -->
    <div v-if="errorMessage" class="text-red-500 mt-4">
      {{ errorMessage }}
    </div>

    <!-- Results -->
    <div v-if="closestCity" class="mt-6">
      <h2 class="text-xl font-semibold mb-2">Closest City</h2>
      <p><strong>City:</strong> {{ closestCity.cityName }}</p>
      <p><strong>Distance:</strong> {{ closestCity.distance }}</p>
    </div>
  </div>
</template>

<script>
import config from '../config';

export default {
  data() {
    return {
      years: [2023, 2021, 2017, 2012, 2010],
      selectedYear: 2023,
      closestCity: null,
      isLoading: false,
      errorMessage: null,
    };
  },
  methods: {
    async fetchClosestCity() {
      this.isLoading = true;
      this.errorMessage = null;
      this.closestCity = null;

      try {
        const response = await fetch(`${config.apiBaseUrl}/api/comparison/closest-city?year=${this.selectedYear}`);

        if (!response.ok) {
          throw new Error(`API Error: ${response.status} - ${response.statusText}`);
        }
        this.closestCity = await response.json();
      } catch (error) {
        console.error("Error fetching closest city:", error);
        this.errorMessage = "Failed to fetch closest city. Please try again.";
      } finally {
        this.isLoading = false;
      }
    },
  },
};
</script>

<style scoped>
</style>
