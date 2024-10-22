<template>
  <div class="register-container flex items-center justify-center min-h-screen bg-gray-50">
    <div class="register-box bg-white shadow-lg rounded-lg p-8 max-w-sm w-full">
      <h2 class="text-2xl font-bold text-center text-gray-800 mb-6">Create Account</h2>
      <form @submit.prevent="registerUser">
        <div class="form-group mb-4">
          <input
              type="text"
              id="username"
              v-model="username"
              placeholder="Username"
              required
              class="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-blue-300"
          />
        </div>
        <div class="form-group mb-4">
          <input
              type="email"
              id="email"
              v-model="email"
              placeholder="Email"
              required
              class="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-blue-300"
          />
        </div>
        <div class="form-group mb-4">
          <input
              type="password"
              id="password"
              v-model="password"
              placeholder="Password"
              @input="isTyping = true"
              required
              class="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-blue-300"
          />
          <div v-if="isTyping" class="text-red-500 mt-2">
            <p v-if="!hasUppercase">Password needs one uppercase letter</p>
            <p v-if="!hasNumberOrSpecialChar">Password needs one number or one special character</p>
          </div>
        </div>

        <div class="form-group mb-4">
          <input
              type="password"
              id="confirmPassword"
              v-model="confirmPassword"
              placeholder="Confirm Password"
              required
              class="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-blue-300"
          />
        </div>
        <button
            type="submit"
            class="w-full bg-blue-600 text-white py-2 rounded-md hover:bg-blue-500 transition duration-300"
            :disabled="!isPasswordValid"
        >
          Register
        </button>

        <p class="error-message text-red-500 text-center mt-4" v-if="errorMessage">{{ errorMessage }}</p>
        <p class="success-message text-green-500 text-center mt-4" v-if="successMessage">{{ successMessage }}</p>
      </form>
      <p class="text-center text-gray-600 mt-6">
        Already have an account? <a href="/login" class="text-blue-600 hover:underline">Login here</a>
      </p>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: '',
      email: '',
      password: '',
      confirmPassword: '',
      errorMessage: '',
      successMessage: '',
      isTyping: false,  // Variable to track if the user is typing
    };
  },
  computed: {
    hasUppercase() {
      // Controle of er een hoofdletter is
      return /[A-Z]/.test(this.password);
    },
    hasNumberOrSpecialChar() {
      // Controle op een nummer of speciaal teken
      return /[0-9!@#$%^&*]/.test(this.password);
    },
    isPasswordValid() {
      // Controleer of het wachtwoord voldoet aan alle vereisten
      return this.password.length >= 6 && this.hasUppercase && this.hasNumberOrSpecialChar;
    },
  },
  methods: {
    async registerUser() {
      this.errorMessage = '';
      this.successMessage = '';

      // Simpele controle of wachtwoorden overeenkomen
      if (this.password !== this.confirmPassword) {
        this.errorMessage = "Passwords do not match";
        return;
      }

      try {
        const response = await fetch('http://localhost:8080/api/register', {
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
          throw new Error(errorData || 'Registration failed');
        }

        const successMessage = await response.text();
        this.successMessage = successMessage;

      } catch (error) {
        this.errorMessage = error.message;
      }
    },
  },
};
</script>

<style scoped>
/* Voeg hier aangepaste CSS toe als dat nodig is */
</style>
