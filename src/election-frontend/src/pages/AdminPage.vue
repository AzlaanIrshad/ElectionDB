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

    <table
        v-if="!loading && displayedUsers.length"
        class="min-w-full bg-gray-100 dark:bg-gray-700 rounded-lg shadow border-collapse"
    >
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
      <tr
          v-for="user in displayedUsers"
          :key="user.id"
          class="hover:bg-gray-200 dark:hover:bg-gray-600"
      >
        <td class="px-4 py-2 text-center align-middle text-gray-800 dark:text-gray-200">
          {{ user.id }}
        </td>
        <td class="px-4 py-2 text-center align-middle text-gray-800 dark:text-gray-200">
          {{ user.username }}
        </td>
        <td class="px-4 py-2 text-center align-middle text-gray-800 dark:text-gray-200">
          {{ user.email }}
        </td>
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
        </td>
      </tr>
      </tbody>
    </table>

    <div
        v-if="!loading && displayedUsers.length === 0"
        class="text-gray-500 dark:text-gray-400"
    >
      Geen gebruikers gevonden.
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
        const baseURL = window.location.hostname.includes("localhost")
            ? "http://localhost:8080"
            : "http://oege.ie.hva.nl:8000";

        const response = await fetch(`${baseURL}/api/users`);
        if (!response.ok) {
          throw new Error("Fout bij het ophalen van gebruikers");
        }
        const data = await response.json();
        this.users = data;
        this.displayedUsers = [...this.users];
      } catch (error) {
        console.error("Fout bij het ophalen van gebruikers:", error);
      } finally {
        this.loading = false;
      }
    },
    async toggleActive(user) {
      user.active = !user.active;
      try {
        const baseURL = window.location.hostname.includes("localhost")
            ? "http://localhost:8080"
            : "http://oege.ie.hva.nl:8000";

        await fetch(`${baseURL}/api/users/${user.id}`, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ active: user.active }),
        });
      } catch (error) {
        console.error("Fout bij het bijwerken van de gebruikersstatus:", error);
      }
    },
    async deleteUser(userId) {
      try {
        const baseURL = window.location.hostname.includes("localhost")
            ? "http://localhost:8080"
            : "http://oege.ie.hva.nl:8000";

        await fetch(`${baseURL}/api/users/${userId}`, {
          method: "DELETE",
        });
        this.users = this.users.filter((user) => user.id !== userId);
        this.displayedUsers = this.users;
      } catch (error) {
        console.error("Fout bij het verwijderen van de gebruiker:", error);
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
