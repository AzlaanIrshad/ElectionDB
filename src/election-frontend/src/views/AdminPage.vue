<template>
  <link rel="stylesheet" href="../assets/AdminPage.css">
  <div class="admin-page">
    <h1>Admin Page</h1>

    <!-- Loading feedback -->
    <div v-if="loading">Loading users...</div>

    <table v-if="!loading">
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
          <button @click="deactivateUser(user.email)">Deactivate</button>
          <button @click="toggleActive(user)">
            {{ user.active ? 'Disable' : 'Enable' }}
          </button>
          <button @click="deleteUser(user.id)">Delete</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name: "AdminPage",
  data() {
    return {
      users: [],
      loading: true,
    };
  },
  methods: {
    async fetchUsers() {
      try {
        const response = await fetch("/api/users")
        if (!response.ok) throw new Error('Network response was not ok');
        this.users = await response.json();
        console.log('Users:', this.users);
      } catch (error) {
        console.error('Error fetching users:', error);
      } finally {
        this.loading = false;
      }
    },
    async deactivateUser(email) {
      try {
        const response = await fetch('http://localhost:3000/api/users/deactivate', {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({ email }),
        });
        if (!response.ok) throw new Error('Network response was not ok');
        await this.fetchUsers();
      } catch (error) {
        console.error('Error deactivating user:', error);
      }
    },
    toggleActive(user) {
      // Here you could implement functionality to enable/disable the user,
      // but since you're already using deactivateUser, this can remain for toggling
      user.active = !user.active;
      // Call deactivateUser to handle the update with the backend
      this.deactivateUser(user.email);
    },
    async deleteUser(userId) {
      // Placeholder for delete functionality
      console.log(`Delete user with ID: ${userId}`);
      // Add your delete logic here
    },
  },
  mounted() {
    this.fetchUsers();
  },
};
</script>

<style scoped>
/* Add your styles here */
</style>
