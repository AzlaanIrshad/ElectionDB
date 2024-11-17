<template>
  <div class="dashboard-container max-w-6xl mx-auto py-12 px-4">
    <h1 class="text-3xl font-bold text-center mb-6 text-gray-900 dark:text-gray-100">
      Dashboard Voting Results
    </h1>
    <p class="text-center text-gray-600 dark:text-gray-300 mb-6">
      Election Results by City
    </p>

    <!-- Loading Spinner -->
    <div v-if="loading" class="text-center">
      <p>Loading...</p>
    </div>

    <!-- Error Message -->
    <div v-if="error" class="text-center text-red-500">
      <p>{{ error }}</p>
    </div>

    <!-- Election Results by City -->
    <div v-if="groupedResults.length" class="results-container">
      <div v-for="city in groupedResults" :key="city.cityId" class="city-item mb-6">
        <details class="group border rounded-md bg-gray-100 dark:bg-gray-800 p-4">
          <summary class="cursor-pointer text-lg font-semibold text-gray-900 dark:text-gray-100">
            City: {{ city.cityName }} (Total Votes: {{ city.totalVotes }})
          </summary>
          <div class="parties mt-4">
            <h3 class="font-medium text-gray-800 dark:text-gray-300 mb-2">Parties:</h3>
            <ul>
              <li v-for="party in city.parties" :key="party.partyId" class="mb-4">
                <details class="group">
                  <summary class="cursor-pointer text-gray-800 dark:text-gray-300">
                    Party: {{ party.partyName }} (Votes: {{ party.totalVotes }})
                  </summary>
                  <ul class="candidates pl-4 mt-2">
                    <li
                        v-for="candidate in party.candidates"
                        :key="candidate.id"
                        class="text-sm text-gray-700 dark:text-gray-400"
                    >
                      Candidate {{ candidate.id }}: {{ candidate.validVotes }} votes
                    </li>
                  </ul>
                </details>
              </li>
            </ul>
          </div>
        </details>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      groupedResults: [],
      loading: false,
      error: null,
    };
  },
  methods: {
    /**
     * Fetches the election results from the backend API.
     * Sets the grouped results and handles loading/error states.
     */
    async fetchElectionResults() {
      this.loading = true;
      this.error = null;
      try {
        const response = await fetch("http://localhost:8080/api/election-results");
        if (!response.ok) {
          throw new Error("Failed to fetch election results");
        }
        const data = await response.json();
        this.groupedResults = this.transformData(data);
      } catch (err) {
        this.error = err.message;
      } finally {
        this.loading = false;
      }
    },

    /**
     * Transforms raw election data into a structured format.
     * Groups results by city and organizes parties with their candidates.
     *
     * @param {Array} data - The raw election data from the API.
     * @returns {Array} Transformed data grouped by city.
     */
    transformData(data) {
      return data.map((transaction) => {
        const cityName = transaction.managingAuthority.authorityIdentifier.value;
        const cityId = transaction.managingAuthority.authorityIdentifier.id;

        const partiesMap = {};
        let currentPartyId = null;

        transaction.count.election.contests.contests.forEach((contest) => {
          const contestId = contest.contestIdentifier.id;
          const contestName = contest.contestName;

          contest.totalVotes.selections.forEach((selection) => {
            if (selection.affiliationIdentifier) {
              currentPartyId = selection.affiliationIdentifier.id;
              const partyName =
                  selection.affiliationIdentifier.registeredName || contestName || "Unknown Party";

              if (!partiesMap[currentPartyId]) {
                partiesMap[currentPartyId] = {
                  partyId: currentPartyId,
                  partyName,
                  totalVotes: selection.validVotes || 0,
                  candidates: [],
                };
              }
            } else if (
                currentPartyId &&
                selection.candidate?.candidateIdentifier?.id
            ) {
              partiesMap[currentPartyId].candidates.push({
                id: selection.candidate.candidateIdentifier.id,
                validVotes: selection.validVotes || 0,
              });
            }
          });
        });

        const parties = Object.values(partiesMap);

        const totalVotes = parties.reduce((sum, party) => sum + party.totalVotes, 0);

        return {
          cityId,
          cityName,
          totalVotes,
          parties,
        };
      });
    },
  },
  mounted() {
    this.fetchElectionResults();
  },
};
</script>