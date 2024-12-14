<template>
  <!-- Enkel Draad -->
  <div class="single-thread max-w-4xl mx-auto px-4 py-8 bg-gray-200 dark:bg-gray-800 rounded-lg shadow-md">
    <h1 class="text-3xl font-bold mb-6 text-gray-800 dark:text-gray-100 text-center">{{ thread.title }}</h1>

    <div v-if="loading" class="text-center text-gray-500 dark:text-gray-400">
      <p>Draad laden...</p>
    </div>

    <div v-if="!loading">
      <h2 class="text-2xl font-semibold mb-4 text-gray-800 dark:text-gray-100">{{ thread.title }}</h2>
      <p class="text-gray-600 dark:text-gray-300 mb-4">{{ thread.body }}</p>
      <p class="text-sm text-gray-500 dark:text-gray-400 mb-2">
        Geplaatst door: <span class="font-medium text-gray-700 dark:text-gray-300">{{ thread.user.username }}</span>
      </p>
      <p class="text-sm text-gray-500 dark:text-gray-400">
        Geplaatst op: <span class="font-medium text-gray-700 dark:text-gray-300">{{ thread.date }}</span>
      </p>
    </div>

    <!-- Reacties op Draad -->
    <div class="comments mt-8">
      <h2 class="text-2xl font-semibold mb-4 text-gray-800 dark:text-gray-100">Reacties</h2>
      <div class="comment-list space-y-4">
        <div
            class="comment-item p-4 bg-gray-50 dark:bg-gray-700 border rounded-lg shadow-md"
            v-for="comment in comments"
            :key="comment.id"
        >
          <p class="text-gray-600 dark:text-gray-300">{{ comment.body }}</p>
          <p class="text-sm text-gray-400 dark:text-gray-400">
            Door: <span class="font-medium text-gray-700 dark:text-gray-300">{{ comment.user.username }}</span> op {{ comment.date }}
          </p>
        </div>
        <CreateCommentComponent/>
      </div>
    </div>

    <!-- Terug naar Draadjes Knop -->
    <div class="mt-6 text-center">
      <router-link to="/threads" class="px-6 py-2 bg-green-700 dark:bg-green-800 text-white rounded-md hover:bg-green-900 dark:hover:bg-green-700 transition duration-200">Terug naar Threads</router-link>
    </div>
  </div>
</template>

<script>
import { threadService } from "../services/threadService";
import CreateCommentComponent from "../components/CreateComment.vue";

export default {
  name: "SingleThread",
  components: {
    CreateCommentComponent,
  },
  data() {
    return {
      thread: {},
      comments: [],
      loading: true,
    };
  },
  methods: {
    async fetchThread() {
      try {
        const data = await threadService.fetchThreadData(this.$route.params.id);
        this.thread = data;
      } catch (err) {
        console.error(err);
      } finally {
        this.loading = false;
      }
    },
    async fetchComments() {
      try {
        const data = await threadService.fetchComments(this.$route.params.id);
        this.comments = data;
      } catch (err) {
        console.error(err);
      }
    },
  },
  created() {
    this.fetchThread();
    this.fetchComments();
  },
};
</script>
