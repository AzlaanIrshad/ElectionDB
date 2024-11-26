<template>
  <div class="relative w-full max-w-xs sm:max-w-sm lg:max-w-md">
    <input
        v-model="searchQuery"
        type="text"
        placeholder="Zoek..."
        @input="searchResults"
        class="w-full pr-8 pl-3 py-2 bg-gray-200 dark:bg-gray-700 text-gray-900 dark:text-gray-100 rounded-lg shadow-md focus:outline-none focus:ring-2 focus:ring-blue-500 transition duration-200 ease-in-out"
    />
    <svg
        xmlns="http://www.w3.org/2000/svg"
        fill="none"
        viewBox="0 0 24 24"
        stroke-width="1.5"
        stroke="currentColor"
        class="absolute right-3 top-1/2 transform -translate-y-1/2 h-5 w-5 text-gray-500 dark:text-gray-300"
    >
      <path
          stroke-linecap="round"
          stroke-linejoin="round"
          d="m21 21-5.197-5.197m0 0A7.5 7.5 0 1 0 5.196 5.196a7.5 7.5 0 0 0 10.607 10.607Z"
      />
    </svg>

    <!-- Search Results Dropdown -->
    <ul
        v-if="results.length > 0"
        class="absolute top-full left-0 w-full bg-gray-700 rounded-lg mt-1 max-h-60 overflow-auto dark:bg-gray-800 shadow-md transition duration-300 ease-in-out z-50"
    >
      <li
          v-for="(result, index) in results"
          :key="result.id || index"
          @click="selectResult(result)"
          class="cursor-pointer text-white px-4 py-2 hover:bg-gray-600 dark:hover:bg-gray-600 transition duration-200 ease-in-out"
      >
        <span v-if="result.partyName">Partij: {{ result.partyName }}</span>
        <span v-else-if="result.candidateName">Kandidaat: {{ result.candidateName }}</span>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  props: {
    electionData: Array,
  },
  data() {
    return {
      searchQuery: "",
      results: [],
    };
  },
  methods: {
    searchResults() {
      if (this.searchQuery.trim() !== "") {
        const uniqueIds = new Set();

        this.results = this.electionData.flatMap((transaction) =>
            transaction.count.election.contests.contests.flatMap((contest) =>
                contest.totalVotes.selections
                    .map((selection) => {
                      const partyId = selection.affiliationIdentifier?.id || null;
                      const partyName = selection.affiliationIdentifier?.registeredName || null;
                      const candidateId = selection.candidate?.candidateIdentifier?.id || null;
                      const candidateName = selection.candidate?.name || null;

                      if (
                          (partyName?.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
                              candidateName?.toLowerCase().includes(this.searchQuery.toLowerCase()))
                      ) {
                        const id = partyId || candidateId;
                        if (!uniqueIds.has(id)) {
                          uniqueIds.add(id);
                          return { id, partyName, candidateId, candidateName };
                        }
                      }
                      return null;
                    })
                    .filter((result) => result !== null)
            )
        );
      } else {
        this.results = [];
      }
    },
    selectResult(result) {
      if (result.partyName) {
        this.$router.push({ name: "single-party", params: { id: result.id }, query: { search: this.searchQuery } });
      } else if (result.candidateName) {
        this.$router.push({ name: "search-results", params: { query: result.candidateId }, query: { search: this.searchQuery } });
      }
      this.searchQuery = "";
      this.results = [];
    },
  },
};
</script>
