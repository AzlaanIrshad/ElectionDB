<template>
  <div class="login-container flex items-center justify-center min-h-screen bg-gray-100">
    <div class="login-box bg-white shadow-lg rounded-lg p-8 max-w-md w-full">
      <h2 class="text-2xl font-bold text-center text-gray-800 mb-6">Login</h2>
      <form @submit.prevent="loginUser">
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
        <div class="form-group mb-6">
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
        <button
            type="submit"
            class="w-full bg-blue-700 text-white py-2 rounded-md hover:bg-blue-600 transition duration-300"
        >
          Login
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
      email: '',
      password: '',
      errorMessage: ''
    };
  },
  methods: {
    loginUser() {
      // Dummy authentication check
      if (this.email === 'user@example.com' && this.password === 'password') {
        alert('Login successful!');
        this.errorMessage = ''; // Reset error message
      } else {
        this.errorMessage = 'Invalid email or password.';
      }
    }
  }
};
</script>

<style scoped>
.login-container {
  background-color: #f8fafc; /* Light gray background */
}

.login-box {
  background-color: white; /* White box for form */
}

.form-group {
  margin-bottom: 1rem; /* Space between form groups */
}

.error-message {
  margin-top: 1rem; /* Space above error message */
}
</style>
