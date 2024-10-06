<template>
  <div class="homepage bg-gray-100 font-sans">

    <!-- Hero Section -->
    <section class="hero text-center py-16 bg-white rounded-lg shadow-xl mt-5 mx-2 lg:mx-5">
      <h1 class="text-4xl lg:text-6xl text-gray-800 mb-5 font-extrabold">Electiondb 2024</h1>
      <p class="text-base lg:text-lg text-gray-700 mb-8">Stay updated with the latest insights on upcoming elections.</p>
      <button class="cta-button px-6 py-3 text-white bg-blue-700 hover:bg-blue-600 rounded-full transition-all shadow-lg transform hover:scale-105">Learn More</button>
      
      <!-- Carousel Section -->
      <div class="carousel my-8 relative overflow-hidden mx-2 sm:mx-6 md:mx-10 lg:mx-12 rounded-lg">
        <div
          class="carousel-images flex transition-transform duration-500"
          :style="{ transform: `translateX(-${activeIndex * 100}%)` }"
        >
          <img
            v-for="(image, index) in featuredImages"
            :key="index"
            :src="image"
            class="hero-image w-full h-48 sm:h-64 lg:h-80 object-cover rounded-lg shadow-xl"
            alt="Featured Image"
          />
        </div>
      </div>

      <!-- Tiles Section -->
      <div class="tiles flex justify-center mt-5 gap-2 sm:gap-4">
        <div
          v-for="(image, index) in featuredImages"
          :key="index"
          class="tile w-1/4 sm:w-1/5 h-24 sm:h-32 lg:h-40 flex justify-center items-center bg-gray-200 text-center rounded-lg shadow-md transition-all cursor-pointer hover:bg-blue-500 hover:text-white transform hover:scale-105"
          :class="{ 'border-blue-500 border-4': activeIndex === index }"
          @click="setActiveIndex(index)"
        >
          <span class="font-semibold text-sm sm:text-lg lg:text-xl">Tile {{ index + 1 }}</span>
        </div>
      </div>
    </section>

    <!-- Categories Section -->
    <section class="categories py-10 mb-10 bg-white rounded-lg shadow-xl mt-10 mx-2 lg:mx-5">
      <h2 class="text-3xl lg:text-4xl text-center mb-8 font-extrabold text-gray-800">Election Categories</h2>
      <div class="blocks flex flex-col md:flex-row justify-around gap-4 lg:gap-6 mx-4 sm:mx-6 lg:mx-10">
        <div class="block w-full md:w-1/4 h-36 sm:h-44 flex justify-center items-center bg-blue-600 text-center rounded-lg shadow-md cursor-pointer hover:bg-blue-800 transition-all text-white transform hover:scale-105">
          <span class="font-bold text-xl lg:text-2xl">Region Results</span>
        </div>
        <div class="block w-full md:w-1/4 h-36 sm:h-44 flex justify-center items-center bg-blue-600 text-center rounded-lg shadow-md cursor-pointer hover:bg-blue-800 transition-all text-white transform hover:scale-105">
          <span class="font-bold text-xl lg:text-2xl">District Insights</span>
        </div>
        <div class="block w-full md:w-1/4 h-36 sm:h-44 flex justify-center items-center bg-blue-600 text-center rounded-lg shadow-md cursor-pointer hover:bg-blue-800 transition-all text-white transform hover:scale-105">
          <span class="font-bold text-xl lg:text-2xl">Candidate Profiles</span>
        </div>
      </div>
      <div class="text-center mt-10">
        <button class="cta-button px-6 sm:px-8 py-3 sm:py-4 text-white bg-blue-700 hover:bg-blue-900 rounded-full transition-all shadow-lg transform hover:scale-105">Explore More</button>
      </div>
    </section>

    <!-- News Section -->
    <section class="news py-12 bg-white rounded-lg shadow-xl mx-2 lg:mx-5 mb-10">
      <h2 class="text-3xl lg:text-4xl text-center mb-10 font-extrabold text-gray-800">Recent Election News</h2>
      <div class="news-content flex flex-col md:flex-row items-center gap-8 mx-4 sm:mx-6 lg:mx-10">
        <div class="news-image w-full md:w-2/5 h-40 sm:h-60 bg-gradient-to-b from-gray-200 to-gray-400 rounded-lg shadow-md"></div>
        <div class="news-text w-full md:w-3/5">
          <h3 class="text-2xl sm:text-3xl font-extrabold mb-5 text-gray-800">Lorem Ipsum Article</h3>
          <p class="mb-4 text-base sm:text-lg text-gray-600 leading-relaxed">Lorem ipsum is a placeholder text used to demonstrate the layout of a document or a typeface.</p>
          <p class="text-base sm:text-lg text-gray-600 leading-relaxed">Read on to discover more about recent election developments and stay informed.</p>
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
      }, 5000); // 5 sec interval for image carousel
    },
    setActiveIndex(index) {
      this.activeIndex = index;
    },
  },
};
</script>