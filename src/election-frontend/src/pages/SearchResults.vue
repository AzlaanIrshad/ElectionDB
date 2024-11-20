<template>
  <div class="container mx-auto px-4 py-6">
    <h1 class="text-2xl font-bold text-center mb-4">Search Results for "{{ query }}"</h1>

    <div v-if="results.length > 0" class="space-y-4">
      <div v-for="result in results" :key="result.id" class="bg-gray-700 p-4 rounded-lg">
        <h3 class="text-xl text-white">{{ result.name }}</h3>
        <p class="text-gray-300">Votes: {{ result.votes }}</p>
      </div>
    </div>

    <div v-else class="text-center text-gray-400">
      No results found for "{{ query }}".
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      query: this.$route.params.query,
      results: [],
    };
  },
  mounted() {
    this.fetchResults();
  },
  methods: {
    fetchResults() {

      fetch(`/api/search?q=${this.query}`)
          .then((response) => response.json())
          .then((data) => {
            this.results = data;
          })
          .catch((error) => {
            console.error("Error fetching results:", error);
          });
    },
  },
};
</script>

<style scoped>

</style>
