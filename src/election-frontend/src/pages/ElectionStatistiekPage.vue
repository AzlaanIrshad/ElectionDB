<template>
  <div>
    <h1 class="text-2xl font-bold mb-4">Onderzoeks Statistiek</h1>

    <!-- Navigation Tabs -->
    <div class="flex space-x-4 border-b border-blue-500 mb-4">
      <button
          v-for="(tab, index) in tabs"
          :key="index"
          @click="activeTab = tab"
          :class="[
          'px-4 py-2 font-medium',
          activeTab === tab ? 'bg-blue-500 text-white' : '',
        ]"
      >
        {{ tab }}
      </button>
    </div>

    <!-- Render Components Based on Active Tab -->
    <div v-if="activeTab === 'Per Stemlocatie'">
      <ElectionMap />
    </div>
    <div v-else-if="activeTab === 'Per Verkiezing'">
      <div class="p-4 bg-gray-100 rounded-lg">
        <h2 class="text-lg font-bold mb-2">Per Verkiezing</h2>
        <p>Data en statistieken per verkiezing worden hier getoond.</p>
      </div>
    </div>
    <div v-else-if="activeTab === 'Per Partij'">
      <div class="p-4 bg-gray-100 rounded-lg">
        <h2 class="text-lg font-bold mb-2">Per Partij</h2>
        <p>Data en statistieken per partij worden hier getoond.</p>
      </div>
    </div>
  </div>
</template>

<script>
import ElectionMap from "@/components/ElectionMap.vue";

export default {
  name: "ElectionStatistiekPage",
  components: { ElectionMap },
  data() {
    return {
      activeTab: "Per Stemlocatie",
      tabs: ["Per Verkiezing", "Per Stemlocatie", "Per Partij"],
    };
  },
};
</script>
