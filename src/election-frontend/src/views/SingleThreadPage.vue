<template>
  <!-- Single Thread -->
  <div class="single-thread max-w-4xl mx-auto px-4 py-8 bg-gray-200 rounded-lg shadow-md">
    <h1 class="text-3xl font-bold mb-6 text-gray-800 text-center">{{ thread.title }}</h1>

    <div v-if="loading" class="text-center text-gray-500">
      <p>Loading thread...</p>
    </div>

    <div v-if="!loading">
      <h2 class="text-2xl font-semibold mb-4 text-gray-800">{{ thread.title }}</h2>
      <p class="text-gray-600 mb-4">{{ thread.body }}</p>
      <p class="text-sm text-gray-500 mb-2">Posted by: <span class="font-medium text-gray-700">{{ thread.user.username }}</span></p>
      <p class="text-sm text-gray-500">Posted on: <span class="font-medium text-gray-700">{{ thread.date }}</span></p>
    </div>

    <!-- Thread Comments -->
    <div class="comments mt-8">
      <h2 class="text-2xl font-semibold mb-4 text-gray-800">Comments</h2>
      <div class="comment-list space-y-4">
        <div
            class="comment-item p-4 bg-gray-50 border rounded-lg shadow-md"
            v-for="comment in comments"
            :key="comment.id"
        >
          <p class="text-gray-600">{{ comment.body }}</p>
          <p class="text-sm text-gray-400">By: <span class="font-medium text-gray-700">{{ comment.user.username }}</span> on {{ comment.date }}</p>
        </div>
      </div>
    </div>

    <!-- Back to Threads Button -->
    <div class="mt-6 text-center">
      <router-link to="/threads" class="px-6 py-2 bg-green-700 text-white rounded-md hover:bg-green-900 transition duration-200">Back to Threads</router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: "SingleThread",
  data() {
    return {
      thread: {},
      comments: [],
      loading: true,
    };
  },
  methods: {
    async fetchThread() {
      const response = await fetch(`http://localhost:8080/api/threads/${this.$route.params.id}`);
      this.thread = await response.json();
      this.comments = this.thread.comments || [];
      this.loading = false;
    },
    async fetchComments() {
      const response = await fetch(`http://localhost:8080/api/threads/${this.$route.params.id}/comments`);
      this.comments = await response.json();
    },
  },
  created() {
    this.fetchThread();
    this.fetchComments();
  },
};
</script>