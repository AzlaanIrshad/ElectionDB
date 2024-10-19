<template>
  <div class="homepage bg-gray-100 font-sans">
    <!-- Section 1-->
    <section>
      <!-- Threads Section -->
      <div class="threads py-12 bg-white rounded-lg shadow-lg mx-2 lg:mx-5 mb-10">
        <h2 class="text-3xl lg:text-4xl text-center mb-10 font-extrabold text-gray-800">Election Threads</h2>
        <div class="threads-content flex flex-col md:flex-row items-center gap-8 mx-4 sm:mx-6 lg:mx-10">
          <img
              src="https://imgs.search.brave.com/QFQM7Ro7Dp252Ne2DY4GK0rbLWu2lPYD37gV1VJDVLg/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9jZG4u/YnJpdGFubmljYS5j/b20vNTYvMTkzNTU2/LTEzMS03RDNEMUVE/RC9FbGVjdGlvbi0t/LUJ1dHRvbi1Wb3Rl/LXN0cmlwZXMtcG9s/aXRpY3MtY2FtcGFp/Z24uanBnP3c9MjAw/Jmg9MjAwJmM9Y3Jv/cA"
              alt="Threads Image"
              class="threads-image w-full md:w-2/6 mr-28 h-40 sm:h-60 object-cover rounded-lg shadow-md"
          />
          <div class="threads-text w-full md:w-3/5">
            <h3 class="text-2xl sm:text-3xl font-extrabold mb-5 text-gray-800">Election Threads</h3>
            <p class="mb-4 text-base sm:text-lg text-gray-600 leading-relaxed">Election threads are a great way to discuss the latest news, polls, and predictions. Join the conversation and share your thoughts on the upcoming elections.</p>
          </div>
        </div>
      </div>
    </section>

    <!-- Section 2 -->
    <section class="categories py-10 mb-10 bg-white rounded-lg shadow-lg mt-10 mx-2 lg:mx-5">
      <!-- Looping through a list of threads -->
      <h2 class="text-3xl lg:text-4xl text-center mb-8 font-extrabold text-gray-800">Active Threads</h2>
      <div class="threads-list space-y-6 mx-4 sm:mx-6 lg:mx-10">
        <div v-for="thread in threads" :key="thread.id" class="thread-item p-4 bg-gray-50 border rounded-lg shadow-md hover:shadow-lg transition-shadow duration-300">
          <!--          <router-link :to="{ name: 'ThreadDetail', params: { id: thread.id } }" class="text-lg font-semibold text-gray-800 hover:underline">-->
          {{ thread.title }}
          <!--          </router-link>-->
          <p class="text-gray-600">{{ thread.content }}</p>
          <p class="text-sm text-gray-400">Created by: {{ thread.author }} on {{ thread.createdAt }}</p>
        </div>
      </div>
    </section>

    <!-- Make new thread button -->
    <section class="admin-panel text-center mb-10">
      <button
          class="cta-button px-6 sm:px-8 py-3 sm:py-4 text-white bg-green-700 hover:bg-green-900 rounded-full transition-all shadow-lg transform hover:scale-105"
          @click="$router.push('/threads/new')"
      >
        Make New Thread
      </button>
    </section>
  </div>
</template>

<script>
export default {
  name: "ThreadsPage",
  data() {
    return {
      // Currently hard coded will be fetched from the backend
      threads: [
        {
          id: 1,
          title: 'First Thread',
          content: 'This is the content of the first thread.',
          author: 'User1',
          createdAt: '2024-10-19',
        },
        {
          id: 2,
          title: 'Second Thread',
          content: 'This is the content of the second thread.',
          author: 'User2',
          createdAt: '2024-10-18',
        },
        {
          id: 3,
          title: 'Third Thread',
          content: 'This is the content of the third thread.',
          author: 'User3',
          createdAt: '2024-10-17',
        },
        {
          id: 4,
          title: 'Fourth Thread',
          content: 'This is the content of the fourth thread.',
          author: 'User4',
          createdAt: '2024-10-17',
        },
      ],
    };
  },
};
</script>