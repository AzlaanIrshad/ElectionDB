<template>
  <footer class="text-white bg-gray-800 dark:bg-gray-900 dark:text-white py-8 mt-auto border-t border-gray-300 dark:border-gray-700">
    <div class="container mx-auto text-center lg:text-left px-6">
      <div class="flex flex-col lg:flex-row justify-between items-center lg:items-start">
        <!-- Logo and About Section -->
        <div class="mb-6 lg:mb-0">
          <p class="text-xl font-semibold">Electiondb</p>
          <p class="text-sm mt-2 text-white dark:text-gray-400">Your hub for real-time election results, updates, and discussions.</p>
        </div>

        <!-- Navigation Links -->
        <div class="flex flex-col lg:flex-row items-center lg:items-start lg:space-x-8">
          <router-link to="/" class="text-white dark:text-gray-300 hover:text-gray-900 dark:hover:text-white mb-4 lg:mb-0">Home</router-link>
          <router-link to="/faq" class="text-white dark:text-gray-300 hover:text-gray-900 dark:hover:text-white mb-4 lg:mb-0">FAQ</router-link>
          <router-link to="/about" class="text-white dark:text-gray-300 hover:text-gray-900 dark:hover:text-white mb-4 lg:mb-0">About Us</router-link>
        </div>
      </div>

      <!-- Copyright -->
      <div class="text-center text-white dark:text-gray-400 text-sm mt-6">
        &copy; 2024 Electiondb. All rights reserved.
      </div>
    </div>
  </footer>
</template>

<script>
export default {
  name: "FooterComponent",
};
</script>
