<template>
  <div class="homepage bg-gray-100 font-sans">
    <!-- Section 1-->
    <section>
      <!-- Threads Section -->
      <div class="threads py-12 bg-gray-200 rounded-lg shadow-lg mx-2 lg:mx-5 mb-10">
        <h2 class="text-3xl lg:text-4xl text-center mb-10 font-extrabold text-gray-800">Create a new election thread!</h2>
        <div class="threads-content flex flex-col md:flex-row items-center gap-8 mx-4 sm:mx-6 lg:mx-10">
          <form>
            <input
                type="text"
                id="title"
                v-model="title"
                placeholder="Thread Title"
                class="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-blue-300"
            />
            <textarea
                id="body"
                v-model="body"
                placeholder="Thread Body"
                class="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-blue-300"
            ></textarea>
            <input type="string" id="category" v-model="category" placeholder="Category" class="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-blue-300" />
          </form>
        </div>
      </div>
    </section>

    <!-- Make new thread button -->
    <section class="admin-panel text-center mb-10">
      <button
          class="cta-button px-6 sm:px-8 py-3 sm:py-4 text-white bg-green-700 hover:bg-green-900 rounded-full transition-all shadow-lg transform hover:scale-105"
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

        const threadData = {
          title: this.title,
          body: this.body,
          category: this.category,
          date: new Date(),
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
