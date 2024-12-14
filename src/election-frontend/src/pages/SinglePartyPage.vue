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
        // Fetch election results
        const response = await fetch(`${config.apiBaseUrl}/api/election-results`);
        if (!response.ok) {
          throw new Error("Failed to fetch election results");
        }

        // Fetch candidates data
        const candidateResponse = await fetch(`${config.apiBaseUrl}/api/candidates`);
        if (!candidateResponse.ok) {
          throw new Error("Failed to fetch candidates");
        }

        const data = await response.json();
        const candidateData = await candidateResponse.json();
        console.log("Candidate Data:", candidateData);
        const party = this.findPartyById(data, partyId, candidateData);
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

    findPartyById(data, partyId, candidateData) {
      let party = null;
      const candidateVotesMap = {};

      for (const transaction of data) {
        const contests = transaction?.count?.election?.contests?.contests || [];
        contests.forEach((contest) => {
          let currentPartyId = null;

          contest.totalVotes.selections.forEach((selection) => {
            if (selection.affiliationIdentifier?.id) {
              currentPartyId = selection.affiliationIdentifier.id;

              if (currentPartyId === partyId) {
                if (!party) {
                  party = {
                    partyId,
                    partyName: selection.affiliationIdentifier.registeredName || "Unknown Party",
                    totalVotes: 0,
                    candidates: [],
                  };
                }
                party.totalVotes += selection.validVotes || 0;
              }
            }

            if (currentPartyId === partyId && selection.candidate?.candidateIdentifier?.id) {
              const candidateId = selection.candidate.candidateIdentifier.id;
              const validVotes = selection.validVotes || 0;

              if (candidateVotesMap[candidateId]) {
                candidateVotesMap[candidateId].validVotes += validVotes;
              } else {
                candidateVotesMap[candidateId] = {
                  id: candidateId,
                  validVotes,
                  name: `Candidate ${candidateId}`, // Default name for fallback
                };
              }
            }
          });
        });
      }

      if (party) {
        console.log("a", candidateData);
        for (const biglist of candidateData) {
          console.log("b", biglist.candidateList);
            const affiliation = biglist?.candidateList?.election?.contests[0]?.affiliations?.find(
                (aff) => aff.affiliationIdentifier?.id === partyId
            );
            console.log(affiliation);
          if (affiliation) {
            affiliation.candidates.forEach((candidate) => {
              const candidateId = candidate.candidateIdentifier.id;
              if (candidateVotesMap[candidateId]) {
                const firstName = candidate.candidateFullName.personName.firstName;
                const lastName = candidate.candidateFullName.personName.lastName;
                candidateVotesMap[candidateId].name = `${firstName} ${lastName}`;
              }
            });
           }
          }
        party.candidates = Object.values(candidateVotesMap);
      }

      console.log("Candidate Votes Map:", candidateVotesMap);
      console.log("Accumulated Party Data:", party);

      return party;
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
