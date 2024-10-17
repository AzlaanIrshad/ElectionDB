<template>
  <div class="login-container flex items-center justify-center min-h-screen bg-gray-50">
    <div class="login-box bg-white shadow-lg rounded-lg p-8 max-w-sm w-full">
      <h2 class="text-2xl font-bold text-center text-gray-800 mb-4">Welcome Back</h2>
      <form @submit.prevent="loginUser">
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
            required
            class="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-blue-300"
          />
        </div>
        <div class="flex items-center justify-between mb-6">
          <div>
            <input type="checkbox" id="remember" class="mr-1">
            <label for="remember" class="text-gray-600 text-sm">Remember me</label>
          </div>
          <a href="#" class="text-sm text-blue-600 hover:underline">Forgot Password?</a>
        </div>
        <button
          type="submit"
          class="w-full bg-blue-600 text-white py-2 rounded-md hover:bg-blue-500 transition duration-300"
        >
          Login
        </button>
        <p class="error-message text-red-500 text-center mt-4" v-if="errorMessage">{{ errorMessage }}</p>
      </form>
      <p class="text-center text-gray-600 mt-6">
        Or <a href="/register" class="text-blue-600 hover:underline">create an account</a>
      </p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      email: '',
      password: '',
      errorMessage: '',
      isLoading: false,
    };
  },
  methods: {
    async loginUser() {
      try {
        this.isLoading = true;
        const response = await axios.post('/auth/login', {
    email: this.email,
    password: this.password,
    rememberMe: document.getElementById('remember').checked,
});
        this.errorMessage = '';
        const sessionId = response.data.sessionId;

        if (document.getElementById('remember').checked) {
          localStorage.setItem('sessionId', sessionId);
        } else {
          sessionStorage.setItem('sessionId', sessionId);
        }

        this.$router.push('/dashboard');
      } catch (error) {
        if (error.response && error.response.status === 401) {
          this.errorMessage = 'Invalid email or password';
        } else {
          this.errorMessage = 'An error occurred. Please try again later.';
        }
      } finally {
        this.isLoading = false;
      }
    },
  },
};
</script>