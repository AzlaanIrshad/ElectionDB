<template>
  <div class="homepage bg-gray-100 dark:bg-gray-900 font-sans">
    <!-- Sectie 1: Overzicht van Discussies -->
    <section class="threads-overview py-12 bg-gray-50 dark:bg-gray-800 rounded-lg shadow-lg mx-2 lg:mx-5 mb-10">
      <div class="max-w-7xl mx-auto flex flex-col lg:flex-row items-center lg:space-x-8">
        <div class="threads-text w-full lg:w-1/2">
          <h2 class="text-3xl lg:text-5xl font-extrabold text-gray-800 dark:text-gray-100 mb-6">Doe mee aan de discussies</h2>
          <p class="text-lg lg:text-xl text-gray-600 dark:text-gray-300 mb-4 leading-relaxed">
            Blijf op de hoogte van het laatste verkiezingsnieuws en deel je mening in real-time. Onze discussies geven je de kans om diepgaand te discussiëren over de belangrijkste verkiezingsthema's.
          </p>
          <p class="text-lg lg:text-xl text-gray-600 dark:text-gray-300">
            Of je nu geïnteresseerd bent in de peilingen, debatten tussen kandidaten of verkiezingsuitslagen, wij hebben het allemaal. Doe nu mee aan de discussie!
          </p>
        </div>
        <img
            src="https://r.mobirisesite.com/963736/assets/images/g84b669d2938ac3ebdc84de8e5a10-h_m48kfvnb.jpg"
            alt="Draadjes Placeholder"
            class="threads-image mt-8 lg:mt-0 w-full lg:w-1/2 rounded-lg shadow-md"
        />
      </div>
    </section>

    <!-- Sectie 2: Actieve Draadjes met Paginering -->
    <section class="active-threads py-10 bg-white dark:bg-gray-800 rounded-lg shadow-lg mx-2 lg:mx-5">
      <div class="max-w-7xl mx-auto">
        <h2 class="text-3xl lg:text-4xl font-extrabold text-center text-gray-800 dark:text-gray-100 mb-8">Actieve Discussies</h2>
        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
          <div
              v-for="thread in paginatedThreads"
              :key="thread.id"
              class="thread-item p-5 bg-gray-100 dark:bg-gray-700 border border-gray-300 dark:border-gray-600 rounded-lg shadow-md hover:shadow-lg hover:bg-gray-50 dark:hover:bg-gray-600 transition-shadow duration-300"
          >
            <div class="flex items-center my-2">
              <!-- Like Icon (SVG) and Count -->
                <div class="flex flex-col items-center mr-4">
                  <svg v-if="thread.likeCount < 0" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="mb-1 size-7">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M7.498 15.25H4.372c-1.026 0-1.945-.694-2.054-1.715a12.137 12.137 0 0 1-.068-1.285c0-2.848.992-5.464 2.649-7.521C5.287 4.247 5.886 4 6.504 4h4.016a4.5 4.5 0 0 1 1.423.23l3.114 1.04a4.5 4.5 0 0 0 1.423.23h1.294M7.498 15.25c.618 0 .991.724.725 1.282A7.471 7.471 0 0 0 7.5 19.75 2.25 2.25 0 0 0 9.75 22a.75.75 0 0 0 .75-.75v-.633c0-.573.11-1.14.322-1.672.304-.76.93-1.33 1.653-1.715a9.04 9.04 0 0 0 2.86-2.4c.498-.634 1.226-1.08 2.032-1.08h.384m-10.253 1.5H9.7m8.075-9.75c.01.05.027.1.05.148.593 1.2.925 2.55.925 3.977 0 1.487-.36 2.89-.999 4.125m.023-8.25c-.076-.365.183-.75.575-.75h.908c.889 0 1.713.518 1.972 1.368.339 1.11.521 2.287.521 3.507 0 1.553-.295 3.036-.831 4.398-.306.774-1.086 1.227-1.918 1.227h-1.053c-.472 0-.745-.556-.5-.96a8.95 8.95 0 0 0 .303-.54" />
                  </svg>
                  <svg v-else xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="mb-1 size-7">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M6.633 10.25c.806 0 1.533-.446 2.031-1.08a9.041 9.041 0 0 1 2.861-2.4c.723-.384 1.35-.956 1.653-1.715a4.498 4.498 0 0 0 .322-1.672V2.75a.75.75 0 0 1 .75-.75 2.25 2.25 0 0 1 2.25 2.25c0 1.152-.26 2.243-.723 3.218-.266.558.107 1.282.725 1.282m0 0h3.126c1.026 0 1.945.694 2.054 1.715.045.422.068.85.068 1.285a11.95 11.95 0 0 1-2.649 7.521c-.388.482-.987.729-1.605.729H13.48c-.483 0-.964-.078-1.423-.23l-3.114-1.04a4.501 4.501 0 0 0-1.423-.23H5.904m10.598-9.75H14.25M5.904 18.5c.083.205.173.405.27.602.197.4-.078.898-.523.898h-.908c-.889 0-1.713-.518-1.972-1.368a12 12 0 0 1-.521-3.507c0-1.553.295-3.036.831-4.398C3.387 9.953 4.167 9.5 5 9.5h1.053c.472 0 .745.556.5.96a8.958 8.958 0 0 0-1.302 4.665c0 1.194.232 2.333.654 3.375Z" />
                  </svg>
                  <span class="text-xs text-gray-500 dark:text-gray-400">{{ thread.likeCount }}</span>
                </div>

              <!-- Title and Additional Information -->
              <div class="flex flex-col w-full">
                <!-- Title with Router Link -->
                <router-link
                    :to="{ name: 'single-thread', params: { id: thread.id } }"
                    class="text-lg font-semibold text-blue-600 dark:text-blue-400 hover:underline mb-2"
                >
                  {{ thread.title }}
                </router-link>

                <!-- Additional information (user and date) -->
                <div class="text-sm text-gray-400 flex justify-between">
                  <span>Gemaakt door: {{ thread.user.username }}</span>
                  <span>{{ thread.date }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Meer Zien Knop -->
        <div v-if="hasMoreThreads" class="text-center mt-8">
          <button
              class="cta-button px-6 sm:px-8 py-3 sm:py-4 text-white bg-blue-600 dark:bg-blue-800 hover:bg-blue-800 dark:hover:bg-blue-900 rounded-full transition-all shadow-lg transform hover:scale-105"
              @click="loadMoreThreads"
          >
            Meer Zien
          </button>
        </div>
      </div>
    </section>

    <!-- Sectie 3: Nieuwe Draad Maken Knop -->
    <section class="text-center mt-12 mb-10">
      <button
          class="cta-button px-6 sm:px-8 py-3 sm:py-4 text-white bg-green-700 dark:bg-green-800 hover:bg-green-900 rounded-full transition-all shadow-lg transform hover:scale-105"
          @click="$router.push('/create-thread')"
      >
        Nieuwe Thread Maken
      </button>
    </section>
  </div>
</template>

<script>
import { threadService } from "../services/ThreadService";
import { likeService } from "../services/LikeService";

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
      return this.threads.slice(0, this.visibleThreadsCount);
    },
    hasMoreThreads() {
      return this.visibleThreadsCount < this.threads.length;
    },
  },
  methods: {
    async fetchThreads() {
      try {
        const data = await threadService.fetchThreads();
        this.threads = await Promise.all(
            data.map(async (thread) => {
              const summary = await likeService.fetchVoteSummary(thread.id);
              return {
                ...thread,
                likeCount: summary.upvotes - summary.downvotes,
              };
            })
        );
      } catch (err) {
        console.error(err);
      }
    },
    loadMoreThreads() {
      this.visibleThreadsCount += 3;
    },
  },
  mounted() {
    this.fetchThreads();
  },
};
</script>

