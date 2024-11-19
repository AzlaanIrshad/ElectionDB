<template>
  <div>
    <h1 class="text-2xl font-bold mb-4">Election Statistiek</h1>

    <!-- Navigation Tabs -->
    <div class="flex space-x-4 border-b border-blue-500 mb-4">
      <button
          v-for="(tab, index) in tabs"
          :key="index"
          @click="activeTab = tab"
          :class="[ 'px-4 py-2 font-medium', activeTab === tab ? 'bg-blue-500 text-white' : '' ]"
      >
        {{ tab }}
      </button>
    </div>

    <!-- Render Components Based on Active Tab -->
    <div v-if="activeTab === 'Per Stemlocatie'">
      <ElectionMap />
    </div>
    <div v-else-if="activeTab === 'Per Verkiezing'">
      <div class="p-4 bg-gray-100 rounded-lg bg-white dark:bg-gray-700">
        <h2 class="text-lg font-bold mb-2">Votes Distribution</h2>
        <ElectionDonutChart :electionData="sampleElectionData" />
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
import ElectionDonutChart from "@/components/ElectionDonutChart.vue";

export default {
  name: "ElectionStatistiekPage",
  components: { ElectionMap, ElectionDonutChart },
  data() {
    return {
      activeTab: "Per Stemlocatie",
      tabs: ["Per Verkiezing", "Per Stemlocatie", "Per Partij"],
      sampleElectionData: [
        { partyName: "VVD", totalVotes: 49517 },
        { partyName: "D66", totalVotes: 41418 },
        { partyName: "PVV", totalVotes: 37012 },
        { partyName: "PvdA", totalVotes: 29015 },
      ],
    };
  },
};
</script>
