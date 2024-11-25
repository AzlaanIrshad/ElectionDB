<template>
  <div class="register-container flex items-center justify-center min-h-screen dark:bg-gray-900">
    <div class="register-box bg-white dark:bg-gray-800 shadow-lg rounded-lg p-8 max-w-sm w-full">
      <h2 class="text-2xl font-bold text-center text-gray-800 dark:text-gray-100 mb-6">Account Aanmaken</h2>
      <form @submit.prevent="registerUser">
        <div class="form-group mb-4">
          <input
              type="text"
              id="username"
              v-model="username"
              placeholder="Gebruikersnaam"
              required
              class="w-full p-3 border border-gray-300 dark:border-gray-600 rounded-md focus:outline-none focus:ring focus:ring-blue-300 dark:focus:ring-blue-800 bg-gray-50 dark:bg-gray-700 text-gray-800 dark:text-gray-100"
          />
        </div>
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
              @input="isTyping = true"
              required
              class="w-full p-3 border border-gray-300 dark:border-gray-600 rounded-md focus:outline-none focus:ring focus:ring-blue-300 dark:focus:ring-blue-800 bg-gray-50 dark:bg-gray-700 text-gray-800 dark:text-gray-100"
          />
          <div v-if="isTyping" class="text-red-500 mt-2">
            <p v-if="!hasUppercase">Wachtwoord moet een hoofdletter bevatten</p>
            <p v-if="!hasNumberOrSpecialChar">Wachtwoord moet een cijfer of speciaal teken bevatten</p>
          </div>
        </div>
        <div class="form-group mb-4">
          <input
              type="password"
              id="confirmPassword"
              v-model="confirmPassword"
              placeholder="Bevestig Wachtwoord"
              required
              class="w-full p-3 border border-gray-300 dark:border-gray-600 rounded-md focus:outline-none focus:ring focus:ring-blue-300 dark:focus:ring-blue-800 bg-gray-50 dark:bg-gray-700 text-gray-800 dark:text-gray-100"
          />
        </div>
        <button
            type="submit"
            class="w-full bg-blue-600 dark:bg-blue-800 text-white py-2 rounded-md hover:bg-blue-500 dark:hover:bg-blue-900 transition duration-300"
            :disabled="!isPasswordValid"
        >
          Registreren
        </button>

        <p class="error-message text-red-500 text-center mt-4" v-if="errorMessage">{{ errorMessage }}</p>
      </form>
      <p class="text-center text-gray-600 dark:text-gray-300 mt-6">
        Heb je al een account?
        <router-link to="/login" class="text-blue-600 dark:text-blue-400 hover:underline">Log hier in</router-link>
      </p>
    </div>
  </div>
</template>

<script>
import config from '../config';

export default {
  data() {
    return {
      username: '',
      email: '',
      password: '',
      confirmPassword: '',
      errorMessage: '',
      isTyping: false,
    };
  },
  computed: {
    hasUppercase() {
      return /[A-Z]/.test(this.password);
    },
    hasNumberOrSpecialChar() {
      return /[0-9!@#$%^&*]/.test(this.password);
    },
    isPasswordValid() {
      return this.password.length >= 6 && this.hasUppercase && this.hasNumberOrSpecialChar;
    },
  },
  methods: {
    async registerUser() {
      this.errorMessage = '';
      if (this.password !== this.confirmPassword) {
        this.errorMessage = "Wachtwoorden komen niet overeen";
        return;
      }
      try {
        const response = await fetch(`${config.apiBaseUrl}/api/register`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            username: this.username,
            email: this.email,
            password: this.password,
          }),
        });

        if (!response.ok) {
          const errorData = await response.text();
          throw new Error(errorData || 'Registratie mislukt');
        }

        // Doorsturen naar de loginpagina
        this.$router.push({ name: 'login' });
      } catch (error) {
        this.errorMessage = error.message;
      }
    },
  },
};
</script>
