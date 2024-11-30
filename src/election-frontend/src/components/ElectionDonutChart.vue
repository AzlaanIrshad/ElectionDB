<template>
  <div class="charts-container max-w-7xl mx-auto bg-white dark:bg-gray-700 p-6 rounded-lg w-2/5">
    <!-- Dropdown voor jaarselectie -->
    <div class="mb-6">
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

    <!-- Chart sectie -->
    <div class="chart-container">
      <h2 class="text-lg md:text-xl font-bold text-center mb-4 text-gray-800 dark:text-gray-100">
        Totale Stemmen Nederland {{ selectedYear }}
      </h2>
      <Chart
          v-if="currentChart"
          :type="'doughnut'"
          :data="currentChart.data"
          :options="currentChart.options"
          class="max-w-full"
      />
    </div>
  </div>
</template>

<script>
import Chart from "primevue/chart";
import config from "../config";

export default {
  name: "ElectionDonutCharts",
  components: { Chart },
  data() {
    return {
      charts: [],
      selectedYear: 2023,
      availableYears: [2017, 2021, 2023],
    };
  },
  computed: {
    currentChart() {
      return this.charts.find((chart) => chart.year === this.selectedYear) || null;
    },
  },
  mounted() {
    this.fetchElectionResults();
  },
  methods: {
    async fetchElectionResults() {
      try {
        const response = await fetch(
            `${config.apiBaseUrl}/api/election-results?years=${this.availableYears.join(",")}`
        );
        if (!response.ok) throw new Error("Ophalen van verkiezingsresultaten mislukt");
        const electionData = await response.json();

        this.charts = this.availableYears.map((year) => {
          const yearData = electionData[year] || {};
          const labels = Object.keys(yearData);
          const data = Object.values(yearData);
          const backgroundColors = labels.map(
              (_, index) => `hsl(${(index / labels.length) * 360}, 70%, 70%)`
          );

          return {
            year,
            data: {
              labels,
              datasets: [
                {
                  data,
                  backgroundColor: backgroundColors,
                },
              ],
            },
            options: {
              responsive: true,
              plugins: {
                legend: {
                  position: "top",
                },
              },
            },
          };
        });
      } catch (err) {
        console.error("Fout bij het ophalen van verkiezingsresultaten:", err);
      }
    },
    onYearChange() {
    },
  },
};
</script>