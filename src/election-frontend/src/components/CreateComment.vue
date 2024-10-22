<template>
  <div class="create-comment-container max-w-4xl mx-auto py-12 px-6 bg-white rounded-lg shadow-xl">
    <h1 class="text-3xl font-extrabold text-gray-800 mb-8 text-center">Create a New Comment</h1>

    <div>
      <form @submit.prevent="createComment" class="space-y-8">
        <div>
          <label for="comment" class="block text-lg font-semibold text-gray-700 mb-3">Comment:</label>
          <textarea
              v-model="body"
              id="comment"
              name="comment"
              rows="4"
              class="w-full p-4 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-300 focus:border-transparent transition duration-300"
              placeholder="Write your comment..."
          ></textarea>
        </div>

        <div>
          <label for="category" class="block text-lg font-semibold text-gray-700 mb-3">Category:</label>
          <input
              v-model="category"
              id="category"
              name="category"
              type="text"
              class="w-full p-4 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-300 focus:border-transparent transition duration-300"
              placeholder="Enter category..."
          />
        </div>

        <div class="text-center">
          <button
              class="w-full sm:w-auto px-8 py-4 text-white bg-blue-600 hover:bg-blue-700 focus:ring-4 focus:ring-blue-300 rounded-full transition-all shadow-md transform hover:scale-105"
              type="submit"
          >
            Submit Comment
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  name: "CreateCommentComponent",
  data() {
    return {
      body: '',
      category: '',
    };
  },
  methods: {
    async createComment() {
      try {
        const dummyUser = {
          id: 1,
          username: 'googoo',
          email: 'googoo@example.com',
          password: 'password',
          role: 'ADMIN'
        };

        const now = new Date();
        const formattedDate = `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, '0')}-${String(now.getDate()).padStart(2, '0')} ${String(now.getHours()).padStart(2, '0')}:${String(now.getMinutes()).padStart(2, '0')}`;

        const commentData = {
          body: this.body,
          date: formattedDate,
          category: this.category,
          user: dummyUser,
        };

        const response = await fetch(`http://localhost:8080/api/threads/${this.$route.params.id}/comments`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(commentData),
        });

        if (!response.ok) {
          console.error('Error creating comment:', response.statusText);
          return;
        }

        console.log('Comment created:', commentData);
      } catch (error) {
        console.error('Error:', error);
      }
      this.$router.go();
    },
  },
}
</script>
