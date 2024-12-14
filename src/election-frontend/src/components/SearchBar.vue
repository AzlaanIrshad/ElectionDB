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
          :key="result.partyId || index"
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
  data() {
    return {
      searchQuery: "",
      results: [],
    };
  },
  methods: {
    async searchResults() {
      if (this.searchQuery.trim() !== "") {
        try {
          const response = await fetch(
              `http://localhost:8080/api/search/2023?query=${encodeURIComponent(this.searchQuery)}`
          );

          if (!response.ok) {
            console.error("Failed to fetch results:", response.statusText);
            return;
          }
          this.results = await response.json();
          console.log("Filtered Results:", this.results);
        } catch (error) {
          console.error("Error fetching search results:", error);
        }
      } else {
        this.results = [];
      }
    },

    selectResult(result) {
      this.$router.push({
        name: "single-party",
        params: { id: result.partyId },
        query: { search: this.searchQuery },
      });
      this.searchQuery = "";
      this.results = [];
    },
  },
};
</script>
