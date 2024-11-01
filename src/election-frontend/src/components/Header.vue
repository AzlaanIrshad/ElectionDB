<template>
  <header class="bg-gray-900 py-4 md:py-6 text-white shadow-lg">
    <div class="container mx-auto flex justify-between items-center px-4 md:px-6">
      <router-link to="/" class="logo text-2xl md:text-3xl font-bold">
        Electiondb
      </router-link>
      <div class="flex items-center">
        <router-link
            v-if="!isLoggedIn"
            to="/login"
            class="text-base md:text-lg text-white bg-blue-600 hover:bg-blue-500 rounded-full px-4 md:px-6 py-2 md:py-3 transition duration-300 mr-4"
        >
          Login
        </router-link>

        <div v-if="isLoggedIn">
          <router-link
              to="/threads"
              class="text-base md:text-lg text-white bg-green-600 hover:bg-green-500 rounded-full px-4 md:px-6 py-2 md:py-3 transition duration-300 mr-4"
          >
            Threads
          </router-link>
          <button
              v-if="isAdmin"
              class="text-base md:text-lg text-white bg-blue-600 hover:bg-blue-500 rounded-full px-4 md:px-6 py-2 md:py-3 transition duration-300 mr-4"
              @click="$router.push('/admin')"
          >
            Go to Admin Panel
          </button>
          <button
              class="text-base md:text-lg text-white bg-red-600 hover:bg-red-500 rounded-full px-4 md:px-6 py-2 md:py-3 transition duration-300"
              @click="logout"
          >
            Logout
          </button>
        </div>
      </div>
    </div>
  </header>
</template>

<script>
export default {
  name: "HeaderComponent",
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
