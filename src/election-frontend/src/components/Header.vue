<template>
  <header class="bg-gray-900 py-4 md:py-6 text-white shadow-lg">
    <div class="container mx-auto flex justify-between items-center px-4 md:px-6">
      <router-link to="/" class="logo text-2xl md:text-3xl font-bold">
        Electiondb
      </router-link>
      <div class="flex items-center">
        <router-link
            to="/login"
            class="text-base md:text-lg text-white bg-blue-600 hover:bg-blue-500 rounded-full px-4 md:px-6 py-2 md:py-3 transition duration-300 mr-4"
        >
          Login
        </router-link>
        <button
            class="text-base md:text-lg text-white bg-blue-600 hover:bg-blue-500 rounded-full px-4 md:px-6 py-2 md:py-3 transition duration-300"
            @click="$router.push('/admin')"
        >
          Go to Admin Panel
        </button>
      </div>
    </div>
  </header>
</template>

<script>
export default {
  name: "HeaderComponent",
};
</script>
