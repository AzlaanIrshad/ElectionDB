<template>
  <link rel="stylesheet" href="../assets/AdminPage.css">
  <div class="admin-page">
    <h1>Admin Page</h1>

    <ul>
      <li v-for="user in users" :key="user.id">
        {{ user.username }} - <button @click="deactivateUser(user.email)">Deactivate</button>
      </li>
    </ul>
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
        const response = await fetch('/api/users');
        if (!response.ok) throw new Error('Network response was not ok');
        this.users = await response.json();
      } catch (error) {
        console.error('Error fetching users:', error);
      } finally {
        this.loading = false;
      }
    },
    async deactivateUser(email) {
      try {
        const response = await fetch('/api/users/deactivate', {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({email}),
        });
        if (!response.ok) throw new Error('Network response was not ok');
        await this.fetchUsers();
      } catch (error) {
        console.error('Error deactivating user:', error);
      }
    },
  },
  mounted() {
    this.fetchUsers();
  },
};
</script>

