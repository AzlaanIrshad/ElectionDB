<template>
  <div class="homepage bg-gray-100 font-sans min-h-screen py-8">
    <!-- Section 1: Create Thread Section -->
    <section class="mx-4 sm:mx-6 lg:mx-10">
      <!-- Threads Section -->
      <div class="threads py-12 bg-white rounded-lg shadow-lg mx-auto mb-10 max-w-4xl">
        <h2 class="text-3xl lg:text-4xl text-center mb-8 font-extrabold text-gray-800">
          Create a new election thread!
        </h2>
        <div class="threads-content flex flex-col md:flex-row items-center gap-8 px-4 sm:px-6 lg:px-8">
          <form class="w-full space-y-6">
            <input
                type="text"
                id="title"
                v-model="title"
                placeholder="Thread Title"
                class="w-full p-4 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-blue-300 text-gray-700"
            />
            <textarea
                id="body"
                v-model="body"
                placeholder="Thread Body"
                class="w-full p-4 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-blue-300 text-gray-700 h-32 resize-none"
            ></textarea>
            <input
                type="text"
                id="category"
                v-model="category"
                placeholder="Category"
                class="w-full p-4 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-blue-300 text-gray-700"
            />
          </form>
        </div>
      </div>
    </section>

    <!-- Make new thread button -->
    <section class="admin-panel text-center mb-10">
      <button
          class="cta-button px-8 py-4 text-white bg-green-600 hover:bg-green-700 focus:ring focus:ring-green-300 rounded-full transition-all shadow-lg transform hover:scale-105"
          @click="createThread"
      >
        Create Thread
      </button>
    </section>
  </div>
</template>

<script>
export default {
  name: "CreateThreadPage",
  data() {
    return {
      title: '',
      body: '',
      category: '',
      date: '',
      user: '',
    };
  },
  methods: {
    async createThread() {
      try {
        // Create a dummy user object for now
        const dummyUser = {
          id: 1, // Use a static ID for the dummy user
          username: 'googoo',
          email: 'googoo@example.com',
          password: 'password',
          role: 'ADMIN'
        };

        // Create a new Date object
        const now = new Date();

        // Format the date to "YYYY-MM-DD HH:mm"
        const formattedDate = `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, '0')}-
        ${String(now.getDate()).padStart(2, '0')} ${String(now.getHours()).padStart(2, '0')}
        :${String(now.getMinutes()).padStart(2, '0')}`

        const threadData = {
          title: this.title,
          body: this.body,
          category: this.category,
          date: formattedDate,
          user: dummyUser,
        };
        const response = await fetch('http://localhost:8080/api/threads', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
        body: JSON.stringify(threadData),
        });


        if (response.ok) {
          this.$router.push('/threads');
        } else {
          console.error('Error creating thread:', response.statusText);
        }
      } catch (error) {
        console.error('Error creating thread:', error);
      }
    },
  },
};
</script>
