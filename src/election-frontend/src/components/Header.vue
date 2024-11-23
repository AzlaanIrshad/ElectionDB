<template>
  <header class="bg-gray-800 dark:bg-gray-900 py-4 md:py-6 text-white shadow-lg">
    <div class="container mx-auto flex flex-wrap md:flex-nowrap items-center justify-between px-4 md:px-8">
      <!-- Logo -->
      <router-link to="/" class="logo text-3xl font-bold tracking-wide text-blue-400 hover:text-blue-300 transition duration-200">
        Electiondb
      </router-link>

      <!-- Search Bar with Dropdown -->
      <div class="relative w-full max-w-md flex-grow md:flex-grow-0 justify-center mt-4 md:mt-0 mx-4 md:mx-8">
        <input
            v-model="searchQuery"
            type="text"
            placeholder="Zoek naar verkiezingsresultaten..."
            @input="searchResults"
            class="w-full px-4 py-2 bg-gray-200 dark:bg-gray-700 text-gray-900 dark:text-gray-100 rounded-lg shadow-md focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
        <ul v-if="results.length > 0" class="absolute top-full left-0 w-full bg-gray-700 rounded-lg mt-1 max-h-60 overflow-auto dark:bg-gray-800">
          <li
              v-for="(result, index) in results"
              :key="result.id || index"
              @click="selectResult(result)"
              class="cursor-pointer px-4 py-2 hover:bg-gray-600 dark:hover:bg-gray-600"
          >
            <span v-if="result.partyName">
              Partij: {{ result.partyName }}
            </span>
            <span v-else-if="result.candidateName">
              Kandidaat: {{ result.candidateName }}
            </span>
          </li>
        </ul>
      </div>

      <div class="flex items-center space-x-4 md:space-x-6">

        <DarkModeToggle />

        <!-- Links voor alle gebruikers -->
        <router-link
            to="/faq"
            class="text-lg text-white hover:text-gray-300 transition duration-200 px-4 py-2 flex items-center space-x-2"
        >
          <span>FAQ</span>
        </router-link>

        <!-- Over Link (Zichtbaar voor zowel ingelogde als niet-ingelogde gebruikers) -->
        <router-link
            to="/about"
            class="text-lg text-white hover:text-gray-300 transition duration-200 px-4 py-2 flex items-center space-x-2"
        >
          <span>Over Ons</span>
        </router-link>

        <!-- Conditional Links for Authentication -->
        <template v-if="!isLoggedIn">
          <router-link
              to="/login"
              class="text-lg bg-blue-600 hover:bg-blue-500 rounded-full px-5 py-2 transition duration-300 flex items-center space-x-2"
          >
            <span>Inloggen</span>
          </router-link>
        </template>

        <template v-else>
          <router-link
              to="/threads"
              class="text-lg text-white hover:text-gray-300 transition duration-200 px-4 py-2 flex items-center space-x-2"
          >
            <span>Discussies</span>
          </router-link>

          <!-- Admin Panel Link -->
          <button
              v-if="isAdmin"
              @click="$router.push('/admin')"
              class="text-lg bg-blue-600 hover:bg-blue-500 rounded-full px-5 py-2 transition duration-300 flex items-center space-x-2"
          >
            <span>Admin Paneel</span>
          </button>

          <!-- Logout Button -->
          <button
              @click="logout"
              class="text-lg bg-red-600 hover:bg-red-500 rounded-full px-5 py-2 transition duration-300 flex items-center space-x-2"
          >
            <span>Uitloggen</span>
          </button>
        </template>
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
        if (!response.ok) throw new Error("Fout bij ophalen van verkiezingsresultaten");
        const data = await response.json();
        this.electionData = data;
      } catch (err) {
        console.error("Fout bij ophalen van verkiezingsresultaten:", err);
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
          console.error("Fout bij decoderen van token payload:", error);
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
