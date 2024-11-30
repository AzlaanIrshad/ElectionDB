<template>
  <div class="charts-container grid grid-cols-1 sm:grid-cols-2 gap-8 max-w-7xl mx-auto bg-white dark:bg-gray-700 p-6 rounded-lg">
    <div v-for="(chart, index) in charts" :key="index" class="chart-container">
      <h2 class="text-lg md:text-xl font-bold text-center mb-4 text-gray-800 dark:text-gray-100">
        Totale Stemmen Nederland {{ chart.year }}
      </h2>
      <Chart :type="'doughnut'" :data="chart.data" :options="chart.options" class="max-w-full" />
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
      charts: [], // Opslag voor de data van afzonderlijke charts
    };
  },
  mounted() {
    this.fetchElectionResults();
  },
  methods: {
    async fetchElectionResults() {
      try {
        const years = [2021, 2023];
        const response = await fetch(`${config.apiBaseUrl}/api/election-results?years=${years.join(",")}`);
        if (!response.ok) throw new Error("Ophalen van verkiezingsresultaten mislukt");
        const electionData = await response.json();

        this.charts = years.map((year) => {
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
  },
};
</script>
