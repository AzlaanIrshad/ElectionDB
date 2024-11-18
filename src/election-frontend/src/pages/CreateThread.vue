<template>
  <div class="homepage bg-gray-100 dark:bg-gray-900 font-sans min-h-screen py-8">
    <section class="mx-4 sm:mx-6 lg:mx-10">
      <div class="threads py-12 bg-white dark:bg-gray-800 rounded-lg shadow-lg mx-auto mb-10 max-w-4xl">
        <h2 class="text-3xl lg:text-4xl text-center mb-8 font-extrabold text-gray-800 dark:text-gray-100">
          Create a new election thread!
        </h2>
        <div class="threads-content flex flex-col md:flex-row items-center gap-8 px-4 sm:px-6 lg:px-8">
          <form class="w-full space-y-6" @submit.prevent="createThread">
            <div>
              <input
                  type="text"
                  id="title"
                  v-model="title"
                  placeholder="Thread Title"
                  class="w-full p-4 border border-gray-300 dark:border-gray-600 rounded-md focus:outline-none focus:ring focus:ring-blue-300 dark:focus:ring-blue-500 text-gray-700 dark:text-gray-200 dark:bg-gray-700"
                  :class="{ 'border-red-500 dark:border-red-500': titleError }"
              />
              <p v-if="titleError" class="text-red-500 text-sm mt-1">{{ titleError }}</p>
            </div>

            <div>
              <textarea
                  id="body"
                  v-model="body"
                  placeholder="Thread Body"
                  class="w-full p-4 border border-gray-300 dark:border-gray-600 rounded-md focus:outline-none focus:ring focus:ring-blue-300 dark:focus:ring-blue-500 text-gray-700 dark:text-gray-200 dark:bg-gray-700 h-32 resize-none"
                  :class="{ 'border-red-500 dark:border-red-500': bodyError }"
              ></textarea>
              <p v-if="bodyError" class="text-red-500 text-sm mt-1">{{ bodyError }}</p>
            </div>

            <div>
              <input
                  type="text"
                  id="category"
                  v-model="category"
                  placeholder="Category"
                  class="w-full p-4 border border-gray-300 dark:border-gray-600 rounded-md focus:outline-none focus:ring focus:ring-blue-300 dark:focus:ring-blue-500 text-gray-700 dark:text-gray-200 dark:bg-gray-700"
                  :class="{ 'border-red-500 dark:border-red-500': categoryError }"
              />
              <p v-if="categoryError" class="text-red-500 text-sm mt-1">{{ categoryError }}</p>
            </div>

            <p v-if="titleError || bodyError || categoryError" class="text-red-500 text-sm mt-4">
              Fill in all fields.
            </p>
          </form>
        </div>
      </div>
    </section>

    <section class="admin-panel text-center mb-10">
      <div class="flex justify-center gap-4">
        <button
            class="cta-button px-8 py-4 text-white bg-green-600 dark:bg-green-700 hover:bg-green-700 dark:hover:bg-green-800 focus:ring focus:ring-green-300 dark:focus:ring-green-500 rounded-full transition-all shadow-lg transform hover:scale-105"
            :disabled="!isFormValid"
            @click="createThread"
        >
          Create Thread
        </button>
        <button
            class="cta-button px-8 py-4 text-white bg-gray-600 dark:bg-gray-700 hover:bg-gray-700 dark:hover:bg-gray-800 focus:ring focus:ring-gray-300 dark:focus:ring-gray-500 rounded-full transition-all shadow-lg transform hover:scale-105"
            @click="goBack"
        >
          Go Back
        </button>
      </div>
    </section>
  </div>
</template>

<script>
export default {
  name: "CreateThreadPage",
  data() {
    return {
      title: '',
      body: '',
      category: '',
      titleError: '',
      bodyError: '',
      categoryError: '',
    };
  },
  computed: {
    isFormValid() {
      return this.title !== '' && this.body !== '' && this.category !== '';
    },
  },
  methods: {
    validateForm() {
      this.titleError = this.bodyError = this.categoryError = '';

      if (this.title === '') {
        this.titleError = 'Title is required.';
      }

      if (this.body === '') {
        this.bodyError = 'Body is required.';
      }

      if (this.category === '') {
        this.categoryError = 'Category is required.';
      }

      return !this.titleError && !this.bodyError && !this.categoryError;
    },
    async createThread() {
      if (!this.validateForm()) {
        return;
      }

      try {
        const dummyUser = {
          id: 1,
          username: 'googoo',
          email: 'googoo@example.com',
          password: 'password',
          role: 'ADMIN'
        };

        const now = new Date();
        const formattedDate = `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, '0')}-`
            + `${String(now.getDate()).padStart(2, '0')} ${String(now.getHours()).padStart(2, '0')}:`
            + `${String(now.getMinutes()).padStart(2, '0')}`;

        const threadData = {
          title: this.title,
          body: this.body,
          category: this.category,
          date: formattedDate,
          user: dummyUser,
        };

        const response = await fetch('http://localhost:8080/api/threads', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(threadData),
        });

        if (response.ok) {
          this.$router.push('/threads');
        } else {
          console.error('Error creating thread:', response.statusText);
        }
      } catch (error) {
        console.error('Error creating thread:', error);
      }
    },
    goBack() {
      this.$router.go(-1);
    }
  },
};
</script>
