<template>
  <div id="app">
    <!-- Header Section -->
    <HeaderComponent />

    <!-- Main Content -->
    <main class="mt-8">
      <HomePage />
    </main>

    <!-- Footer Section -->
    <FooterComponent />
  </div>
</template>

<script>
import HomePage from "./components/HomePage.vue";
import HeaderComponent from "./components/Header.vue";
import FooterComponent from "./components/Footer.vue";

export default {
  name: "App",
  components: {
    HomePage,
    HeaderComponent,
    FooterComponent,
  },
};
</script>