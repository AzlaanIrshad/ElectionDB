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
    // Fetch the users from backend
    async fetchUsers() {
  try {
    const response = await fetch('http://localhost:8080/api/users');
    if (!response.ok) {
      const text = await response.text();
      throw new Error(`Network response was not ok: ${text}`);
    }
    this.users = await response.json();
  } catch (error) {
    console.error('Error fetching users:', error);
  }
}

,

    // Deactivate a user
    async deactivateUser(email) {
      try {
        const response = await fetch('http://localhost:8080/api/users/deactivate', {  // Add correct backend URL
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({ email }),
        });
        if (!response.ok) {
          const text = await response.text();  // Capture detailed error response
          throw new Error(`Network response was not ok: ${text}`);
        }
        await this.fetchUsers();  // Refresh the user list after successful deactivation
      } catch (error) {
        console.error('Error deactivating user:', error);
      }
    },
  },
  mounted() {
    this.fetchUsers();  // Fetch users on component mount
  },
};
</script>

