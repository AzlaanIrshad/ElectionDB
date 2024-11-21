<template>
  <header class="bg-gray-800 dark:bg-gray-900 py-4 md:py-6 text-white shadow-lg">
    <div class="container mx-auto flex justify-between items-center px-4 md:px-8">
      <!-- Logo -->
      <router-link to="/" class="logo text-3xl font-bold tracking-wide text-blue-400 hover:text-blue-300 transition duration-200">
        VerkiezingsDB
      </router-link>

      <div class="flex items-center space-x-4 md:space-x-6">
        <!-- Donkere Modus Toggle -->
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

        <!-- Voorwaardelijke Links voor Authenticatie -->
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

          <!-- Admin Paneel Link -->
          <button
              v-if="isAdmin"
              @click="$router.push('/admin')"
              class="text-lg bg-blue-600 hover:bg-blue-500 rounded-full px-5 py-2 transition duration-300 flex items-center space-x-2"
          >
            <span>Admin Paneel</span>
          </button>

          <!-- Uitloggen Knop -->
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
  name: "HeaderComponent",
  components: {
    DarkModeToggle,
  },
  data() {
    return {
      isLoggedIn: false,
      isAdmin: false,
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
          console.error("Fout bij het decoderen van de tokenpayload:", error);
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