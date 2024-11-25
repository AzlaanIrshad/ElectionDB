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
        <div class="form-group mb-4 relative">
          <input
              :type="showPassword ? 'text' : 'password'"
              id="password"
              v-model="password"
              placeholder="Wachtwoord"
              required
              class="w-full p-3 border border-gray-300 dark:border-gray-600 rounded-md focus:outline-none focus:ring focus:ring-blue-300 dark:focus:ring-blue-800 bg-gray-50 dark:bg-gray-700 text-gray-800 dark:text-gray-100"
          />
          <button
              type="button"
              @click="togglePassword"
              class="absolute inset-y-0 right-3 flex items-center text-gray-500 dark:text-gray-300 focus:outline-none"
          >
            <svg
                v-if="showPassword"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke-width="1.5"
                stroke="currentColor"
                class="w-6 h-6"
            >
              <path stroke-linecap="round" stroke-linejoin="round" d="M2.036 12.322a1.012 1.012 0 0 1 0-.639C3.423 7.51 7.36 4.5 12 4.5c4.638 0 8.573 3.007 9.963 7.178.07.207.07.431 0 .639C20.577 16.49 16.64 19.5 12 19.5c-4.638 0-8.573-3.007-9.963-7.178Z" />
              <path stroke-linecap="round" stroke-linejoin="round" d="M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z" />
            </svg>
            <svg
                v-else
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke-width="1.5"
                stroke="currentColor"
                class="w-6 h-6"
            >
              <path stroke-linecap="round" stroke-linejoin="round" d="M3.98 8.223A10.477 10.477 0 0 0 1.934 12C3.226 16.338 7.244 19.5 12 19.5c.993 0 1.953-.138 2.863-.395M6.228 6.228A10.451 10.451 0 0 1 12 4.5c4.756 0 8.773 3.162 10.065 7.498a10.522 10.522 0 0 1-4.293 5.774M6.228 6.228 3 3m3.228 3.228 3.65 3.65m7.894 7.894L21 21m-3.228-3.228-3.65-3.65m0 0a3 3 0 1 0-4.243-4.243m4.242 4.242L9.88 9.88" />
            </svg>
          </button>
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
import config from '../config';

export default {
  data() {
    return {
      email: '',
      password: '',
      rememberMe: false,
      errorMessage: '',
      showPassword: false,
    };
  },
  methods: {
    togglePassword() {
      this.showPassword = !this.showPassword;
    },
    async loginUser() {
      this.errorMessage = ''; // Reset foutmelding
      try {
        const response = await fetch(`${config.apiBaseUrl}/api/login`, {
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

        this.$router.push({ name: 'home' });
      } catch (error) {
        this.errorMessage = error.message;
      }
    },
  },
};
</script>
