<template>
  <div class="login-container flex items-center justify-center min-h-screen bg-gray-50">
    <div class="login-box bg-white shadow-lg rounded-lg p-8 max-w-sm w-full">
      <h2 class="text-2xl font-bold text-center text-gray-800 mb-4">Login</h2>
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
export default {
  data() {
    return {
      email: '',
      password: '',
      errorMessage: ''
    };
  },
  methods: {
    async loginUser() {
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

        const data = await response.json();

        if (response.ok) {
          // Save token or user data if necessary
          localStorage.setItem('token', data.token);

          // Redirect succes
          this.$router.push('/');
        } else {
          // Show error message if login fails
          this.errorMessage = data.message;
        }
      } catch (error) {
        console.error('Login error:', error);
        this.errorMessage = 'An error occurred. Please try again.';
      }
    }
  }
};
</script>
