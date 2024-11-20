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
            placeholder="Search election results..."
            @input="searchResults"
            class="w-full px-4 py-2 bg-gray-200 dark:bg-gray-700 text-gray-900 dark:text-gray-100 rounded-lg shadow-md focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
        <ul v-if="results.length > 0" class="absolute top-full left-0 w-full bg-gray-700 rounded-lg mt-1 max-h-60 overflow-auto dark:bg-gray-800">
          <li
              v-for="result in results"
              :key="result.name"
              @click="selectResult(result)"
              class="cursor-pointer px-4 py-2 hover:bg-gray-600 dark:hover:bg-gray-600"
          >
            {{ result.name }}
          </li>
        </ul>
      </div>

      <!-- Navigation and Actions -->
      <div class="flex items-center space-x-4 md:space-x-6">
        <DarkModeToggle />

        <router-link
            to="/faq"
            class="text-lg text-white hover:text-gray-300 transition duration-200 px-4 py-2 flex items-center space-x-2"
        >
          <span>FAQ</span>
        </router-link>

        <router-link
            to="/about"
            class="text-lg text-white hover:text-gray-300 transition duration-200 px-4 py-2 flex items-center space-x-2"
        >
          <span>About Us</span>
        </router-link>

        <template v-if="!isLoggedIn">
          <router-link
              to="/login"
              class="text-lg bg-blue-600 hover:bg-blue-500 rounded-full px-5 py-2 transition duration-300 flex items-center space-x-2"
          >
            <span>Login</span>
          </router-link>
        </template>

        <template v-else>
          <router-link
              to="/threads"
              class="text-lg text-white hover:text-gray-300 transition duration-200 px-4 py-2 flex items-center space-x-2"
          >
            <span>Threads</span>
          </router-link>

          <button
              v-if="isAdmin"
              @click="$router.push('/admin')"
              class="text-lg bg-blue-600 hover:bg-blue-500 rounded-full px-5 py-2 transition duration-300 flex items-center space-x-2"
          >
            <span>Admin Panel</span>
          </button>

          <button
              @click="logout"
              class="text-lg bg-red-600 hover:bg-red-500 rounded-full px-5 py-2 transition duration-300 flex items-center space-x-2"
          >
            <span>Logout</span>
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
    };
  },
  mounted() {
    this.checkAuthStatus();
  },
  methods: {
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
    async searchResults() {
      if (this.searchQuery.length > 1) {
        try {
          const response = await fetch("http://localhost:8080/api/election-results");
          const data = await response.json();
          this.results = data.filter((result) =>
              result.name.toLowerCase().includes(this.searchQuery.toLowerCase())
          );
        } catch (error) {
          console.error("Error fetching election results:", error);
        }
      } else {
        this.results = [];
      }
    },
    selectResult(result) {
      this.$router.push({ name: "search-results", params: { query: result.name } });
    },
  },
  watch: {
    "$route"() {
      this.checkAuthStatus();
    },
  },
};
</script>
