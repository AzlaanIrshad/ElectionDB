<template>
    <div class="homepage">
      <!-- Hero Section with Carousel -->
      <section class="hero text-center py-16 rounded-lg">
        <h1 class="text-5xl text-black mb-5">Elections</h1>
        <p class="text-lg text-black mb-5">Lorem ipsum dolor sit, amet consectetur adipisicing elit. Dignissimos, voluptates dolore. Vitae, modi error? Dolorem eveniet illo debitis et repellat. Quia fugiat doloremque nam dolore illo in reprehenderit corrupti eligendi.</p>
        <button class="cta-button px-6 py-3 text-white bg-gray-200 hover:bg-gray-600 rounded-md transition">Learn more</button>
        
        <div class="carousel my-5">
          <img :src="featuredImages[activeIndex]" class="hero-image w-full h-72 object-cover rounded-lg" alt="Featured Image" />
        </div>
        
        <div class="tiles flex justify-around mt-5 gap-2">
          <div
            v-for="(image, index) in featuredImages"
            :key="index"
            class="tile w-1/3 h-36 bg-gray-200 text-center leading-36 rounded-lg transition cursor-pointer hover:bg-gray-600 hover:text-white"
            :class="{ 'border-white border-4': activeIndex === index }"
            @click="setActiveIndex(index)"
          >
            Tile {{ index + 1 }}
          </div>
        </div>
      </section>
  
      <!-- Section 2 - Categories -->
      <section class="categories py-10 mb-10 bg-gray-50 rounded-lg">
        <h2 class="text-3xl text-center mb-6">Election Categories</h2>
        <div class="blocks flex justify-around gap-5">
          <div class="block w-1/3 h-36 bg-gray-200 text-center leading-36 rounded-lg transition cursor-pointer hover:bg-gray-600 hover:text-white">
            Region Results
          </div>
          <div class="block w-1/3 h-36 bg-gray-200 text-center leading-36 rounded-lg transition cursor-pointer hover:bg-gray-600 hover:text-white">
            District Insights
          </div>
          <div class="block w-1/3 h-36 bg-gray-200 text-center leading-36 rounded-lg transition cursor-pointer hover:bg-gray-600 hover:text-white">
            Candidate Profiles
          </div>
        </div>
        <div class="text-center mt-6">
          <button class="cta-button px-6 py-3 text-white bg-gray-200 hover:bg-gray-600 rounded-md transition">Learn more</button>
        </div>
      </section>
  
      <!-- Section 3 - Articles or News -->
      <section class="news py-10 bg-white rounded-lg shadow-md">
        <h2 class="text-3xl text-center mb-6">Recent Election News</h2>
        <div class="news-content flex items-center gap-5">
          <div class="news-image w-2/5 h-52 bg-gray-200 rounded-lg ml-8"></div>
          <div class="news-text w-3/5">
            <h3 class="text-2xl mb-3">Lorem Ipsum Article</h3>
            <p class="mb-3">Lorem ipsum is a placeholder text used to demonstrate the layout of a document or a typeface.</p>
            <p>Read on to discover more about recent election developments.</p>
          </div>
        </div>
      </section>
    </div>
  </template>
  
  <script>
  export default {
    name: "HomePage",
    data() {
      return {
        activeIndex: 0,
        featuredImages: [
          "https://via.placeholder.com/800x300?text=Featured+Image+1",
          "https://via.placeholder.com/800x300?text=Featured+Image+2",
          "https://via.placeholder.com/800x300?text=Featured+Image+3",
        ],
      };
    },
    mounted() {
      this.startCarousel();
    },
    methods: {
      startCarousel() {
        setInterval(() => {
          this.activeIndex = (this.activeIndex + 1) % this.featuredImages.length;
        }, 4000); // 4 sec
      },
      setActiveIndex(index) {
        this.activeIndex = index;
      },
    },
  };
  </script>
  