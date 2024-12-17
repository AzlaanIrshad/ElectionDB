<template>
  <div class="charts-container max-w-full sm:max-w-7xl mx-auto bg-white dark:bg-gray-700 p-6 rounded-lg lg:w-2/5 sm:w-full">
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
      <h2 class="text-lg sm:text-xl font-bold text-center mb-4 text-gray-800 dark:text-gray-100">
        Totale Stemmen Nederland {{ selectedYear }}
      </h2>
      <Chart
          v-if="currentChart"
          :type="'doughnut'"
          :data="currentChart.data"
          :options="currentChart.options"
          :fontColor ="'#000'"
          class="max-w-full"
      />
      <div v-else class="text-center text-gray-500 dark:text-gray-300">
        Geen gegevens beschikbaar voor het geselecteerde jaar.
      </div>
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
      availableYears: [2023, 2021, 2017, 2012, 2010],
    };
  },
  computed: {
    isDarkMode() {
      return document.documentElement.classList.contains('dark');
    },
    currentChart() {
      return this.charts.find((chart) => chart.year === this.selectedYear) || null;
    },
  },
  watch: {
    isDarkMode() {
      this.updateChartColors();
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
                  labels: {
                    color: this.isDarkMode ? '#ffffff' : '#000000',
                  },
                },
                datalabels: {
                  color: this.isDarkMode ? '#ffffff' : '#000000',
                  font: {
                    weight: 'bold',
                  },
                },
              },
              elements: {
                arc: {
                  borderWidth: 1,
                },
              },
              animation: {
                animateRotate: true,
                animateScale: true,
              },
            },

          };
        });
      } catch (err) {
        console.error("Fout bij het ophalen van verkiezingsresultaten:", err);
      }
    },
    updateChartColors() {
      if (this.currentChart) {
        this.currentChart.options.plugins.legend.labels.color = this.isDarkMode ? '#ffffff' : '#000000';
        this.currentChart.options.plugins.datalabels.color = this.isDarkMode ? '#ffffff' : '#000000';
      }
    },
    onYearChange() {
      console.log(`Jaar gewijzigd naar: ${this.selectedYear}`);
    },
  },
};
</script>
