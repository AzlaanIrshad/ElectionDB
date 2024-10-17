<template>
  <header class="bg-gray-900 py-6 text-white shadow-lg">
    <div class="container mx-auto flex justify-between items-center px-6">
      <div class="logo text-3xl font-bold">Electiondb</div>
      <router-link to="/login" class="text-lg text-white bg-blue-600 hover:bg-blue-500 rounded px-4 py-2 transition duration-300">
        Login
      </router-link>
    </div>
  </header>
</template>

<script>
export default {
  name: "HeaderComponent",
};
</script>