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
import PartyCandidateHorizontalBarChart from "../components/CandidateHorizontalBarChart.vue";

export default {
  name: "SinglePartyPage",
  components: {
    PartyCandidateHorizontalBarChart,
  },
  data() {
    return {
      partyName: "",
      partyData: null,
      loading: false,
      error: null,
    };
  },
  methods: {
    async fetchPartyData() {
      const partyId = this.$route.params.id;
      this.loading = true;
      this.error = null;

      try {
        const response = await fetch(
            "http://localhost:8080/api/election-results"
        );
        if (!response.ok) {
          throw new Error("Failed to fetch election results");
        }

        const data = await response.json();
        const party = this.findPartyById(data, partyId);
        if (party) {
          this.partyData = this.transformPartyData(party);
        } else {
          throw new Error("Party not found");
        }
      } catch (err) {
        this.error = err.message;
      } finally {
        this.loading = false;
      }
    },
    findPartyById(data, partyId) {
      for (const transaction of data) {
        for (const contest of transaction.count.election.contests.contests) {
          for (const selection of contest.totalVotes.selections) {
            if (selection.affiliationIdentifier) {
              const currentPartyId = selection.affiliationIdentifier.id;
              const partyName =
                  selection.affiliationIdentifier.registeredName || "Unknown Party";

              if (currentPartyId === partyId) {
                return {
                  partyName,
                  totalVotes: selection.validVotes || 0,
                  candidates: [],
                };
              }
            }
          }
        }
      }
      return null;
    },
    transformPartyData(party) {
      return {
        partyName: party.partyName,
        totalVotes: party.totalVotes,
        candidates: party.candidates || [],
        chartDataForCandidates: this.prepareChartData(party.candidates || []),
      };
    },
    prepareChartData(candidates) {
      const topCandidates = candidates
          .sort((a, b) => b.validVotes - a.validVotes)
          .slice(0, 10);

      const chartLabels = topCandidates.map(
          (candidate) => `Candidate ${candidate.id}`
      );
      const chartData = topCandidates.map((candidate) => candidate.validVotes);

      return {
        labels: chartLabels,
        datasets: [
          {
            label: "Votes",
            data: chartData,
            backgroundColor: "rgba(87,192,75,0.8)",
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
