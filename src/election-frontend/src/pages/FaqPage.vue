<template>
  <div class="faq-container max-w-3xl mx-auto py-12 px-4">
    <h2 class="text-3xl font-bold text-center mb-6 text-gray-900 dark:text-gray-100">
      Frequently Asked Questions
    </h2>

    <!-- Search Bar -->
    <div class="search-bar mb-6">
      <input
          type="text"
          v-model="searchQuery"
          @input="fetchFaqs"
          placeholder="What are you looking for?"
          class="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-blue-300 dark:border-gray-600 dark:bg-gray-800 dark:text-gray-100 dark:placeholder-gray-400"
      />
    </div>

    <!-- FAQ List -->
    <div v-if="filteredFaqs.length" class="faq-list">
      <div v-for="faq in filteredFaqs" :key="faq.id" class="faq-item border-b border-gray-300 py-4 dark:border-gray-600">
        <details class="group">
          <summary class="cursor-pointer font-semibold text-lg text-gray-800 dark:text-gray-100 group-open:mb-2">
            {{ faq.question }}
          </summary>
          <p class="mt-2 text-gray-600 dark:text-gray-300">
            {{ faq.answer }}
          </p>
        </details>
      </div>
    </div>

    <!-- No FAQs Found Message -->
    <p v-else class="text-center text-gray-500 dark:text-gray-400">No FAQs found.</p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      searchQuery: '',
      faqs: [],
    };
  },
  computed: {
    filteredFaqs() {
      return this.faqs.filter(faq =>
          faq.question.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    },
  },
  methods: {
    async fetchFaqs() {
      let url = 'http://localhost:8080/api/faqs';
      if (this.searchQuery) {
        url += `/search?q=${encodeURIComponent(this.searchQuery)}`;
      }
      const response = await fetch(url);
      this.faqs = await response.json();
    },
  },
  mounted() {
    this.fetchFaqs();
  },
};
</script>
