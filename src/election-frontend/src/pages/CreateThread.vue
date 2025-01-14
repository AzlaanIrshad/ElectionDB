<template>
  <div class="homepage bg-gray-100 dark:bg-gray-900 font-sans min-h-screen py-8">
    <section class="mx-4 sm:mx-6 lg:mx-10">
      <div class="threads py-12 bg-white dark:bg-gray-800 rounded-lg shadow-lg mx-auto mb-10 max-w-4xl">
        <h2 class="text-3xl lg:text-4xl text-center mb-8 font-extrabold text-gray-800 dark:text-gray-100">
          Maak een nieuwe verkiezingsdraad!
        </h2>
        <div class="threads-content flex flex-col md:flex-row items-center gap-8 px-4 sm:px-6 lg:px-8">
          <form class="w-full space-y-6" @submit.prevent="createThread">
            <div>
              <input
                  type="text"
                  id="title"
                  v-model="title"
                  placeholder="Titel van de draad"
                  class="w-full p-4 border border-gray-300 dark:border-gray-600 rounded-md focus:outline-none focus:ring focus:ring-blue-300 dark:focus:ring-blue-500 text-gray-700 dark:text-gray-200 dark:bg-gray-700"
                  :class="{ 'border-red-500 dark:border-red-500': titleError }"
              />
              <p v-if="titleError" class="text-red-500 text-sm mt-1">{{ titleError }}</p>
            </div>

            <div>
              <textarea
                  id="body"
                  v-model="body"
                  placeholder="Inhoud van de draad"
                  class="w-full p-4 border border-gray-300 dark:border-gray-600 rounded-md focus:outline-none focus:ring focus:ring-blue-300 dark:focus:ring-blue-500 text-gray-700 dark:text-gray-200 dark:bg-gray-700 h-32 resize-none"
                  :class="{ 'border-red-500 dark:border-red-500': bodyError }"
              ></textarea>
              <p v-if="bodyError" class="text-red-500 text-sm mt-1">{{ bodyError }}</p>
            </div>

            <div>
              <input
                  type="text"
                  id="category"
                  v-model="categoryInput"
                  placeholder="Categories (comma-separated)"
                  class="w-full p-4 border border-gray-300 dark:border-gray-600 rounded-md focus:outline-none focus:ring focus:ring-blue-300 dark:focus:ring-blue-500 text-gray-700 dark:text-gray-200 dark:bg-gray-700"
                  :class="{ 'border-red-500 dark:border-red-500': categoryError }"
              />
              <p v-if="categoryError" class="text-red-500 text-sm mt-1">{{ categoryError }}</p>
            </div>

            <p v-if="titleError || bodyError || categoryError" class="text-red-500 text-sm mt-4">
              Vul alle velden in.
            </p>
          </form>
        </div>
      </div>
    </section>

    <section class="admin-panel text-center mb-10">
      <div class="flex justify-center gap-4">
        <button
            class="cta-button px-8 py-4 text-white bg-green-600 dark:bg-green-700 hover:bg-green-700 dark:hover:bg-green-800 focus:ring focus:ring-green-300 dark:focus:ring-green-500 rounded-full transition-all shadow-lg transform hover:scale-105"
            :disabled="!isFormValid"
            @click="createThread"
        >
          Draad Aanmaken
        </button>
        <button
            class="cta-button px-8 py-4 text-white bg-gray-600 dark:bg-gray-700 hover:bg-gray-700 dark:hover:bg-gray-800 focus:ring focus:ring-gray-300 dark:focus:ring-gray-500 rounded-full transition-all shadow-lg transform hover:scale-105"
            @click="goBack"
        >
          Ga Terug
        </button>
      </div>
    </section>
  </div>
</template>

<script>
import {threadService} from '../services/ThreadService';

export default {
  name: "CreateThreadPage",
  data() {
    return {
      title: '',
      body: '',
      categoryInput: '',
      titleError: '',
      bodyError: '',
      categoryError: '',
      isLoggedIn: false,
      userEmail: null,
    };
  },
  computed: {
    isFormValid() {
      return this.title !== '' && this.body !== '' && this.categoryInput !== '';
    },
  },
  methods: {
    // Method to check authentication status
    checkAuthStatus() {
      const token = localStorage.getItem("token");
      if (token) {
        try {
          const payload = JSON.parse(atob(token.split(".")[1]));
          console.log("Decoded token payload:", payload);
          this.userEmail = payload.sub;
        } catch (error) {
          console.error("Error decoding token payload:", error);
          this.isLoggedIn = false;
        }
      } else {
        this.isLoggedIn = false;
      }
    },

    // Validate form inputs
    validateForm() {
      this.titleError = this.bodyError = this.categoryError = '';

      if (this.title === '') {
        this.titleError = 'Titel is verplicht.';
      }

      if (this.body === '') {
        this.bodyError = 'Inhoud is verplicht.';
      }

      if (this.categoryInput === '') {
        this.categoryError = 'Categorie is verplicht.';
      }

      return !this.titleError && !this.bodyError && !this.categoryError;
    },

    // Method to create a thread
    async createThread() {
      this.checkAuthStatus(); // Check authentication status

      if (!this.isLoggedIn) {
        console.error("User is not logged in");
        return;
      }

      if (!this.validateForm()) {
        return;
      }

      try {
        const now = new Date();
        const formattedDate = `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, '0')}-`
            + `${String(now.getDate()).padStart(2, '0')} ${String(now.getHours()).padStart(2, '0')}:`
            + `${String(now.getMinutes()).padStart(2, '0')}`;

        const categories = this.categoryInput
            .split(",")
            .map((cat) => cat.trim())
            .filter((cat) => cat !== ""); // Remove empty categories

        if (categories.length === 0) {
          this.categoryError = 'Categorie is verplicht.';
          return;
        }

        const threadData = {
          title: this.title,
          body: this.body,
          categories: categories, // Send as array
          date: formattedDate,
          email: this.userEmail,
        };

        const response = await threadService.createThread(threadData);

        if (response.ok) {
          this.$router.push('/threads');
        } else {
          console.error('Fout bij het aanmaken van de draad:', response.statusText);
        }
      } catch (error) {
        console.error('Fout bij het aanmaken van de draad:', error);
      }
    },

    goBack() {
      this.$router.go(-1);
    }
  },
  mounted() {
    this.checkAuthStatus(); // Check auth status when the component is mounted
  }
};

</script>
