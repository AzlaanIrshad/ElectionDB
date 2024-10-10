<template>
  <div>
    <h1>User Management</h1>

    <!-- Form to add/edit users -->
    <form @submit.prevent="submitForm">
      <input type="hidden" v-model="user.id" />
      <label>Username: <input type="text" v-model="user.username" /></label>
      <label>Email: <input type="email" v-model="user.email" /></label>
      <label>Password: <input type="password" v-model="user.password" /></label>
      <!-- Removed Active checkbox -->
      <button type="submit">Save</button>
    </form>

    <!-- User Table -->
    <table>
      <thead>
      <tr>
        <th>ID</th>
        <th>Username</th>
        <th>Email</th>
        <th>Active</th>
        <th>Actions</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="user in users" :key="user.id">
        <td>{{ user.id }}</td>
        <td>{{ user.username }}</td>
        <td>{{ user.email }}</td>
        <td>{{ user.active ? 'Yes' : 'No' }}</td>
        <td>
          <button @click="editUser(user)">Edit</button>
          <button @click="deleteUser(user.id)">Delete</button>
          <button @click="toggleActive(user)">
            {{ user.active ? 'Disable' : 'Enable' }}
          </button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      users: [],
      user: { id: null, username: '', email: '', password: '', active: false }
    };
  },
  created() {
    this.loadUsers();
  },
  methods: {
    // Fetch all users from the backend
    loadUsers() {
      axios.get('/admin/users').then(response => {
        this.users = response.data;
      });
    },
    // Create or update user via form
    submitForm() {
      if (this.user.id) {
        // Update existing user
        axios.put(`/admin/users/${this.user.id}`, this.user).then(() => {
          this.loadUsers();
          this.resetForm();
        });
      } else {
        // Create new user
        axios.post('/admin/users', this.user).then(() => {
          this.loadUsers();
          this.resetForm();
        });
      }
    },
    // Pre-fill form for editing a user
    editUser(user) {
      this.user = { ...user }; // Clone user to form
    },
    // Delete user with confirmation
    deleteUser(id) {
      if (confirm('Are you sure you want to delete this user?')) {
        axios.delete(`/admin/users/${id}`).then(() => {
          this.loadUsers();
        });
      }
    },
    // Toggle user active status (Enable/Disable)
    toggleActive(user) {
      const action = user.active ? 'Disable' : 'Enable';
      if (confirm(`Are you sure you want to ${action} this user?`)) {
        // Make API call to toggle active status
        axios.put(`/admin/users/${user.id}/toggle-active`).then(() => {
          this.loadUsers(); // Reload users to refresh the list
        });
      }
    },
    // Reset form fields
    resetForm() {
      this.user = { id: null, username: '', email: '', password: '', active: false };
    }
  }
};
</script>
