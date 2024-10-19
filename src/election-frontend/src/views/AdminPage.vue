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

    <table v-if="!loading && displayedUsers.length" class="min-w-full bg-gray-100 rounded-lg shadow">
      <thead class="bg-gray-800 text-white">
      <tr>
        <th class="px-4 py-2">ID</th>
        <th class="px-4 py-2">Username</th>
        <th class="px-4 py-2">Email</th>
        <th class="px-4 py-2">Active</th>
        <th class="px-4 py-2">Actions</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="user in displayedUsers" :key="user.id" class="hover:bg-gray-200">
        <td class="border px-4 py-2">{{ user.id }}</td>
        <td class="border px-4 py-2">{{ user.username }}</td>
        <td class="border px-4 py-2">{{ user.email }}</td>
        <td class="border px-4 py-2">
          <span v-if="user.active" class="text-green-600">Yes</span>
          <span v-else class="text-red-600">No</span>
        </td>
        <td class="border px-4 py-2 flex justify-center items-center space-x-2">
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
      users: [
        {id: 1, username: "user1", email: "user1@example.com", active: true},
        {id: 2, username: "user2", email: "user2@example.com", active: true},
        {id: 3, username: "user3", email: "user3@example.com", active: false},
        {id: 4, username: "user4", email: "user4@example.com", active: true},
        {id: 5, username: "user5", email: "user5@example.com", active: false},
      ],
      displayedUsers: [],
      loading: false,
    };
  },
  methods: {
    async fetchUsers() {
      this.loading = true;
      await new Promise((resolve) => setTimeout(resolve, 1000));
      this.loading = false;


      this.displayedUsers = [...this.users];
    },
    async toggleActive(user) {
      user.active = !user.active;
    },
    async deleteUser(userId) {
      this.users = this.users.filter((user) => user.id !== userId);

      this.displayedUsers = this.users;
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
