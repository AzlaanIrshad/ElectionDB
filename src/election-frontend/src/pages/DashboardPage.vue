<template>
  <div class="dashboard-container max-w-6xl mx-auto py-12 px-4">
    <h1 class="text-3xl font-bold text-center mb-6 text-gray-900 dark:text-gray-100">
      Verkiezingsresultaten per Stad
    </h1>

    <!-- Laden -->
    <div v-if="loading" class="text-center">
      <p>Bezig met laden...</p>
    </div>

    <!-- Zoekbalk -->
    <div class="search-bar mb-8">
      <div class="max-w-md mx-auto">
        <input
            type="text"
            v-model="searchQuery"
            placeholder="Zoek een stad..."
            class="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-blue-300 dark:border-gray-600 dark:bg-gray-800 dark:text-gray-100 dark:placeholder-gray-400"
        />
      </div>
    </div>
    <div class="mb-4">
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
    <!-- Verkiezingsresultaten per Stad -->
    <div v-if="filteredResults.length" class="results-container">
      <div v-for="city in filteredResults" :key="city.cityId" class="city-item border-b border-gray-300 py-4 dark:border-gray-600">
        <details class="group">
          <summary class=" font-semibold text-lg text-gray-800 dark:text-gray-100 group-open:mb-2">
            Stad: {{ city.cityName }} (Totaal Stemmen: {{ city.totalVotes }})
          </summary>

          <!-- Chart -->
          <TotalPartyVoteBarChart :chartData="city.chartData" />
          <!-- ---- -->
          <div class="parties mt-4">
            <ul>
              <li v-for="party in city.parties" :key="party.partyId" class="mb-4">
                  <summary class=" text-gray-800 dark:text-gray-300">
                    Partij: {{ party.partyName }} (Stemmen: {{ party.totalVotes }})
                  </summary>
              </li>
            </ul>
          </div>
        </details>
      </div>
    </div>

    <!-- Geen Resultaten Gevonden Bericht -->
    <p v-else class="text-center text-gray-500 dark:text-gray-400">Geen steden gevonden.</p>
  </div>
</template>

<script>
import TotalPartyVoteBarChart from '../components/TotalPartyVoteBarChart.vue';
import config from '../config';

export default {
  name: "ElectionDashboard",
  components: {
    TotalPartyVoteBarChart,
  },
  data() {
    return {
      searchQuery: '',
      electionData: [],
      loading: false,
      error: null,
      selectedYear: 2023,
      availableYears: [2023, 2021, 2017],
    };
  },
  computed: {
    filteredResults() {
      return this.electionData.filter(city =>
          city.cityName.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    },
  },
  methods: {
    async fetchElectionResults() {
      this.loading = true;
      this.error = null;
      try {
        const response = await fetch(`${config.apiBaseUrl}/api/election-results/all?years=${this.selectedYear}`);
        if (!response.ok) {
          throw new Error("Ophalen van verkiezingsresultaten mislukt");
        }
        const data = await response.json();
        console.log("Election Data:", data);
        this.electionData = data[this.selectedYear]; // Adjust for specific years if needed
      } catch (err) {
        this.error = err.message;
      } finally {
        this.loading = false;
      }
    },
    onYearChange() {
      this.fetchElectionResults();
    },
  },

  mounted() {
    this.fetchElectionResults();
  },
};
</script>

