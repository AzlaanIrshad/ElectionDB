<template>
  <div class="mb-4 bg-white dark:bg-gray-800 rounded p-4">
    <Breadcrumb :home="home" :model="breadcrumbItems">
      <template #homeicon>
        <router-link
            :to="home.route"
            class="cursor-pointer hover:opacity-75"
            title="Ga naar Home"
        >
          <span v-html="home.icon"></span>
        </router-link>
      </template>
      <template #item="{ item, props }">
        <router-link
            v-if="item.route"
            :to="item.route"
            class="cursor-pointer"
        >
          <span v-html="item.icon"></span>
          <span class="text-primary font-semibold">{{ item.label }}</span>
        </router-link>
        <a
            v-else
            :href="item.url"
            :target="item.target"
            class="cursor-pointer"
        >
          <span v-html="item.icon"></span>
          <span class="text-surface-700 dark:text-surface-0">{{ item.label }}</span>
        </a>
      </template>
    </Breadcrumb>
  </div>
</template>

<script>
import Breadcrumb from "primevue/breadcrumb";
import { useRoute } from "vue-router";
import { reactive, watch } from "vue";

export default {
  name: "BreadcrumbComponent",
  components: {
    Breadcrumb,
  },
  props: {
    home: {
      type: Object,
      required: true,
    },
  },
  setup() {
    const route = useRoute();
    const breadcrumbItems = reactive([]);

    const updateBreadcrumbs = () => {
      breadcrumbItems.length = 0; // Leeg de lijst eerst
      route.matched.forEach((matchedRoute) => {
        const label =
            typeof matchedRoute.meta.breadcrumb === "function"
                ? matchedRoute.meta.breadcrumb(route)
                : matchedRoute.meta.breadcrumb;

        breadcrumbItems.push({
          label: label,
          route: matchedRoute.path !== route.path ? matchedRoute.path : null,
          icon: matchedRoute.meta.icon || "",
        });
      });
    };

    watch(route, updateBreadcrumbs, { immediate: true });

    return {
      breadcrumbItems,
      updateBreadcrumbs,
    };
  },
};
</script>

<style scoped>
.p-breadcrumb {
  background-color: transparent;
}
.p-breadcrumb-item-link {
  color: #000000;
}
.dark .p-breadcrumb-item-link {
  color: #ffffff;
}
.p-breadcrumb-item-link:hover {
  text-decoration: underline;
  cursor: pointer;
}
</style>
