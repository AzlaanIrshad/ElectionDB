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
          :key="result.partyName || index"
          @click="selectResult(result)"
          class="cursor-pointer text-white px-4 py-2 hover:bg-gray-600 dark:hover:bg-gray-600 transition duration-200 ease-in-out"
      >
        <span>Partij: {{ result.partyName }}</span>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  props: {
    electionData: {
      type: Object, // Accepts object
      required: true,
    },
  },
  data() {
    return {
      searchQuery: "",
      results: [],
    };
  },
  methods: {
    searchResults() {
      console.log("Election Data:", this.electionData); // Debug log

      // Check if electionData is valid and has the expected structure
      if (!this.electionData || typeof this.electionData !== "object") {
        console.error("electionData is undefined or not an object.");
        return;
      }

      // Get the actual data inside the year key (e.g., 2023)
      const yearData = this.electionData[Object.keys(this.electionData)[0]]; // This assumes only one year key is present

      if (!yearData || typeof yearData !== "object") {
        console.error("Year data is not valid.");
        return;
      }

      // If the search query is not empty
      if (this.searchQuery.trim() !== "") {
        const query = this.searchQuery.toLowerCase();
        const allParties = Object.keys(yearData);

        console.log("All Parties:", allParties); // Debug log

        // Filter parties based on the search query and map to desired format
        this.results = allParties
            .filter((partyName) => partyName.toLowerCase().includes(query))
            .map((partyName) => ({
              partyName,
              votes: yearData[partyName], // Get vote count
            }));

        console.log("Filtered Results:", this.results); // Debug log
      } else {
        // Clear results when the search query is empty
        this.results = [];
      }
    },


    selectResult(result) {
      this.$router.push({
        name: "single-party",
        params: { id: result.partyName },
        query: { search: this.searchQuery },
      });
      this.searchQuery = "";
      this.results = [];
    },
  },
};
</script>
