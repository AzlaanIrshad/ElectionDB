<template>
  <div class="flex flex-col md:flex-row h-screen">
    <!-- Sidebar Menu -->
    <div
        class="w-full md:w-64 bg-gray-900 text-gray-300 shadow-lg flex flex-col border-r border-white md:h-screen"
    >
      <div class="flex justify-between items-center p-4 md:border-b border-gray-600">
        <h2 class="text-xl font-bold">Statistieken</h2>
        <!-- Toggle Button for Mobile -->
        <button
            class="md:hidden text-gray-300 hover:text-white focus:outline-none"
            @click="isSidebarOpen = !isSidebarOpen"
        >
          <svg
              xmlns="http://www.w3.org/2000/svg"
              class="h-6 w-6"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
          >
            <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M4 6h16M4 12h16m-7 6h7"
            />
          </svg>
        </button>
      </div>
      <ul
          :class="[
          'mt-4 space-y-1 px-4 transition-all duration-200',
          isSidebarOpen ? 'block' : 'hidden md:block'
        ]"
      >
        <li v-for="(tab, index) in tabs" :key="index">
          <button
              @click="activeTab = tab.name"
              :class="[
              'w-full flex items-center px-4 py-3 space-x-3 text-left rounded-lg transition-all duration-200',
              activeTab === tab.name ? 'bg-blue-400 text-white shadow-md' : 'hover:bg-gray-600 hover:text-white'
            ]"
          >
            <span class="inline-flex justify-center items-center w-6 h-6 rounded-full text-blue-300">
              <span v-html="tab.icon"></span>
            </span>
            <span>{{ tab.name }}</span>
          </button>
        </li>
      </ul>
    </div>

    <!-- Main Content -->
    <div class="flex-1 p-4 md:p-6 overflow-y-auto">
      <h1 class="text-2xl font-bold mb-6">
        Verkiezingsstatistieken
      </h1>

      <!-- Render Componenten Gebaseerd op Actieve Tab -->
      <div v-if="activeTab === 'Per Stemlocatie'">
        <ElectionMap />
      </div>
      <div v-else-if="activeTab === 'Per Verkiezing'">
        <div class="p-6 dark:bg-gray-800 rounded-lg shadow-md">
          <h2 class="text-lg font-semibold mb-4 text-gray-200">
            Stemmenverdeling
          </h2>
          <ElectionDonutChart :electionData="sampleElectionData" />
        </div>
      </div>
      <div v-else-if="activeTab === 'Per Partij'">
        <div class="p-6 bg-gray-800 rounded-lg shadow-md">
          <h2 class="text-lg font-semibold mb-4 text-gray-200">
            Per Partij
          </h2>
          <p class="text-gray-400">
            Data en statistieken per partij worden hier weergegeven.
          </p>
        </div>
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
      isSidebarOpen: false,
      tabs: [
        {
          name: "Per Stemlocatie",
          icon: `
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
              <path stroke-linecap="round" stroke-linejoin="round" d="M9 6.75V15m6-6v8.25m.503 3.498 4.875-2.437c.381-.19.622-.58.622-1.006V4.82c0-.836-.88-1.38-1.628-1.006l-3.869 1.934c-.317.159-.69.159-1.006 0L9.503 3.252a1.125 1.125 0 0 0-1.006 0L3.622 5.689C3.24 5.88 3 6.27 3 6.695V19.18c0 .836.88 1.38 1.628 1.006l3.869-1.934c.317-.159.69-.159 1.006 0l4.994 2.497c.317.158.69.158 1.006 0Z" />
            </svg>
          `,
        },
        {
          name: "Per Verkiezing",
          icon: `
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
              <path stroke-linecap="round" stroke-linejoin="round" d="M6 6.878V6a2.25 2.25 0 0 1 2.25-2.25h7.5A2.25 2.25 0 0 1 18 6v.878m-12 0c.235-.083.487-.128.75-.128h10.5c.263 0 .515.045.75.128m-12 0A2.25 2.25 0 0 0 4.5 9v.878m13.5-3A2.25 2.25 0 0 1 19.5 9v.878m0 0a2.246 2.246 0 0 0-.75-.128H5.25c-.263 0-.515.045-.75.128m15 0A2.25 2.25 0 0 1 21 12v6a2.25 2.25 0 0 1-2.25 2.25H5.25A2.25 2.25 0 0 1 3 18v-6c0-.98.626-1.813 1.5-2.122" />
            </svg>
          `,
        },
        {
          name: "Per Partij",
          icon: `
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
              <path stroke-linecap="round" stroke-linejoin="round" d="M3.75 3v11.25A2.25 2.25 0 0 0 6 16.5h2.25M3.75 3h-1.5m1.5 0h16.5m0 0h1.5m-1.5 0v11.25A2.25 2.25 0 0 1 18 16.5h-2.25m-7.5 0h7.5m-7.5 0-1 3m8.5-3 1 3m0 0 .5 1.5m-.5-1.5h-9.5m0 0-.5 1.5m.75-9 3-3 2.148 2.148A12.061 12.061 0 0 1 16.5 7.605" />
            </svg>
          `,
        },
      ],
      sampleElectionData: [], // Voorbeeld data voor de grafiek
    };
  },
  methods: {
    async fetchElectionData() {
      const data = await fetch("/api/election-statistics");
      this.sampleElectionData = await data.json();
    },
  },
  mounted() {
    this.fetchElectionData();
  },
};
</script>
