<template>
  <div class="single-party-page">
    <h1>Party Details</h1>

    <!-- Loading State -->
    <div v-if="loading" class="loading">
      <p>Loading party data...</p>
    </div>

    <!-- Error State -->
    <div v-if="error" class="error">
      <p>{{ error }}</p>
    </div>

    <!-- Party Data -->
    <div v-if="partyData && !loading" class="party-data">
      <h2>{{ partyData.partyName }}</h2>
      <p>Total Votes: {{ partyData.totalVotes }}</p>

      <!-- Display Candidate Chart -->
      <div v-if="partyData.chartDataForCandidates">
        <h3>Top Candidates</h3>
        <PartyCandidateHorizontalBarChart
            :chart-data="partyData.chartDataForCandidates"
        />
      </div>

      <!-- No Candidates -->
      <div v-else>
        <p>No candidates found for this party.</p>
      </div>
    </div>
  </div>
</template>

<script>
import config from "../config";
import PartyCandidateHorizontalBarChart from "../components/CandidateHorizontalBarChart.vue";

export default {
  name: "SinglePartyPage",
  components: {
    PartyCandidateHorizontalBarChart,
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
.single-party-page {
  padding: 20px;
}

.loading,
.error {
  color: red;
  font-size: 18px;
  margin: 10px 0;
}

.party-data h2 {
  color: #333;
}

.party-data h3 {
  margin-top: 20px;
  color: #555;
}
</style>
