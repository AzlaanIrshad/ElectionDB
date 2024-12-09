<template>
  <div class="mb-4 bg-white dark:bg-gray-800 rounded p-4">
    <Breadcrumb :home="home" :model="breadcrumbItems">
      <template #homeicon>
        <a @click="home.command" class="cursor-pointer hover:opacity-75" title="Ga naar Home">
          <span v-html="home.icon"></span>
        </a>
      </template>
      <template #item="{ item, props }">
        <router-link
            v-if="item.route"
            v-slot="{ href, navigate }"
            :to="item.route"
            custom
        >
          <a :href="href" v-bind="props.action" @click="navigate">
            <span v-html="item.icon"></span>
            <span class="text-primary font-semibold">{{ item.label }}</span>
          </a>
        </router-link>
        <a v-else :href="item.url" :target="item.target" v-bind="props.action">
          <span v-html="item.icon"></span>
          <span class="text-surface-700 dark:text-surface-0">{{ item.label }}</span>
        </a>
      </template>
    </Breadcrumb>
  </div>
</template>

<script>
import Breadcrumb from "primevue/breadcrumb";
import { useRoute, useRouter } from "vue-router";

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
  data() {
    return {
      breadcrumbItems: [],
    };
  },
  setup() {
    const route = useRoute();
    const router = useRouter();
    return { route, router };
  },
  methods: {
    updateBreadcrumbs() {
      this.breadcrumbItems = this.route.matched.map((route) => ({
        label: route.meta.breadcrumb || route.name,
        command: () => this.$router.push(route.path),
      }));
    },
  },
  watch: {
    $route() {
      this.updateBreadcrumbs();
    },
  },
  created() {
    this.updateBreadcrumbs();
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
