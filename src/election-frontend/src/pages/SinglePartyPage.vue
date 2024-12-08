<template>
  <div class="single-party-page p-5 dark:text-white">
    <h1 class="text-3xl font-bold mb-6 text-gray-800 dark:text-white">Party Details</h1>

    <!-- Loading State -->
    <div v-if="loading" class="text-center text-blue-500 dark:text-blue-300">
      <p>Loading party data...</p>
    </div>

    <!-- Error State -->
    <div v-if="error" class="text-center text-red-500 dark:text-red-300">
      <p>{{ error }}</p>
    </div>

    <!-- Party Data -->
    <div v-if="partyData && !loading" class="party-data">
      <h2 class="text-2xl font-semibold text-gray-700 dark:text-white">{{ partyData.partyName }}</h2>
      <p class="text-lg text-gray-600 dark:text-gray-300">Total Votes: {{ partyData.totalVotes }}</p>

      <!-- Display Candidate Chart -->
      <div v-if="partyData.chartDataForCandidates" class="mt-6">
        <PartyCandidateHorizontalBarChart
            :chart-data="partyData.chartDataForCandidates"
        />
      </div>

      <!-- No Candidates -->
      <div v-else class="mt-4 text-gray-500 dark:text-gray-400">
        <p>No candidates found for this party.</p>
      </div>

      <!-- Candidate Table -->
      <div class="candidate-table mt-8 flex justify-center flex-col items-center">
        <h3 class="text-xl font-semibold text-gray-700 dark:text-gray-300 mb-4">All Candidates</h3>
        <div class="overflow-x-auto bg-white dark:bg-gray-800 rounded-lg shadow w-1/2 ">
          <DataTable
              :value="partyData.candidates"
              :rows="5"
              class="custom table-auto w-full text-sm text-gray-800 bg-white dark:text-white dark:bg-gray-800"
              paginator
              :paginatorTemplate="'FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink RowsPerPageDropdown'"
          >
            <Column
                field="candidateId"
                header="ID"
                sortable
                class="tablecolumn px-0 py-0 text-center"
            />
            <Column
                field="name"
                header="Name"
                sortable
                class="tablecolumn px-0 py-0 text-left"
            />
            <Column
                field="validVotes"
                header="Votes"
                sortable
                class="tablecolumn px-0 py-0 text-right"
            />
          </DataTable>
        </div>
      </div>
    </div>
  </div>
</template>



<script>
import config from "../config";
import PartyCandidateHorizontalBarChart from "../components/CandidateHorizontalBarChart.vue";

import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import Row from 'primevue/row';


export default {
  name: "SinglePartyPage",
  components: {
    PartyCandidateHorizontalBarChart,
    DataTable,
    Column,
    Row,
  },
  data() {
    return {
      partyId: this.$route.params.id, // Use partyId directly from route
      partyData: null,
      loading: false,
      error: null,
    };
  },
  methods: {
    async fetchPartyData() {
      this.loading = true;
      this.error = null;

      try {
        // Fetch party data from the backend
        const response = await fetch(`${config.apiBaseUrl}/api/party-result/${this.partyId}`);
        if (!response.ok) {
          throw new Error("Failed to fetch party data");
        }

        const data = await response.json();
        console.log("Party Data:", data);
        // Use the data directly for rendering
        this.partyData = this.transformPartyData(data);
      } catch (err) {
        this.error = err.message;
      } finally {
        this.loading = false;
      }
    },

    transformPartyData(data) {
      // Directly process the data received from the backend
      const years = Object.keys(data);
      if (years.length === 0) return null;

      const partyData = data[years[0]];
      return {
        partyName: partyData.partyName,
        totalVotes: partyData.totalVotes,
        candidates: partyData.candidates || [],
        chartDataForCandidates: this.prepareChartData(partyData.candidates || []),
      };
    },

    prepareChartData(candidates) {
      if (!candidates || candidates.length === 0) return null;

      const topCandidates = candidates
          .sort((a, b) => b.validVotes - a.validVotes)
          .slice(0, 10);

      const chartLabels = topCandidates.map((candidate) => candidate.name);
      const chartData = topCandidates.map((candidate) => candidate.validVotes);

      return {
        labels: chartLabels,
        datasets: [
          {
            label: "Votes",
            data: chartData,
            backgroundColor: "#06b6d4",
          },
        ],
      };
    },
  },

  mounted() {
    this.fetchPartyData();
  },
};
</script>
<style scoped>
</style>

