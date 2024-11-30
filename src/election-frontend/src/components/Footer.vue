<template>
  <footer class="bg-gray-800 rounded-lg shadow dark:bg-gray-900 py-8 border-t border-gray-300 dark:border-gray-700">
    <div class="w-full max-w-screen-xl mx-auto p-4">
      <div class="sm:flex sm:items-center sm:justify-between">
        <!-- Logo en Beschrijving -->
        <a href="#" class="flex items-center mb-4 sm:mb-0 space-x-3">
          <span class="self-center text-2xl font-semibold whitespace-nowrap text-white">VerkiezingsDB</span>
        </a>
        <!-- Navigatielinks -->
        <ul class="flex flex-wrap items-center mb-6 text-sm font-medium text-gray-400 sm:mb-0">
          <li>
            <router-link to="/" class="hover:underline me-4 md:me-6 text-gray-400 hover:text-white">Home</router-link>
          </li>
          <li>
            <router-link to="/faq" class="hover:underline me-4 md:me-6 text-gray-400 hover:text-white">FAQ</router-link>
          </li>
          <li>
            <router-link to="/about" class="hover:underline me-4 md:me-6 text-gray-400 hover:text-white">Over Ons</router-link>
          </li>
          <li>
            <router-link to="/dashboard" class="hover:underline me-4 md:me-6 text-gray-400 hover:text-white">Dashboard</router-link>
          </li>
          <li>
            <router-link to="/election-statistiek" class="hover:underline text-gray-400 hover:text-white">Verkiezingsstatistiek</router-link>
          </li>
        </ul>
      </div>
      <hr class="my-6 border-gray-700 sm:mx-auto" />
      <span class="block text-sm text-gray-400 text-center">&copy; 2024 <span class="font-semibold">VerkiezingsDB</span>. Alle Rechten Voorbehouden.</span>
    </div>
  </footer>
</template>

<script>
export default {
  name: "FooterComponent",
};
</script>
