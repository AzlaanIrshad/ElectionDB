<template>
  <div class="admin-page max-w-7xl mx-auto p-8 bg-gray-100 dark:bg-gray-900 shadow-lg rounded-lg">
    <h1 class="text-4xl font-extrabold text-gray-900 dark:text-gray-100 mb-8">Beheerderspagina</h1>

    <!-- Search Bar -->
    <div class="mb-8">
      <input
          type="text"
          placeholder="Zoek gebruikers..."
          class="w-full border border-gray-300 dark:border-gray-700 rounded-lg p-4 text-gray-800 dark:text-gray-200 bg-white dark:bg-gray-800 shadow-md focus:outline-none focus:ring-2 focus:ring-blue-600 transition duration-300 ease-in-out"
          @input="searchUsers"
      />
    </div>

    <!-- Loading State -->
    <div v-if="loading" class="text-center text-gray-500 dark:text-gray-400">Gebruikers laden...</div>

    <!-- User Table -->
    <div v-if="!loading && displayedUsers.length" class="overflow-x-auto">
      <table class="min-w-full bg-white dark:bg-gray-800 rounded-lg shadow-md border border-gray-200 dark:border-gray-700">
        <thead>
        <tr class="bg-gray-900 text-white">
          <th class="px-6 py-3 text-center font-semibold text-xs sm:text-sm">ID</th>
          <th class="px-6 py-3 text-center font-semibold text-xs sm:text-sm">Gebruikersnaam</th>
          <th class="px-6 py-3 text-center font-semibold text-xs sm:text-sm">E-mail</th>
          <th class="px-6 py-3 text-center font-semibold text-xs sm:text-sm">Actief</th>
          <th class="px-6 py-3 text-center font-semibold text-xs sm:text-sm">Acties</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="user in displayedUsers" :key="user.id" class="border-b border-gray-200 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-700 transition duration-300 ease-in-out">
          <td class="px-6 py-4 text-center text-gray-800 dark:text-gray-200">{{ user.id }}</td>
          <td class="px-6 py-4 text-center text-gray-800 dark:text-gray-200">{{ user.username }}</td>
          <td class="px-6 py-4 text-center text-gray-800 dark:text-gray-200">{{ user.email }}</td>
          <td class="px-6 py-4 text-center">
              <span :class="{'text-green-600': user.active, 'text-red-600': !user.active}">
                {{ user.active ? 'Ja' : 'Nee' }}
              </span>
          </td>
          <td class="px-6 py-4 text-center">
            <div class="flex justify-center space-x-3">
              <button
                  @click="toggleActive(user)"
                  class="bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 px-4 rounded-lg shadow-md hover:shadow-lg focus:outline-none transition duration-300 ease-in-out"
              >
                {{ user.active ? 'Deactiveren' : 'Activeren' }}
              </button>
              <button
                  @click="confirmDelete(user)"
                  class="bg-red-600 hover:bg-red-700 text-white font-semibold py-2 px-4 rounded-lg shadow-md hover:shadow-lg focus:outline-none transition duration-300 ease-in-out"
              >
                Verwijderen
              </button>
            </div>
          </td>
        </tr>
        </tbody>
      </table>
    </div>

    <!-- No Users Found Message -->
    <div v-if="!loading && displayedUsers.length === 0" class="text-center text-gray-500 dark:text-gray-400">
      Geen gebruikers gevonden.
    </div>

    <!-- Delete Confirmation Modal -->
    <div v-if="showDeleteModal" class="fixed inset-0 bg-gray-500 bg-opacity-50 flex items-center justify-center z-50">
      <div class="bg-white dark:bg-gray-800 rounded-lg p-8 shadow-lg transform scale-100 hover:scale-105 transition duration-300 ease-in-out">
        <h3 class="text-xl font-bold text-gray-800 dark:text-gray-100 mb-4">Weet je zeker dat je deze gebruiker wilt verwijderen?</h3>
        <div class="flex justify-end space-x-4">
          <button
              @click="deleteUser"
              class="bg-red-600 hover:bg-red-700 text-white font-semibold py-2 px-4 rounded-lg shadow-md hover:shadow-lg focus:outline-none transition duration-300 ease-in-out"
          >
            Ja
          </button>
          <button
              @click="cancelDelete"
              class="bg-gray-500 hover:bg-gray-600 text-white font-semibold py-2 px-4 rounded-lg shadow-md hover:shadow-lg focus:outline-none transition duration-300 ease-in-out"
          >
            Nee
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { AdminPageService } from '../services/AdminPageService';

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
        this.users = await AdminPageService.fetchUsers();
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
        await AdminPageService.toggleUserActive(user.id, user.active);
      } catch (error) {
        console.error('Error updating user status:', error);
        user.active = !user.active; // Revert state in case of error
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
          await AdminPageService.deleteUser(this.userToDelete.id);
          this.users = this.users.filter(user => user.id !== this.userToDelete.id);
          this.displayedUsers = [...this.users];

          this.showDeleteModal = false;
          this.userToDelete = null;
          alert('User deleted successfully!');
        } catch (error) {
          console.error('Error deleting user:', error);
          alert('Failed to delete the user');
        }
      }
    },
    cancelDelete() {
      this.showDeleteModal = false;
      this.userToDelete = null;
    },
  },
  mounted() {
    this.fetchUsers();
  },
};
</script>



<style>
</style>
