<template>
  <div class="create-comment-container max-w-4xl mx-auto py-12 px-6 bg-white dark:bg-gray-700 rounded-lg shadow-xl">
    <h1 class="text-3xl font-extrabold text-gray-800 dark:text-gray-100 mb-8 text-center">Maak een Nieuwe Reactie</h1>

    <div>
      <form @submit.prevent="createComment" class="space-y-8">
        <div>
          <label for="comment" class="block text-lg font-semibold text-gray-700 dark:text-gray-200 mb-3">Reactie:</label>
          <textarea
              v-model="body"
              id="comment"
              name="comment"
              rows="4"
              class="w-full p-4 border border-gray-300 dark:border-gray-600 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-300 dark:focus:ring-blue-500 focus:border-transparent transition duration-300 bg-gray-50 dark:bg-gray-700 text-gray-800 dark:text-gray-100"
              placeholder="Schrijf je reactie..."></textarea>
        </div>
        <div class="text-center">
          <button
              class="w-full sm:w-auto px-8 py-4 text-white bg-blue-600 dark:bg-blue-700 hover:bg-blue-700 dark:hover:bg-blue-600 focus:ring-4 focus:ring-blue-300 dark:focus:ring-blue-500 rounded-full transition-all shadow-md transform hover:scale-105"
              type="submit">
            Reactie Indienen
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import { threadService } from '../services/ThreadService.js';

export default {
  name: "CreateCommentComponent",
  data() {
    return {
      body: '',
      userEmail: '',
    };
  },
  methods: {

    // This method is called to check the authentication status
    checkAuthStatus() {
      const token = localStorage.getItem("token");
      if (token) {
        try {
          const payload = JSON.parse(atob(token.split(".")[1]));
          this.userEmail = payload.sub;
        } catch (error) {
          console.error("Error decoding token payload:", error);
        }
      }
    },

    // This method is used to create a comment
    async createComment() {
      if (!this.userEmail) {
        console.error('User is not authenticated.');
        return;
      }

      const threadId = this.$route.params.id;

      const now = new Date();
      const formattedDate = `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, '0')}-`
          + `${String(now.getDate()).padStart(2, '0')} ${String(now.getHours()).padStart(2, '0')}:`
          + `${String(now.getMinutes()).padStart(2, '0')}`;

      const commentData = {
        body: this.body,
        date: formattedDate,
        email: this.userEmail,
      };

      try {
        const response = await threadService.createComment(threadId, commentData);

        if (response.ok) {
          console.log('Reactie aangemaakt:', commentData);
          this.$router.go(0);
        } else {
          console.error('Fout bij het maken van de reactie:', response.statusText);
        }
      } catch (error) {
        console.error('Fout:', error);
      }
    },
  },
  mounted() {
    this.checkAuthStatus();
  },
}
</script>
