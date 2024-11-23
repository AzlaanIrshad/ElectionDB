<template>
  <header class="bg-gray-800 dark:bg-gray-900 py-4 shadow-lg relative">
    <div class="flex flex-col md:flex-row items-center justify-between px-4 md:px-6 space-y-4 md:space-y-0">
      <router-link
          to="/"
          class="max-lg:mr-2 text-2xl md:text-3xl font-bold tracking-wide text-blue-400 hover:text-blue-300 transition duration-200 ease-in-out transform hover:scale-105"
      >
        Electiondb
      </router-link>

      <!-- Center: Search bar and Navigation -->
      <div class="flex-1 flex flex-col md:flex-row items-center justify-center space-y-4 md:space-y-0 md:space-x-6 mx-2">
        <!-- Mobile Hamburger Menu SVG -->
        <div class="lg:hidden flex items-center space-x-2 z-20">
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="h-6 w-6 text-white cursor-pointer" @click="toggleMobileMenu">
            <path stroke-linecap="round" stroke-linejoin="round" d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5" />
          </svg>
        </div>

        <!-- Search Bar Component -->
        <SearchBar :electionData="electionData" />

        <!-- Navigation Links (Desktop Only) -->
        <div class="hidden lg:flex flex-wrap justify-center items-center space-x-6 pl-3">
          <router-link
              to="/faq"
              class="text-sm md:text-base text-white hover:text-gray-300 transition duration-200 ease-in-out"
          >
            FAQ
          </router-link>
          <router-link
              to="/about"
              class="text-sm md:text-base text-white hover:text-gray-300 transition duration-200 ease-in-out"
          >
            Over Ons
          </router-link>
          <router-link
              to="/threads"
              class="text-sm md:text-base text-white hover:text-gray-300 transition duration-200 ease-in-out"
          >
            Discussies
          </router-link>
        </div>
      </div>

      <!-- Right: Actions -->
      <div class="flex flex-wrap justify-center items-center space-x-2">
        <template v-if="!isLoggedIn">
          <router-link
              to="/login"
              class="text-sm bg-blue-600 hover:bg-blue-500 rounded-full px-3 md:px-4 py-1.5 transition duration-300 ease-in-out flex items-center"
          >
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="white" class="h-5 w-5 mr-2">
              <path stroke-linecap="round" stroke-linejoin="round" d="M8.25 9V5.25A2.25 2.25 0 0 1 10.5 3h6a2.25 2.25 0 0 1 2.25 2.25v13.5A2.25 2.25 0 0 1 16.5 21h-6a2.25 2.25 0 0 1-2.25-2.25V15M12 9l3 3m0 0-3 3m3-3H2.25" />
            </svg>
            Inloggen
          </router-link>
        </template>

        <template v-else>
          <button
              v-if="isAdmin"
              @click="$router.push('/admin')"
              class="text-sm text-white bg-blue-600 hover:bg-blue-500 rounded-full px-3 md:px-4 py-1.5 transition duration-300 ease-in-out flex items-center"
          >
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="white" class="h-5 w-5 mr-2">
              <path stroke-linecap="round" stroke-linejoin="round" d="M16.5 10.5V6.75a4.5 4.5 0 1 0-9 0v3.75m-.75 11.25h10.5a2.25 2.25 0 0 0 2.25-2.25v-6.75a2.25 2.25 0 0 0-2.25-2.25H6.75a2.25 2.25 0 0 0-2.25 2.25v6.75a2.25 2.25 0 0 0 2.25 2.25Z" />
            </svg>
            Admin Paneel
          </button>
          <button
              @click="logout"
              class="text-sm text-white bg-red-600 hover:bg-red-500 rounded-full px-3 md:px-4 py-1.5 transition duration-300 ease-in-out flex items-center"
          >
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6 mr-1">
              <path stroke-linecap="round" stroke-linejoin="round" d="M8.25 9V5.25A2.25 2.25 0 0 1 10.5 3h6a2.25 2.25 0 0 1 2.25 2.25v13.5A2.25 2.25 0 0 1 16.5 21h-6a2.25 2.25 0 0 1-2.25-2.25V15M12 9l3 3m0 0-3 3m3-3H2.25" />
            </svg>


            Uitloggen
          </button>
        </template>

        <DarkModeToggle />
      </div>
    </div>

    <!-- Mobile Menu (Conditional) -->
    <div v-if="isMobileMenuOpen" class="lg:hidden bg-gray-800 dark:bg-gray-900 absolute top-0 left-0 w-full h-full flex flex-col items-center justify-center space-y-4 pt-16 z-30">
      <!-- Close Button (X SVG) -->
      <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="h-6 w-6 text-white absolute top-4 right-4 cursor-pointer" @click="toggleMobileMenu">
        <path stroke-linecap="round" stroke-linejoin="round" d="M6 18L18 6M6 6l12 12" />
      </svg>

      <router-link
          to="/faq"
          class="text-base text-white hover:text-gray-300 transition duration-200 ease-in-out"
      >
        FAQ
      </router-link>
      <router-link
          to="/about"
          class="text-base text-white hover:text-gray-300 transition duration-200 ease-in-out"
      >
        Over Ons
      </router-link>
      <router-link
          to="/threads"
          class="text-base text-white hover:text-gray-300 transition duration-200 ease-in-out"
      >
        Discussies
      </router-link>

      <!-- Mobile Auth Links -->
      <template v-if="!isLoggedIn">
        <router-link
            to="/login"
            class="text-base text-white bg-blue-600 hover:bg-blue-500 rounded-full px-3 py-1.5 transition duration-300 ease-in-out"
        >
          Inloggen
        </router-link>
      </template>

      <template v-else>
        <button
            v-if="isAdmin"
            @click="$router.push('/admin')"
            class="text-base text-white bg-blue-600 hover:bg-blue-500 rounded-full px-3 py-1.5 transition duration-300 ease-in-out"
        >
          Admin Paneel
        </button>
        <button
            @click="logout"
            class="text-base text-white bg-red-600 hover:bg-red-500 rounded-full px-3 py-1.5 transition duration-300 ease-in-out"
        >
          Uitloggen
        </button>
      </template>
    </div>
  </header>
</template>

<script>
import DarkModeToggle from "./DarkModeToggle.vue";
import SearchBar from "./SearchBar.vue";

export default {
  name: "Header",
  components: {
    DarkModeToggle,
    SearchBar,
  },
  data() {
    return {
      isLoggedIn: false,
      isAdmin: false,
      electionData: [],
      isMobileMenuOpen: false,
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
    toggleMobileMenu() {
      this.isMobileMenuOpen = !this.isMobileMenuOpen;
    },
  },
};
</script>
