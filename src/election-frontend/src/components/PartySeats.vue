<template>
  <div class="charts-container max-w-full sm:max-w-7xl mx-auto bg-white dark:bg-gray-700 p-6 rounded-lg">
    <!-- Dropdown voor partijselectie -->
    <div class="mb-6">
      <label for="party" class="block font-bold mb-2 text-gray-800 dark:text-gray-100">Selecteer Partij:</label>
      <select
          id="party"
          v-model="selectedParty"
          @change="onPartyChange"
          class="bg-white dark:bg-gray-900 border border-gray-300 dark:border-gray-700 rounded px-2 py-1 w-full"
      >
        <option
            v-for="party in availableParties"
            :key="party"
            :value="party"
        >
          {{ party }}
        </option>
      </select>
    </div>

    <!-- Line Chart voor Zetels per Partij over de jaren -->
    <div class="chart-container mt-6">
      <h2 class="text-lg md:text-xl font-bold text-center mb-4 text-gray-800 dark:text-gray-100">
        Zetels per Partij (2017-2023)
      </h2>
      <Chart
          v-if="lineChartData"
          :type="'line'"
          :data="lineChartData"
          :options="lineChartOptions"
          class="max-w-full"
      />
    </div>

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
    <div v-if="currentChart && currentChart.data.datasets[0].data.some(value => value > 0)" class="chart-container">
      <h2 class="text-lg md:text-xl font-bold text-center mb-4 text-gray-800 dark:text-gray-100">
        Zetels per Partij {{ selectedYear }}
      </h2>
      <Chart
          :type="'bar'"
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
  name: "PartySeats",
  components: { Chart },
  data() {
    return {
      seatData: {},
      lineChartData: null,
      selectedParty: null,
      selectedYear: 2023,
      availableYears: [2017, 2021, 2023],
      availableParties: [],
      charts: [],
    };
  },
  mounted() {
    this.fetchPartySeatsData();
  },
  computed: {
    currentChart() {
      return this.charts.find((chart) => chart.year === this.selectedYear) || null;
    },
  },
  methods: {
    async fetchPartySeatsData() {
      try {
        const response = await fetch(
            `${config.apiBaseUrl}/api/seat-allocation?years=${this.availableYears.join(",")}`
        );
        if (!response.ok) throw new Error("Ophalen van zetels per partij mislukt");
        const seatData = await response.json();

        this.seatData = seatData;

        const allParties = Object.keys(seatData[this.availableYears[0]] || {});
        this.availableParties = allParties.filter((party) =>
            this.availableYears.some((year) => seatData[year]?.[party] > 0)
        );

        if (!this.selectedParty && this.availableParties.length > 0) {
          this.selectedParty = this.availableParties[0];
        }

        this.updateLineChartData();
        this.generateBarCharts();
      } catch (err) {
        console.error("Fout bij het ophalen van zetels per partij:", err);
      }
    },
    updateLineChartData() {
      if (!this.selectedParty || !this.seatData) return;

      this.lineChartData = {
        labels: this.availableYears,
        datasets: [
          {
            label: this.selectedParty,
            data: this.availableYears.map(
                (year) => (this.seatData[year]?.[this.selectedParty] ?? 0)
            ),
            borderColor: this.getRandomColor(),
            tension: 0.1,
          },
        ],
      };
    },
    generateBarCharts() {
      this.charts = this.availableYears.map((year) => {
        const yearData = this.seatData[year] || {};
        const labels = Object.keys(yearData).filter(party => yearData[party] > 0);
        const data = labels.map(label => yearData[label] ?? 0);
        const backgroundColors = labels.map(
            (_, index) => `hsl(${(index / labels.length) * 360}, 70%, 70%)`
        );

        return {
          year,
          data: {
            labels,
            datasets: [
              {
                label: "Zetels",
                data,
                backgroundColor: backgroundColors,
              },
            ],
          },
          options: {
            responsive: true,
            scales: {
              x: { title: { display: true, text: "Partijen" } },
              y: { title: { display: true, text: "Aantal Zetels" }, beginAtZero: true },
            },
            plugins: { legend: { position: "top" } },
          },
        };
      });
    },
    onPartyChange() {
      this.updateLineChartData();
    },
    onYearChange() {
      this.generateBarCharts();
    },
    getRandomColor() {
      return `hsl(${Math.random() * 360}, 70%, 50%)`;
    },
  },
};
</script>

