<template>
  <div
      class="w-full md:w-64 bg-gray-900 text-gray-300 shadow-lg flex flex-col border-r border-white md:h-full"
  >
    <div class="flex justify-between items-center p-4 md:border-b border-gray-600">
      <h2 class="text-xl font-bold">Statistieken</h2>
      <button
          class="md:hidden text-gray-300 hover:text-white focus:outline-none"
          @click="$emit('toggleSidebar')"
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
    <ul :class="['mt-4 space-y-1 px-4 transition-all duration-200', isSidebarOpen ? 'block' : 'hidden md:block']">
      <li v-for="(tab, index) in tabs" :key="index">
        <router-link
            :to="{ name: tab.routeName }"
            class="w-full flex items-center px-4 py-3 space-x-3 text-left rounded-lg transition-all duration-200"
            active-class="bg-blue-400 text-white shadow-md"
        >
          <span class="inline-flex justify-center items-center w-6 h-6 rounded-full text-blue-300">
            <span v-html="tab.icon"></span>
          </span>
          <span>{{ tab.name }}</span>
        </router-link>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "SidebarMenu",
  props: {
    tabs: {
      type: Array,
      required: true,
    },
    isSidebarOpen: {
      type: Boolean,
      default: false,
    },
  },
};
</script>
