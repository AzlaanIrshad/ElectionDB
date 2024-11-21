<template>
  <div class="login-container flex items-center justify-center min-h-screen dark:bg-gray-900">
    <div class="login-box bg-white dark:bg-gray-800 shadow-lg rounded-lg p-8 max-w-sm w-full">
      <h2 class="text-2xl font-bold text-center text-gray-800 dark:text-gray-100 mb-4">Inloggen</h2>
      <form @submit.prevent="loginUser">
        <div class="form-group mb-4">
          <input
              type="email"
              id="email"
              v-model="email"
              placeholder="E-mailadres"
              required
              class="w-full p-3 border border-gray-300 dark:border-gray-600 rounded-md focus:outline-none focus:ring focus:ring-blue-300 dark:focus:ring-blue-800 bg-gray-50 dark:bg-gray-700 text-gray-800 dark:text-gray-100"
          />
        </div>
        <div class="form-group mb-4">
          <input
              type="password"
              id="password"
              v-model="password"
              placeholder="Wachtwoord"
              required
              class="w-full p-3 border border-gray-300 dark:border-gray-600 rounded-md focus:outline-none focus:ring focus:ring-blue-300 dark:focus:ring-blue-800 bg-gray-50 dark:bg-gray-700 text-gray-800 dark:text-gray-100"
          />
        </div>
        <div class="flex items-center justify-between mb-6">
          <div>
            <input type="checkbox" id="remember" class="mr-1" v-model="rememberMe" />
            <label for="remember" class="text-gray-600 dark:text-gray-300 text-sm">Onthoud mij</label>
          </div>
          <a href="#" class="text-sm text-blue-600 dark:text-blue-400 hover:underline">Wachtwoord vergeten?</a>
        </div>
        <button
            type="submit"
            class="w-full bg-blue-600 dark:bg-blue-800 text-white py-2 rounded-md hover:bg-blue-500 dark:hover:bg-blue-900 transition duration-300"
        >
          Inloggen
        </button>
        <p class="error-message text-red-500 text-center mt-4" v-if="errorMessage">{{ errorMessage }}</p>
      </form>
      <p class="text-center text-gray-600 dark:text-gray-300 mt-6">
        Of <a href="/register" class="text-blue-600 dark:text-blue-400 hover:underline">maak een account aan</a>
      </p>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      email: '',
      password: '',
      rememberMe: false,
      errorMessage: '',
    };
  },
  methods: {
    async loginUser() {
      this.errorMessage = ''; // Reset foutmelding
      try {
        const response = await fetch('http://localhost:8080/api/login', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            email: this.email,
            password: this.password,
          }),
        });

        if (!response.ok) {
          throw new Error('Ongeldig e-mailadres of wachtwoord');
        }
        const token = await response.text();

        localStorage.setItem('token', token);

        this.$router.push({name: 'home'});
      } catch (error) {
        this.errorMessage = error.message;
      }
    }
  }
};
</script>
