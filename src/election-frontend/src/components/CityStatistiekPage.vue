<template>
  <div class="flex flex-wrap">
    <!-- Nationale Verkiezingsresultaten en Alle Stad Statistieken naast elkaar -->
    <div class="p-6 w-full md:w-2/5">
      <h1 class="text-3xl font-bold mb-6 text-center text-gray-800 dark:text-gray-100">Nederlandse Verkiezingsresultaten</h1>
      <div class="chart" v-if="nationalChartData">
        <Chart type="line" :data="nationalChartData" :options="nationalChartOptions" class="h-[40rem]" />
      </div>
    </div>

    <div class="p-6 w-full md:w-3/5">
      <h1 class="text-3xl font-bold mb-6 text-center text-gray-800 dark:text-gray-100">Totale Stemmen per Stad</h1>

      <!-- Dropdown voor jaarselectie -->
      <div class="mb-6">
        <label for="year-stats" class="block font-bold mb-3 text-gray-800 dark:text-gray-100">Selecteer Jaar:</label>
        <select
            id="year-stats"
            v-model="selectedYear"
            @change="onYearChange"
            class="bg-white dark:bg-gray-900 border border-gray-300 dark:border-gray-700 rounded px-3 py-2 w-full"
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

      <div class="chart" v-if="chartData">
        <Chart type="bar" :data="chartData" :options="chartOptions" class="h-[40rem]" />
      </div>
    </div>

    <!-- Aantal Stemmen voor Leidende Partij hieronder -->
    <div class="p-6 w-full">
      <h1 class="text-3xl font-bold mb-6 text-center text-gray-800 dark:text-gray-100">Aantal Stemmen voor Leidende Partij</h1>
      <div class="mb-6">
        <label for="year-stats" class="block font-bold mb-3 text-gray-800 dark:text-gray-100">Selecteer Jaar:</label>
        <select
            id="year-stats"
            v-model="selectedYear"
            @change="onYearChange"
            class="bg-white dark:bg-gray-900 border border-gray-300 dark:border-gray-700 rounded px-3 py-2 w-full"
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
      <div class="chart" v-if="leadingPartyChartData">
        <Chart type="bar" :data="leadingPartyChartData" :options="leadingPartyChartOptions" class="h-[40rem]" />
      </div>
    </div>

    <!-- Stad Statistieken: Amsterdam -->
    <div class="p-6 w-full">
      <div class="max-w-2xl mx-auto">
        <h1 class="text-3xl font-bold mb-6 text-center text-gray-800 dark:text-gray-100">Stad Statistieken: {{ cityName }}</h1>

        <div v-if="cityData" class="bg-white dark:bg-gray-800 shadow-lg p-6 rounded-lg">
          <h2 class="text-2xl font-semibold text-gray-800 dark:text-gray-100 mb-6">Verkiezingsresultaten voor {{ cityName }}</h2>

          <div class="space-y-4">
            <div>
              <h3 class="font-semibold text-gray-800 dark:text-gray-100">Leidende Partij:</h3>
              <p class="text-gray-700 dark:text-gray-300">{{ cityData.leadingParty }}</p>
            </div>

            <div>
              <h3 class="font-semibold text-gray-800 dark:text-gray-100">Aantal Stemmen voor Leidende Partij:</h3>
              <p class="text-gray-700 dark:text-gray-300">{{ cityData.votes }}</p>
            </div>
          </div>

          <div class="mt-4">
            <h3 class="font-semibold text-gray-800 dark:text-gray-100">Totaal Aantal Stemmen in {{ cityName }}:</h3>
            <p class="text-gray-700 dark:text-gray-300">{{ cityTotalVotes !== null ? cityTotalVotes : 'Niet beschikbaar' }}</p>
          </div>

          <div class="mt-4">
            <h3 class="font-semibold text-gray-800 dark:text-gray-100">Totaal Aantal Stemmen (Nederland):</h3>
            <p class="text-gray-700 dark:text-gray-300">{{ totalVotes[selectedYear] || 'Niet beschikbaar' }}</p>
          </div>

          <div class="mb-8 mt-4">
            <label for="year" class="block font-bold mb-3 text-gray-800 dark:text-gray-100">Selecteer Jaar:</label>
            <select
                id="year"
                v-model="selectedYear"
                @change="onYearChange"
                class="bg-white dark:bg-gray-900 border border-gray-300 dark:border-gray-700 rounded px-3 py-2 w-full"
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

        <div v-else class="text-center mt-6 text-gray-500 dark:text-gray-300">
          <p>Geen gegevens beschikbaar voor deze stad.</p>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import Chart from "primevue/chart";
import config from "../config";

export default {
  name: "CityStatistiekPage",
  components: { Chart },
  data() {
    return {
      cityData: null,
      cityTotalVotes: null,
      totalVotes: {},
      cityName: this.$route.params.cityName,
      selectedYear: 2023,
      availableYears: [2023, 2021, 2017, 2012, 2010],
      chartData: null,
      chartOptions: null,
      nationalChartData: null,
      nationalChartOptions: null,
      leadingPartyChartData: null,
      leadingPartyChartOptions: null,
    };
  },
  mounted() {
    this.fetchCityData();
    this.fetchTotalVotes();
    this.fetchCityTotalVotes();
    this.setupChart();
    this.setupNationalChart();
    this.setupLeadingPartyChart();
  },
  watch: {
    "$route.params.cityName"() {
      this.cityName = this.$route.params.cityName;
      this.fetchCityData();
      this.setupChart();
    },
    selectedYear() {
      this.fetchCityData();
      this.fetchCityTotalVotes();
      this.setupChart();
      this.setupLeadingPartyChart();
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
    async setupChart() {
      try {
        const response = await fetch(
            `${config.apiBaseUrl}/api/map-results/city-total-votes?year=${this.selectedYear}`
        );

        if (!response.ok) throw new Error("Fout bij het ophalen van gegevens voor de grafiek");

        const data = await response.json();
        const cities = data.cities || [];

          this.chartData = {
            labels: cities.map(city => city.cityName),
            datasets: [
              {
                label: "Total Votes per stad",
                type: "bar",
                data: cities.map(city => city.totalVotes),
                backgroundColor: "#42A5F5",
              },
            ],
          };

          this.chartOptions = {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
              legend: {
                position: "top",
              },
            },
            scales: {
              x: {
                stacked: false,
              },
              y: {
                stacked: false,
              },
            },
          };
        } catch (error) {
          console.error("Fout bij het instellen van de grafiek:", error);
        }
      },



      async setupLeadingPartyChart() {
        try {
          const response = await fetch(
              `${config.apiBaseUrl}/api/map-results/city-leading-party-votes?year=${this.selectedYear}`
          );

          if (!response.ok) throw new Error("Fout bij het ophalen van gegevens voor de leidende partij grafiek");

          const data = await response.json();

          const cityVotesMap = {};

          data.cities.forEach(city => {
            const key = `${city.cityName} (${city.leadingParty})`;
            if (!cityVotesMap[key]) {
              cityVotesMap[key] = 0;
            }
            cityVotesMap[key] += city.votes;
          });

          const labels = Object.keys(cityVotesMap);
          const votes = Object.values(cityVotesMap);

          const datasets = [
            {
              label: "Aantal stemmen",
              data: votes,
              backgroundColor: "#FF6384",
            },
          ];

          this.leadingPartyChartData = {
            labels: labels,
            datasets: datasets,
          };

        } catch (error) {
          console.error(error.message);
        }
      },



      async setupNationalChart() {
        try {
          const response = await fetch(
              `${config.apiBaseUrl}/api/map-results/total-votes-all`
          );

          if (!response.ok) throw new Error("Fout bij het ophalen van gegevens voor de nationale grafiek");

          const data = await response.json();

          this.nationalChartData = {
            labels: Object.keys(data),
            datasets: [
              {
                label: "Totaal stemmen in Nederland",
                type: "line",
                data: Object.values(data),
                borderColor: "#FF6384",
                backgroundColor: "rgba(255, 99, 132, 0.2)",
              },
            ],
          };

          this.nationalChartOptions = {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
              legend: {
                position: "top",
              },
            },
            scales: {
              x: {
                stacked: false,
              },
              y: {
                stacked: false,
              },
            },
          };
        } catch (error) {
          console.error("Fout bij het instellen van de nationale grafiek:", error);
        }
      },
      onYearChange() {
        console.log(`Jaar gewijzigd naar: ${this.selectedYear}`);
      },
    },
  };
</script>