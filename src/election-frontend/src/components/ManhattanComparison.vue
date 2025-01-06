<template>
  <div class="p-6">
    <h1 class="text-2xl font-bold mb-4">Manhattan Distance Comparison</h1>

    <div class="mb-4">
      <label for="year" class="block text-lg font-medium">Select Year:</label>
      <select id="year" v-model="selectedYear" class="border border-gray-300 rounded px-3 py-2">
        <option v-for="year in years" :key="year" :value="year">{{ year }}</option>
      </select>
    </div>

    <button
        @click="compareManhattanDistance"
        class="bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600 transition"
    >
      Compare
    </button>

    <div v-if="comparisonResults" class="mt-6">
      <h2 class="text-xl font-semibold">Comparison Results</h2>
      <ul>
        <li v-for="(distance, city) in comparisonResults.cityDistances" :key="city">
          {{ city }}: {{ distance }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      years: [2021, 2022, 2023],
      selectedYear: 2023,
      comparisonResults: null,
    };
  },
  methods: {
    async compareManhattanDistance() {
      try {
        const response = await fetch(`/election-results/compare-manhattan?year=${this.selectedYear}`);
        this.comparisonResults = await response.json();
      } catch (error) {
        console.error("Error fetching Manhattan comparison results:", error);
      }
    },
  },
};
</script>

<style scoped>
</style>
