<template>
  <div class="create-comment-container max-w-4xl mx-auto py-8 px-4 bg-white rounded-lg shadow-lg">
    <h1 class="text-3xl font-bold text-gray-800 mb-6 text-center">Create Comment</h1>

    <div>
      <form @submit.prevent="createComment" class="space-y-6">
        <!-- Comment Body -->
        <div>
          <label for="comment" class="block text-lg font-medium text-gray-700 mb-2">Comment:</label>
          <textarea
              v-model="body"
              id="comment"
              name="comment"
              rows="4"
              class="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-blue-300"
              placeholder="Write your comment..."
          ></textarea>
        </div>

        <!-- Category -->
        <div>
          <label for="category" class="block text-lg font-medium text-gray-700 mb-2">Category:</label>
          <input
              v-model="category"
              id="category"
              name="category"
              type="string"
              class="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-blue-300"
              placeholder="Enter category..."
          />
        </div>

        <!-- Submit Button -->
        <button
            class="cta-button w-full px-6 py-3 text-white bg-green-700 hover:bg-green-900 rounded-full transition-all shadow-lg transform hover:scale-105"
            type="submit"
        >
          Create Comment
        </button>
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
      date: '',
      user: '',
      category: '',
    };
  },
  methods: {
    async createComment() {
      try {
        const dummyUser = {
          id: 1, // Use a static ID for the dummy user
          username: 'googoo',
          email: 'googoo@example.com',
          password: 'password',
          role: 'ADMIN'
        };

        const commentData = {
          body: this.body,
          date: new Date(),
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
    },
  },
}
</script>
