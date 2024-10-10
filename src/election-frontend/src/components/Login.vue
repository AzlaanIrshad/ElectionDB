<template>
  <div class="login-container">
    <h2>Login</h2>
    <form @submit.prevent="loginUser">
      <div class="form-group">
        <label for="email">Email:</label>
        <input type="email" id="email" placeholder="Enter your email" required>
      </div>
      <div class="form-group">
        <label for="password">Password:</label>
        <input type="password" id="password" placeholder="Enter your password" required>
      </div>
      <button type="submit" class="login-button">Login</button>
      <p class="error-message" v-if="errorMessage">{{ errorMessage }}</p>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      errorMessage: ''
    };
  },
  methods: {
    loginUser() {
      const email = document.getElementById('email').value;
      const password = document.getElementById('password').value;
      
    }
  }
};
</script>
