<template>
  <div id="app">
    <!-- Header Section -->
    <header class="bg-gray-900 py-6 text-white shadow-lg">
      <div class="container mx-auto flex justify-between items-center px-6">
        <div class="logo text-3xl font-bold">Electiondb</div>
      </div>
    </header>

    <!-- Main Content -->
    <main class="mt-8">
      <HomePage />
    </main>

    <!-- Footer Section -->
    <footer class="bg-gray-900 py-8 mt-16 text-white">
      <div class="container mx-auto text-center">
        <p class="text-lg font-semibold">&copy; 2024 Electiondb. All rights reserved.</p>
      </div>
    </footer>
  </div>
</template>

<script>
import HomePage from "./components/HomePage.vue";

export default {
  name: "App",
  components: {
    HomePage,
  },
};
</script>