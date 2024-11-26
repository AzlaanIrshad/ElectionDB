<template>
  <div class="admin-page p-6 bg-white dark:bg-gray-800 shadow-md rounded-lg">
    <h1 class="text-3xl font-bold mb-4 text-gray-800 dark:text-gray-100">Beheerderspagina</h1>

    <input
        type="text"
        placeholder="Zoek gebruikers..."
        class="border border-gray-300 dark:border-gray-600 rounded-md p-2 mb-4 w-full bg-gray-50 dark:bg-gray-700 text-gray-800 dark:text-gray-200 placeholder-gray-500 dark:placeholder-gray-400"
        @input="searchUsers"
    />

    <div v-if="loading" class="text-gray-500 dark:text-gray-400">Gebruikers laden...</div>

    <table v-if="!loading && displayedUsers.length" class="min-w-full bg-gray-100 dark:bg-gray-700 rounded-lg shadow border-collapse">
      <thead class="bg-gray-800 dark:bg-gray-900 text-white">
      <tr>
        <th class="px-4 py-2 text-center">ID</th>
        <th class="px-4 py-2 text-center">Gebruikersnaam</th>
        <th class="px-4 py-2 text-center">E-mail</th>
        <th class="px-4 py-2 text-center">Actief</th>
        <th class="px-4 py-2 text-center">Acties</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="user in displayedUsers" :key="user.id" class="hover:bg-gray-200 dark:hover:bg-gray-600">
        <td class="px-4 py-2 text-center align-middle text-gray-800 dark:text-gray-200">{{ user.id }}</td>
        <td class="px-4 py-2 text-center align-middle text-gray-800 dark:text-gray-200">{{ user.username }}</td>
        <td class="px-4 py-2 text-center align-middle text-gray-800 dark:text-gray-200">{{ user.email }}</td>
        <td class="px-4 py-2 text-center align-middle">
          <span v-if="user.active" class="text-green-600 dark:text-green-400">Ja</span>
          <span v-else class="text-red-600 dark:text-red-400">Nee</span>
        </td>
        <td class="px-4 py-2 text-center align-middle flex justify-center items-center space-x-2">
          <button
              @click="toggleActive(user)"
              class="bg-blue-500 hover:bg-blue-600 dark:bg-blue-700 dark:hover:bg-blue-600 text-white font-bold py-1 px-4 rounded w-32"
          >
            {{ user.active ? 'Deactiveren' : 'Activeren' }}
          </button>
          <button
              @click="confirmDelete(user)"
              class="bg-red-500 hover:bg-red-600 dark:bg-red-700 dark:hover:bg-red-600 text-white font-bold py-1 px-4 rounded w-32"
          >
            Verwijderen
          </button>
        </td>
      </tr>
      </tbody>
    </table>

    <div v-if="!loading && displayedUsers.length === 0" class="text-gray-500 dark:text-gray-400">
      Geen gebruikers gevonden.
    </div>

    <!-- Delete Confirmation Modal -->
    <div v-if="showDeleteModal" class="fixed inset-0 bg-gray-500 bg-opacity-50 flex items-center justify-center z-50">
      <div class="bg-white dark:bg-gray-800 rounded-lg p-6">
        <h3 class="text-xl font-bold text-gray-800 dark:text-gray-100">Weet je zeker dat je deze gebruiker wilt verwijderen?</h3>
        <div class="mt-4 flex justify-end space-x-4">
          <button
              @click="deleteUser"
              class="bg-red-500 hover:bg-red-600 text-white font-bold py-2 px-4 rounded"
          >
            Ja
          </button>
          <button
              @click="cancelDelete"
              class="bg-gray-500 hover:bg-gray-600 text-white font-bold py-2 px-4 rounded"
          >
            Nee
          </button>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import config from '../config';

export default {
  name: "AdminPage",
  data() {
    return {
      users: [],
      displayedUsers: [],
      loading: false,
      showDeleteModal: false,
      userToDelete: null,
    };
  },
  methods: {
    async fetchUsers() {
      this.loading = true;
      try {
        const response = await fetch(`${config.apiBaseUrl}/api/users`);
        const data = await response.json();
        this.users = data;
        this.displayedUsers = [...this.users];
      } catch (error) {
        console.error('Fout bij het ophalen van gebruikers:', error);
      } finally {
        this.loading = false;
      }
    },

    async toggleActive(user) {
      user.active = !user.active;
      try {
        await fetch(`${config.apiBaseUrl}/api/users/${user.id}`, {
          method: 'PUT',
          headers: {'Content-Type': 'application/json'},
          body: JSON.stringify({active: user.active}),
        });
      } catch (error) {
        console.error('Fout bij het bijwerken van de gebruikersstatus:', error);
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

    confirmDelete(user) {
      this.userToDelete = user;
      this.showDeleteModal = true;
    },

    async deleteUser() {
      if (this.userToDelete) {
        try {
          await fetch(`${config.apiBaseUrl}/api/users/${this.userToDelete.id}`, {
            method: 'DELETE',
            headers: { 'Content-Type': 'application/json' },
          });

          if (response.ok) {
            this.users = this.users.filter(user => user.id !== this.userToDelete.id);
            this.displayedUsers = [...this.users];

            this.showDeleteModal = false;
            this.userToDelete = null;
            alert('User deleted successfully!');
          } else {
            alert('Failed to delete the user');
          }
        } catch (error) {
          console.error('Error deleting user:', error);
        }
      }
    },

    cancelDelete() {
      this.showDeleteModal = false;
      this.userToDelete = null;
    }
  },
  mounted() {
    this.fetchUsers();
  },
};
</script>

<style scoped>

</style>
