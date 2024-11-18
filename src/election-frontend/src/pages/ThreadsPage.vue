<template>
  <div class="homepage bg-gray-100 dark:bg-gray-900 font-sans">
    <!-- Section 1: Election Threads Overview -->
    <section class="threads-overview py-12 bg-gray-50 dark:bg-gray-800 rounded-lg shadow-lg mx-2 lg:mx-5 mb-10">
      <div class="max-w-7xl mx-auto flex flex-col lg:flex-row items-center lg:space-x-8">
        <div class="threads-text w-full lg:w-1/2">
          <h2 class="text-3xl lg:text-5xl font-extrabold text-gray-800 dark:text-gray-100 mb-6">Join the Conversation</h2>
          <p class="text-lg lg:text-xl text-gray-600 dark:text-gray-300 mb-4 leading-relaxed">
            Stay up-to-date with the latest election news and share your thoughts in real-time. Our election threads allow you to dive deep into the discussions around the most critical election topics.
          </p>
          <p class="text-lg lg:text-xl text-gray-600 dark:text-gray-300">
            Whether you're interested in the polls, candidate debates, or election results, we've got you covered. Join the conversation now!
          </p>
        </div>
        <img
            src="https://via.placeholder.com/400x250.png?text=Election+Threads"
            alt="Threads Placeholder"
            class="threads-image mt-8 lg:mt-0 w-full lg:w-1/2 rounded-lg shadow-md"
        />
      </div>
    </section>

    <!-- Section 2: Active Threads with Pagination -->
    <section class="active-threads py-10 bg-white dark:bg-gray-800 rounded-lg shadow-lg mx-2 lg:mx-5">
      <div class="max-w-7xl mx-auto">
        <h2 class="text-3xl lg:text-4xl font-extrabold text-center text-gray-800 dark:text-gray-100 mb-8">Active Election Threads</h2>
        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
          <div
              v-for="thread in paginatedThreads"
              :key="thread.id"
              class="thread-item p-6 bg-gray-100 dark:bg-gray-700 border border-gray-300 dark:border-gray-600 rounded-lg shadow-md hover:shadow-lg hover:bg-gray-50 dark:hover:bg-gray-600 transition-shadow duration-300"
          >
            <router-link
                :to="{ name: 'single-thread', params: { id: thread.id } }"
                class="text-lg font-semibold text-blue-600 dark:text-blue-400 hover:underline"
            >
              {{ thread.title }}
            </router-link>
            <p class="text-gray-600 dark:text-gray-300 mt-2 mb-4 line-clamp-3">
              {{ thread.content }}
            </p>
            <div class="text-sm text-gray-400 flex justify-between">
              <span>Created by: {{ thread.user.username }}</span>
              <span>{{ thread.date }}</span>
            </div>
          </div>
        </div>

        <!-- See More Button -->
        <div v-if="hasMoreThreads" class="text-center mt-8">
          <button
              class="cta-button px-6 sm:px-8 py-3 sm:py-4 text-white bg-blue-600 dark:bg-blue-800 hover:bg-blue-800 dark:hover:bg-blue-900 rounded-full transition-all shadow-lg transform hover:scale-105"
              @click="loadMoreThreads"
          >
            See More
          </button>
        </div>
      </div>
    </section>

    <!-- Section 3: Create New Thread Button -->
    <section class="text-center mt-12 mb-10">
      <button
          class="cta-button px-6 sm:px-8 py-3 sm:py-4 text-white bg-green-700 dark:bg-green-800 hover:bg-green-900 rounded-full transition-all shadow-lg transform hover:scale-105"
          @click="$router.push('/create-thread')"
      >
        Create New Thread
      </button>
    </section>
  </div>
</template>

<script>
export default {
  name: "ThreadsPage",
  data() {
    return {
      threads: [],
      visibleThreadsCount: 3,
    };
  },
  computed: {
    paginatedThreads() {
      // Display only the visible number of threads
      return this.threads.slice(0, this.visibleThreadsCount);
    },
    hasMoreThreads() {
      // Check if there are more threads to show
      return this.visibleThreadsCount < this.threads.length;
    },
  },
  methods: {
    async fetchThreads() {
      const response = await fetch("http://localhost:8080/api/threads");
      this.threads = await response.json();
    },
    loadMoreThreads() {
      // Increase the number of visible threads
      this.visibleThreadsCount += 3;
    },
  },
  mounted() {
    this.fetchThreads();
  },
};
</script>
