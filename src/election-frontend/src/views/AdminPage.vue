<template>
  <div class="admin-page p-6 bg-white shadow-md rounded-lg">
    <h1 class="text-3xl font-bold mb-4">Admin Page</h1>

    <input
        type="text"
        placeholder="Search users..."
        class="border border-gray-300 rounded-md p-2 mb-4 w-full"
        @input="searchUsers"
    />

    <div v-if="loading" class="text-gray-500">Loading users...</div>

    <table v-if="!loading && displayedUsers.length" class="min-w-full bg-gray-100 rounded-lg shadow border-collapse">
      <thead class="bg-gray-800 text-white">
      <tr>
        <th class="px-4 py-2">ID</th>
        <th class="px-4 py-2">Username</th>
        <th class="px-4 py-2">Email</th>
        <th class="px-4 py-2 text-center">Active</th>
        <th class="px-4 py-2">Actions</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="user in displayedUsers" :key="user.id" class="hover:bg-gray-200">
        <td class="px-4 py-2">{{ user.id }}</td>
        <td class="px-4 py-2">{{ user.username }}</td>
        <td class="px-4 py-2">{{ user.email }}</td>
        <td class="px-4 py-2 text-center">
          <span v-if="user.active" class="text-green-600">Yes</span>
          <span v-else class="text-red-600">No</span>
        </td>
        <td class="px-4 py-2 flex justify-center items-center space-x-2">
          <button
              @click="toggleActive(user)"
              class="bg-blue-500 hover:bg-blue-600 text-white font-bold py-1 px-4 rounded w-32"
          >
            {{ user.active ? 'Deactivate' : 'Activate' }}
          </button>
          <button
              @click="deleteUser(user.id)"
              class="bg-red-500 hover:bg-red-600 text-white font-bold py-1 px-4 rounded w-32"
          >
            Delete
          </button>
        </td>
      </tr>
      </tbody>
    </table>

    <div v-if="!loading && displayedUsers.length === 0" class="text-gray-500">
      No users found.
    </div>
  </div>
</template>

<script>
export default {
  name: "AdminPage",
  data() {
    return {
      users: [],
      displayedUsers: [],
      loading: false,
    };
  },
  methods: {
    async fetchUsers() {
      this.loading = true;
      try {
        const response = await fetch('http://localhost:8080/api/users');
        const data = await response.json();
        this.users = data;
        this.displayedUsers = [...this.users];
      } catch (error) {
        console.error('Error fetching users:', error);
      } finally {
        this.loading = false;
      }
    },
    async toggleActive(user) {
      user.active = !user.active;
      try {
        await fetch(`http://localhost:8080/api/users/${user.id}`, {
          method: 'PUT',
          headers: {'Content-Type': 'application/json'},
          body: JSON.stringify({active: user.active})
        });
      } catch (error) {
        console.error('Error updating user status:', error);
      }
    },
    async deleteUser(userId) {
      try {
        await fetch(`http://localhost:8080/api/users/${userId}`, {
          method: 'DELETE'
        });
        this.users = this.users.filter(user => user.id !== userId);
        this.displayedUsers = this.users;
      } catch (error) {
        console.error('Error deleting user:', error);
      }
    },
    searchUsers(event) {
      const query = event.target.value.toLowerCase();
      if (query) {
        this.displayedUsers = this.users.filter(
            (user) =>
                user.username.toLowerCase().includes(query) ||
                user.email.toLowerCase().includes(query)
        );
      } else {
        this.displayedUsers = [...this.users];
      }
    },
  },
  mounted() {
    this.fetchUsers();
  },
};
</script>

<style scoped>
.admin-page {
}
</style>
