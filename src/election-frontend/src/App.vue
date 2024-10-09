<template>
  <div id="app">
    <!-- Header Section -->
    <HeaderComponent />

    <!-- Main Content -->
    <main class="mt-8">
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
