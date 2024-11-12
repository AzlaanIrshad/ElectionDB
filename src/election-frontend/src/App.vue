<template>
  <div id="app" class="flex flex-col min-h-screen bg-gray-100 text-gray-900 dark:bg-gray-900 dark:text-gray-100">
    <!-- Header Section -->
    <HeaderComponent />

    <!-- Main Content -->
    <main class="flex-grow my-8">
      <router-view />
    </main>

    <!-- Footer Section -->
    <FooterComponent />
  </div>
</template>

<script>
import HeaderComponent from "./components/Header.vue";
import FooterComponent from "./components/Footer.vue";

export default {
  name: "App",
  components: {
    HeaderComponent,
    FooterComponent,
  },
};
</script>

<style>
#app {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

main {
  flex-grow: 1;
}
</style>
