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
            required
            class="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-blue-300"
          />
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
        >
          Register
        </button>
        <p class="error-message text-red-500 text-center mt-4" v-if="errorMessage">{{ errorMessage }}</p>
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
      errorMessage: ''
    };
  },
  methods: {
    async registerUser() {
      this.errorMessage = '';

      // Check if passwords match
      if (this.password !== this.confirmPassword) {
        this.errorMessage = 'Passwords do not match.';
        return;
      }

      // Create a user object
      const user = {
        username: this.username,
        email: this.email,
        password: this.password
      };

      try {
        // Send registration request to the backend
        const response = await fetch('http://localhost:8080/api/register', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(user)
        });

        // Handle the response
        if (!response.ok) {
          const errorData = await response.json();
          this.errorMessage = errorData.message || 'Registration failed. Please try again.';
          return;
        }
        alert(`Registration successful for ${this.username}!`);

        // Clear the form fields
        this.username = '';
        this.email = '';
        this.password = '';
        this.confirmPassword = '';
      } catch (error) {
        this.errorMessage = 'An error occurred. Please try again later.';
        console.error('Registration error:', error);
      }
    }
  }
};
</script>
