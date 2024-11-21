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

    <!-- Verkiezingsresultaten per Stad -->
    <div v-if="filteredResults.length" class="results-container">
      <div v-for="city in filteredResults" :key="city.cityId" class="city-item border-b border-gray-300 py-4 dark:border-gray-600">
        <details class="group">
          <summary class="cursor-pointer font-semibold text-lg text-gray-800 dark:text-gray-100 group-open:mb-2">
            Stad: {{ city.cityName }} (Totaal Stemmen: {{ city.totalVotes }})
          </summary>

          <!-- Chart -->
          <TotalPartyVoteBarChart :chartData="city.chartData" />
          <div class="parties mt-4">
            <ul>
              <li v-for="party in city.parties" :key="party.partyId" class="mb-4">
                <details class="group">
                  <summary class="cursor-pointer text-gray-800 dark:text-gray-300">
                    Partij: {{ party.partyName }} (Stemmen: {{ party.totalVotes }})
                  </summary>
                  <ul class="kandidaten pl-4 mt-2">
                    <li
                        v-for="candidate in party.candidates"
                        :key="candidate.id"
                        class="text-sm text-gray-700 dark:text-gray-400"
                    >
                      Kandidaat {{ candidate.id }}: {{ candidate.validVotes }} stemmen
                    </li>
                  </ul>
                </details>
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

export default {
  name: "ElectionDashboard",
  components: {
    TotalPartyVoteBarChart,
  },
  data() {
    return {
      searchQuery: '',
      groupedResults: [],
      loading: false,
      error: null,
    };
  },
  computed: {
    filteredResults() {
      return this.groupedResults.filter(city =>
          city.cityName.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    },
  },
  methods: {
    async fetchElectionResults() {
      this.loading = true;
      this.error = null;
      try {
        const baseURL = window.location.hostname.includes("localhost")
            ? "http://localhost:8080"
            : "http://oege.ie.hva.nl:8000";

        const response = await fetch(`${baseURL}/api/election-results`);
        if (!response.ok) {
          throw new Error("Ophalen van verkiezingsresultaten mislukt");
        }
        const data = await response.json();
        this.groupedResults = this.transformData(data);
      } catch (err) {
        this.error = err.message;
      } finally {
        this.loading = false;
      }
    },

    transformData(data) {
      return data.map((transaction) => {
        const cityName = transaction.managingAuthority.authorityIdentifier.value;
        const cityId = transaction.managingAuthority.authorityIdentifier.id;

        const partiesMap = {};
        let currentPartyId = null;

        transaction.count.election.contests.contests.forEach((contest) => {
          contest.totalVotes.selections.forEach((selection) => {
            if (selection.affiliationIdentifier) {
              currentPartyId = selection.affiliationIdentifier.id;
              const partyName = selection.affiliationIdentifier.registeredName || "Unknown Party";
              if (!partiesMap[currentPartyId]) {
                partiesMap[currentPartyId] = {
                  partyId: currentPartyId,
                  partyName,
                  totalVotes: selection.validVotes || 0,
                  candidates: [],
                };
              }
            } else if (currentPartyId && selection.candidate?.candidateIdentifier?.id) {
              partiesMap[currentPartyId].candidates.push({
                id: selection.candidate.candidateIdentifier.id,
                validVotes: selection.validVotes || 0,
              });
            }
          });
        });

        const parties = Object.values(partiesMap);
        const totalVotes = parties.reduce((sum, party) => sum + party.totalVotes, 0);

        const chartData = this.prepareChartData(parties);

        return {
          cityId,
          cityName,
          totalVotes,
          parties,
          chartData,
        };
      });
    },

    prepareChartData(parties) {
      return {
        labels: parties.map(party => party.partyName),
        datasets: [{
          label: 'Totaal Stemmen',
          data: parties.map(party => party.totalVotes),
          backgroundColor: parties.map(() => 'rgba(87,192,75,0.8)'),
          borderColor: parties.map(() => 'rgba(0,0,0,1)'),
          borderWidth: 1,
        }],
      };
    },
  },
  mounted() {
    this.fetchElectionResults();
  },
};
</script>
