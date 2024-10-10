<template>
  <div class="register-container flex items-center justify-center min-h-screen bg-gray-100">
    <div class="register-box bg-white shadow-lg rounded-lg p-8 max-w-md w-full">
      <h2 class="text-2xl font-bold text-center text-gray-800 mb-6">Register</h2>
      <form @submit.prevent="registerUser">
        <div class="form-group mb-4">
          <label for="username" class="block text-gray-700">Username:</label>
          <input
              type="text"
              id="username"
              v-model="username"
              placeholder="Enter your username"
              required
              class="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-blue-300"
          />
        </div>
        <div class="form-group mb-4">
          <label for="email" class="block text-gray-700">Email:</label>
          <input
              type="email"
              id="email"
              v-model="email"
              placeholder="Enter your email"
              required
              class="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-blue-300"
          />
        </div>
        <div class="form-group mb-4">
          <label for="password" class="block text-gray-700">Password:</label>
          <input
              type="password"
              id="password"
              v-model="password"
              placeholder="Enter your password"
              required
              class="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-blue-300"
          />
        </div>
        <div class="form-group mb-6">
          <label for="confirmPassword" class="block text-gray-700">Confirm Password:</label>
          <input
              type="password"
              id="confirmPassword"
              v-model="confirmPassword"
              placeholder="Confirm your password"
              required
              class="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-blue-300"
          />
        </div>
        <button
            type="submit"
            class="w-full bg-blue-700 text-white py-2 rounded-md hover:bg-blue-600 transition duration-300"
        >
          Register
        </button>
        <p class="error-message text-red-500 text-center mt-4" v-if="errorMessage">{{ errorMessage }}</p>
      </form>
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
      errorMessage: ''
    };
  },
  methods: {
    registerUser() {
      // Dummy registration check
      if (this.password !== this.confirmPassword) {
        this.errorMessage = 'Passwords do not match.';
        return;
      }

      // Simulating a successful registration
      alert(`Registration successful for ${this.username}!`);
      this.errorMessage = ''; // Reset error message
      // Reset fields after successful registration
      this.username = '';
      this.email = '';
      this.password = '';
      this.confirmPassword = '';
    }
  }
};
</script>

<style scoped>
.register-container {
  background-color: #f8fafc; /* Light gray background */
}

.register-box {
  background-color: white; /* White box for form */
}

.form-group {
  margin-bottom: 1rem; /* Space between form groups */
}

.error-message {
  margin-top: 1rem; /* Space above error message */
}
</style>
le>