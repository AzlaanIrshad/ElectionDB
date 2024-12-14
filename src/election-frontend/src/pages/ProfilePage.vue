<template>
  <div class="flex flex-col items-center p-6 bg-gray-100 min-h-screen">
    <h1 class="text-2xl font-semibold text-gray-800 mb-4">My Profile</h1>
    <div class="w-full max-w-4xl bg-white shadow-lg rounded-lg p-6">
      <div class="mb-4">
        <h2 class="text-xl font-medium text-gray-700">User Information</h2>
        <p class="text-gray-500">Here are your profile details.</p>
      </div>

      <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
        <div class="flex flex-col">
          <label class="text-gray-700 font-medium mb-1">Username</label>
          <input
              type="text"
              v-model="user.username"
              class="px-3 py-2 border rounded-md border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500"
              disabled
          />
        </div>
        <div class="flex flex-col">
          <label class="text-gray-700 font-medium mb-1">Email</label>
          <input
              type="email"
              v-model="user.email"
              class="px-3 py-2 border rounded-md border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500"
              disabled
          />
        </div>
      </div>

      <div class="mt-6 flex justify-center">
        <button
            @click="toggleActiveStatus"
            class="px-4 py-2 bg-blue-500 text-white font-semibold rounded-lg hover:bg-blue-600 transition duration-300"
        >
          {{ user.active ? 'Deactivate' : 'Activate' }} Account
        </button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      user: {
        username: '',
        email: '',
        active: true,
      },
    };
  },
  mounted() {
    this.fetchUserProfile();
  },
  methods: {
    async fetchUserProfile() {
      try {
        const response = await fetch('/users');
        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }
        const users = await response.json();
        const userId = this.$route.params.id;
        const user = users.find(u => u.id === parseInt(userId, 10));

        if (user) {
          this.user = user;
        } else {
          console.error(`User with ID ${userId} not found`);
        }
      } catch (error) {
        console.error("Error fetching user profile:", error);
      }
    },
    async toggleActiveStatus() {
      try {
        const response = await fetch(`/api/users/${this.user.id}`, {
          method: 'POST',
        });

        if (response.ok) {
          const data = await response.json();
          this.user = data;
          alert(`Account ${this.user.active ? 'activated' : 'deactivated'} successfully`);
        } else {
          console.error("Failed to toggle active status");
        }
      } catch (error) {
        console.error("Error toggling active status:", error);
      }
    },
  },
};
</script>

<style scoped>

</style>
