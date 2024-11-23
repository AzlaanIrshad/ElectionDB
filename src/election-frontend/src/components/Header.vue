<template>
  <header class="bg-gray-800 dark:bg-gray-900 py-4 md:py-6 text-white shadow-lg relative">
    <div class="container mx-auto flex items-center justify-between px-4 md:px-8">
      <!-- Electiondb clickable on the far left -->
      <router-link
          to="/"
          class="logo text-3xl font-bold tracking-wide text-blue-400 hover:text-blue-300 transition duration-200 ease-in-out transform hover:scale-105"
      >
        Electiondb
      </router-link>

      <!-- Search Bar with Dropdown (Centered) -->
      <div class="relative w-full max-w-md flex-grow md:flex-grow-0 mx-4">
        <div class="relative">
          <input
              v-model="searchQuery"
              type="text"
              placeholder="Zoek..."
              @input="searchResults"
              class="w-full pr-10 pl-4 py-2 bg-gray-200 dark:bg-gray-700 text-gray-900 dark:text-gray-100 rounded-lg shadow-md focus:outline-none focus:ring-2 focus:ring-blue-500 transition duration-200 ease-in-out transform hover:scale-105"
          />
          <!-- Heroicon for Search (Magnifying Glass) on the right side -->
          <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              stroke-width="1.5"
              stroke="currentColor"
              class="absolute right-3 top-1/2 transform -translate-y-1/2 h-5 w-5 text-gray-500 dark:text-gray-300 transition duration-200 ease-in-out"
          >
            <path
                stroke-linecap="round"
                stroke-linejoin="round"
                d="m21 21-5.197-5.197m0 0A7.5 7.5 0 1 0 5.196 5.196a7.5 7.5 0 0 0 10.607 10.607Z"
            />
          </svg>
        </div>
        <ul
            v-if="results.length > 0"
            class="absolute top-full left-0 w-full bg-gray-700 rounded-lg mt-1 max-h-60 overflow-auto dark:bg-gray-800 transition duration-300 ease-in-out"
        >
          <li
              v-for="(result, index) in results"
              :key="result.id || index"
              @click="selectResult(result)"
              class="cursor-pointer px-4 py-2 hover:bg-gray-600 dark:hover:bg-gray-600 transition duration-200 ease-in-out"
          >
            <span v-if="result.partyName">Party: {{ result.partyName }}</span>
            <span v-else-if="result.candidateName">Candidate: {{ result.candidateName }}</span>
          </li>
        </ul>
      </div>

      <!-- Navigation links placed to the left after logo -->
      <div class="flex items-center space-x-4 md:space-x-6">
        <router-link
            to="/faq"
            class="text-lg text-white hover:text-gray-300 transition duration-200 ease-in-out px-4 py-2 flex items-center space-x-2 hover:scale-105"
        >
          FAQ
        </router-link>

        <router-link
            to="/about"
            class="text-lg text-white hover:text-gray-300 transition duration-200 ease-in-out px-4 py-2 flex items-center space-x-2 hover:scale-105"
        >
          Over Ons
        </router-link>

        <!-- Conditional Links for Authentication -->
        <template v-if="!isLoggedIn">
          <router-link
              to="/login"
              class="text-lg bg-blue-600 hover:bg-blue-500 rounded-full px-5 py-2 transition duration-300 ease-in-out flex items-center space-x-2 hover:scale-105"
          >
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="white" class="h-5 w-5 mr-2">
              <path stroke-linecap="round" stroke-linejoin="round" d="M8.25 9V5.25A2.25 2.25 0 0 1 10.5 3h6a2.25 2.25 0 0 1 2.25 2.25v13.5A2.25 2.25 0 0 1 16.5 21h-6a2.25 2.25 0 0 1-2.25-2.25V15M12 9l3 3m0 0-3 3m3-3H2.25" />
            </svg>

            Inloggen
          </router-link>
        </template>

        <template v-else>
          <router-link
              to="/threads"
              class="text-lg text-white hover:text-gray-300 transition duration-200 ease-in-out px-4 py-2 flex items-center space-x-2 hover:scale-105"
          >
            Discussies
          </router-link>

          <!-- Admin Panel Link -->
          <button
              v-if="isAdmin"
              @click="$router.push('/admin')"
              class="text-lg bg-blue-600 hover:bg-blue-500 rounded-full px-5 py-2 transition duration-300 ease-in-out flex items-center space-x-2 hover:scale-105"
          >
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="white" class="h-5 w-5 mr-2">
              <path stroke-linecap="round" stroke-linejoin="round" d="M16.5 10.5V6.75a4.5 4.5 0 1 0-9 0v3.75m-.75 11.25h10.5a2.25 2.25 0 0 0 2.25-2.25v-6.75a2.25 2.25 0 0 0-2.25-2.25H6.75a2.25 2.25 0 0 0-2.25 2.25v6.75a2.25 2.25 0 0 0 2.25 2.25Z" />
            </svg>

            Admin Paneel
          </button>

          <!-- Logout Button with SVG Icon -->
          <button
              @click="logout"
              class="text-lg bg-red-600 hover:bg-red-500 rounded-full px-5 py-2 transition duration-300 ease-in-out flex items-center space-x-2 hover:scale-105"
          >
            <!-- SVG Icon -->
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" class="h-5 w-5 mr-2" fill="white" stroke="white">
              <path d="M377.9 105.9L500.7 228.7c7.2 7.2 11.3 17.1 11.3 27.3s-4.1 20.1-11.3 27.3L377.9 406.1c-6.4 6.4-15 9.9-24 9.9c-18.7 0-33.9-15.2-33.9-33.9l0-62.1-128 0c-17.7 0-32-14.3-32-32l0-64c0-17.7 14.3-32 32-32l128 0 0-62.1c0-18.7 15.2-33.9 33.9-33.9c9 0 17.6 3.6 24 9.9zM160 96L96 96c-17.7 0-32 14.3-32 32l0 256c0 17.7 14.3 32 32 32l64 0c17.7 0 32 14.3 32 32s-14.3 32-32 32l-64 0c-53 0-96-43-96-96L0 128C0 75 43 32 96 32l64 0c17.7 0 32 14.3 32 32s-14.3 32-32 32z"/>
            </svg>

            Uitloggen
          </button>
        </template>
      </div>

      <!-- Dark Mode Toggle positioned on the absolute right -->
      <div class="absolute right-8 top-1/2 transform -translate-y-1/2 transition duration-200 ease-in-out">
        <DarkModeToggle />
      </div>
    </div>
  </header>
</template>

<script>
import DarkModeToggle from "./DarkModeToggle.vue";

export default {
  name: "Header",
  components: {
    DarkModeToggle,
  },
  data() {
    return {
      isLoggedIn: false,
      isAdmin: false,
      searchQuery: "",
      results: [],
      electionData: [],
    };
  },
  mounted() {
    this.checkAuthStatus();
    this.fetchElectionResults();
  },
  methods: {
    async fetchElectionResults() {
      try {
        const response = await fetch("http://localhost:8080/api/election-results");
        if (!response.ok) throw new Error("Failed to fetch election results");
        const data = await response.json();
        this.electionData = data;
      } catch (err) {
        console.error("Error fetching election results:", err);
      }
    },
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
        this.$router.push({ name: "single-party", params: { id: result.id } });
      } else if (result.candidateName) {
        this.$router.push({ name: "search-results", params: { query: result.candidateId } });
      }
      this.searchQuery = '';
      this.results = [];

      setTimeout(() => {
        window.location.reload();
      }, 300);
    },
    checkAuthStatus() {
      const token = localStorage.getItem("token");
      if (token) {
        try {
          const payload = JSON.parse(atob(token.split(".")[1]));
          this.isLoggedIn = true;
          this.isAdmin = payload.role === "ADMIN";
        } catch (error) {
          console.error("Error decoding token payload:", error);
        }
      } else {
        this.isLoggedIn = false;
        this.isAdmin = false;
      }
    },
    logout() {
      localStorage.removeItem("token");
      this.isLoggedIn = false;
      this.isAdmin = false;
      this.$router.push("/login");
    },
  },
  watch: {
    "$route"() {
      this.checkAuthStatus();
    },
  },
};
</script>

<style scoped>
</style>
