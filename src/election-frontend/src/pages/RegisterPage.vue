<template>
  <div class="register-container flex items-center justify-center min-h-screen dark:bg-gray-900">
    <div class="register-box bg-white dark:bg-gray-800 shadow-lg rounded-lg p-8 max-w-sm w-full">
      <h2 class="text-2xl font-bold text-center text-gray-800 dark:text-gray-100 mb-6">Account Aanmaken</h2>
      <form @submit.prevent="registerUser">
        <!-- Gebruikersnaam -->
        <div class="form-group mb-4">
          <input
              type="text"
              id="username"
              v-model="username"
              placeholder="Gebruikersnaam"
              required
              class="w-full p-3 border border-gray-300 dark:border-gray-600 rounded-md focus:outline-none focus:ring focus:ring-blue-300 dark:focus:ring-blue-800 bg-gray-50 dark:bg-gray-700 text-gray-800 dark:text-gray-100"
          />
          <ul class="text-red-500 text-sm mt-1">
            <li v-for="error in fieldErrors.username" :key="error">{{ error }}</li>
          </ul>
        </div>

        <!-- E-mailadres -->
        <div class="form-group mb-4">
          <input
              type="email"
              id="email"
              v-model="email"
              placeholder="E-mailadres"
              required
              class="w-full p-3 border border-gray-300 dark:border-gray-600 rounded-md focus:outline-none focus:ring focus:ring-blue-300 dark:focus:ring-blue-800 bg-gray-50 dark:bg-gray-700 text-gray-800 dark:text-gray-100"
          />
          <ul class="text-red-500 text-sm mt-1">
            <li v-for="error in fieldErrors.email" :key="error">{{ error }}</li>
          </ul>
        </div>

        <!-- Wachtwoord -->
        <div class="form-group mb-4 relative">
          <input
              :type="showPassword ? 'text' : 'password'"
              id="password"
              v-model="password"
              placeholder="Wachtwoord"
              required
              class="w-full p-3 border border-gray-300 dark:border-gray-600 rounded-md focus:outline-none focus:ring focus:ring-blue-300 dark:focus:ring-blue-800 bg-gray-50 dark:bg-gray-700 text-gray-800 dark:text-gray-100"
          />
          <ul class="text-red-500 text-sm mt-1">
            <li v-for="error in fieldErrors.password" :key="error">{{ error }}</li>
          </ul>
        </div>

        <!-- Bevestig Wachtwoord -->
        <div class="form-group mb-4 relative">
          <input
              :type="showPassword ? 'text' : 'password'"
              id="confirmPassword"
              v-model="confirmPassword"
              placeholder="Bevestig Wachtwoord"
              required
              class="w-full p-3 border border-gray-300 dark:border-gray-600 rounded-md focus:outline-none focus:ring focus:ring-blue-300 dark:focus:ring-blue-800 bg-gray-50 dark:bg-gray-700 text-gray-800 dark:text-gray-100"
          />
          <ul class="text-red-500 text-sm mt-1">
            <li v-for="error in fieldErrors.confirmPassword" :key="error">{{ error }}</li>
          </ul>
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
              <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  d="M2.036 12.322a1.012 1.012 0 0 1 0-.639C3.423 7.51 7.36 4.5 12 4.5c4.638 0 8.573 3.007 9.963 7.178.07.207.07.431 0 .639C20.577 16.49 16.64 19.5 12 19.5c-4.638 0-8.573-3.007-9.963-7.178Z"
              />
              <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  d="M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z"
              />
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
              <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  d="M3.98 8.223A10.477 10.477 0 0 0 1.934 12C3.226 16.338 7.244 19.5 12 19.5c.993 0 1.953-.138 2.863-.395M6.228 6.228A10.451 10.451 0 0 1 12 4.5c4.756 0 8.773 3.162 10.065 7.498a10.522 10.522 0 0 1-4.293 5.774M6.228 6.228 3 3m3.228 3.228 3.65 3.65m7.894 7.894L21 21m-3.228-3.228-3.65-3.65m0 0a3 3 0 1 0-4.243-4.243m4.242 4.242L9.88 9.88"
              />
            </svg>
          </button>
        </div>

        <button
            type="submit"
            class="w-full bg-blue-600 dark:bg-blue-700 hover:bg-blue-700 dark:hover:bg-blue-800 text-white py-3 px-4 rounded-md focus:outline-none focus:ring focus:ring-blue-300 dark:focus:ring-blue-800 transition"
        >
          Registreren
        </button>
        <p v-if="errorMessage" class="text-red-500 text-sm mt-4 text-center">{{ errorMessage }}</p>
      </form>
      <p class="text-center text-gray-600 dark:text-gray-300 mt-6">
        Of <a href="/login" class="text-blue-600 dark:text-blue-400 hover:underline">Heb je al een account?</a>
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
      showPassword: false,
      fieldErrors: {
        username: [],
        email: [],
        password: [],
        confirmPassword: [],
      },
      errorMessage: '',
    };
  },
  watch: {
    username: 'validateUsername',
    email: 'validateEmail',
    password: 'validatePassword',
    confirmPassword: 'validateConfirmPassword',
  },
  methods: {
    togglePassword() {
      this.showPassword = !this.showPassword;
    },
    validateUsername() {
      this.fieldErrors.username = [];
      if (!this.username) {
        this.fieldErrors.username.push('Gebruikersnaam is verplicht.');
      }
    },
    validateEmail() {
      this.fieldErrors.email = [];
      if (!this.email) {
        this.fieldErrors.email.push('E-mailadres is verplicht.');
      } else if (!/\S+@\S+\.\S+/.test(this.email)) {
        this.fieldErrors.email.push('E-mailadres is ongeldig.');
      }
    },
    validatePassword() {
      this.fieldErrors.password = [];
      if (!this.password) {
        this.fieldErrors.password.push('Wachtwoord is verplicht.');
      }
      if (this.password.length < 6) {
        this.fieldErrors.password.push('Wachtwoord moet minimaal 6 tekens bevatten.');
      }
      if (!/[0-9!@#$%^&*]/.test(this.password)) {
        this.fieldErrors.password.push('Wachtwoord moet minimaal één cijfer of speciaal teken bevatten.');
      }
    },
    validateConfirmPassword() {
      this.fieldErrors.confirmPassword = [];
      if (this.password !== this.confirmPassword) {
        this.fieldErrors.confirmPassword.push('Wachtwoorden komen niet overeen.');
      }
    },
    async registerUser() {
      this.validateUsername();
      this.validateEmail();
      this.validatePassword();
      this.validateConfirmPassword();

      const isValid = Object.values(this.fieldErrors).every((field) => field.length === 0);
      if (!isValid) return;

      try {
        const response = await fetch(`${config.apiBaseUrl}/api/register`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ username: this.username, email: this.email, password: this.password }),
        });

        if (!response.ok) {
          this.errorMessage = await response.text();
          return;
        }

        this.$router.push({ name: 'login' });
      } catch (error) {
        this.errorMessage = error.message || 'Er is een onverwachte fout opgetreden.';
      }
    },
  },
};
</script>
