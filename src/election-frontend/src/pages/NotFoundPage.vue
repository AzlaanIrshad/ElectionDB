<template>
  <div class="min-h-screen flex flex-col items-center justify-center bg-gray-100 dark:bg-gray-900">
    <h1 class="text-6xl font-bold text-red-600 mb-4 dark:text-red-400">404</h1>
    <p class="text-xl text-gray-700 mb-8 dark:text-gray-300">
      Oeps! De pagina die je zoekt, bestaat niet.
    </p>
    <router-link
        to="/"
        class="px-4 py-2 bg-blue-600 text-white rounded-lg shadow hover:bg-blue-700 dark:bg-blue-700 dark:hover:bg-blue-800 dark:text-gray-200 transition duration-200"
    >
      Ga terug naar de Homepagina
    </router-link>
  </div>
</template>

<script>
export default {
  name: "NotFoundPage",
};
</script>