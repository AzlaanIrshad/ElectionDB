<template>
  <div class="homepage bg-gray-100 font-sans">
    <!-- Hero Section -->
    <section class="hero text-center py-12 lg:py-16 bg-white rounded-lg shadow-xl mt-5 mx-2 lg:mx-5">
      <h1 class="text-3xl sm:text-4xl lg:text-6xl text-gray-800 mb-5 font-extrabold">
        Welcome to Electiondb
      </h1>
      <p class="text-sm sm:text-base lg:text-lg text-gray-700 my-6 sm:my-8 mx-4 sm:mx-12 lg:mx-24">
        Stay ahead of the curve with Electiondb 2024, your ultimate hub for real-time election updates and expert analysis. Whether it's breaking news, shifts in voter sentiment, or key insights into the most closely-watched races, we provide comprehensive, up-to-the-minute coverage. Dive deep into national and state-level contests, explore detailed candidate profiles, and track every pivotal development as we journey through the 2024 election cycle. From trending issues to in-depth forecasts, our platform ensures you're informed every step of the way.
      </p>
      <a href="/">
        <button
            class="cta-button px-4 sm:px-6 py-2 sm:py-3 text-white bg-blue-700 hover:bg-blue-600 rounded-full transition-all shadow-lg transform hover:scale-105">
          Election Dashboard
        </button>
      </a>

      <!-- Carousel Section -->
      <div class="carousel my-8 relative overflow-hidden mx-4 sm:mx-8 md:scroll-mx-24 lg:mx-48 rounded-lg shadow-lg">
        <div
            class="carousel-images flex transition-transform duration-500"
            :style="{ transform: `translateX(-${activeIndex * 100}%)` }"
        >
          <img
              v-for="(image, index) in featuredImages"
              :key="index"
              :src="image"
              class="hero-image w-full h-48 sm:h-64 lg:h-80 object-fill rounded-lg shadow-md"
              alt="Featured Image"
          />
        </div>

        <!-- Left Arrow -->
        <button
            class="absolute left-4 top-1/2 transform -translate-y-1/2 bg-white text-gray-800 p-2 rounded-full shadow-lg hover:bg-gray-200 transition"
            @click="prevImage"
        >
          &#10094;
        </button>

        <!-- Right Arrow -->
        <button
            class="absolute right-4 top-1/2 transform -translate-y-1/2 bg-white text-gray-800 p-2 rounded-full shadow-lg hover:bg-gray-200 transition"
            @click="nextImage"
        >
          &#10095;
        </button>
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
          <span class="font-semibold text-sm sm:text-lg lg:text-xl">View {{ index + 1 }}</span>
        </div>
      </div>
    </section>

    <!-- Categories Section -->
    <section class="categories py-10 mb-10 bg-white rounded-lg shadow-lg mt-10 mx-2 lg:mx-5">
      <h2 class="text-3xl lg:text-4xl text-center mb-8 font-extrabold text-gray-800">Election Categories</h2>
      <div class="blocks flex flex-col md:flex-row justify-around gap-4 lg:gap-6 mx-4 sm:mx-6 lg:mx-10">
        <div class="block w-full md:w-1/4 h-36 sm:h-44 flex justify-center items-center bg-blue-600 text-center rounded-lg shadow-md cursor-pointer hover:bg-blue-800 transition-all text-white transform hover:scale-105">
          <a href="/"><span class="font-bold text-xl lg:text-2xl">National Overview</span></a>
        </div>
        <div class="block w-full md:w-1/4 h-36 sm:h-44 flex justify-center items-center bg-blue-600 text-center rounded-lg shadow-md cursor-pointer hover:bg-blue-800 transition-all text-white transform hover:scale-105">
          <a href="/"><span class="font-bold text-xl lg:text-2xl">State-by-State Results</span></a>
        </div>
        <div class="block w-full md:w-1/4 h-36 sm:h-44 flex justify-center items-center bg-blue-600 text-center rounded-lg shadow-md cursor-pointer hover:bg-blue-800 transition-all text-white transform hover:scale-105">
          <a href="/"><span class="font-bold text-xl lg:text-2xl">Candidate Profiles</span></a>
        </div>
      </div>
      <div class="text-center mt-10">
        <a href="/">
          <button class="cta-button px-6 sm:px-8 py-3 sm:py-4 text-white bg-blue-700 hover:bg-blue-900 rounded-full transition-all shadow-lg transform hover:scale-105">Election Dashboard</button>
        </a>
      </div>
    </section>

    <!-- Admin Panel Button -->
    <section class="admin-panel text-center mb-10">
      <button
          class="cta-button px-6 sm:px-8 py-3 sm:py-4 text-white bg-green-700 hover:bg-green-900 rounded-full transition-all shadow-lg transform hover:scale-105"
          @click="$router.push('/admin')"
      >
        Go to Admin Panel
      </button>
    </section>

    <!-- News Section -->
    <section class="news py-12 bg-white rounded-lg shadow-lg mx-2 lg:mx-5 mb-10">
      <h2 class="text-3xl lg:text-4xl text-center mb-10 font-extrabold text-gray-800">Recent Election News</h2>
      <div class="news-content flex flex-col md:flex-row items-center gap-8 mx-4 sm:mx-6 lg:mx-10">
        <img
            src="https://media.gettyimages.com/id/1368872054/nl/vector/online-news-search-and-reading-news-updates-news-websites-information-on-newspapers-public.jpg?s=612x612&w=0&k=20&c=iZdnx4rKpTsJiSK8tFNyE1xztBXHXpHoBEXdDdT4ZFw="
            alt="News Image"
            class="news-image w-full md:w-2/6 mr-28 h-40 sm:h-60 object-cover rounded-lg shadow-md"
        />
        <div class="news-text w-full md:w-3/5">
          <h3 class="text-2xl sm:text-3xl font-extrabold mb-5 text-gray-800">Dutch Elections See Surge in New Political Parties</h3>
          <p class="mb-4 text-base sm:text-lg text-gray-600 leading-relaxed">The Netherlands is witnessing a rise in new political movements, challenging the traditional political landscape. These emerging parties are focusing on issues like housing, climate change, and immigration, which resonate strongly with younger voters.</p>
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
        "https://media.gettyimages.com/id/1327961273/nl/foto/the-hague-netherlands-general-interior-view-of-the-tweede-kamer-building-is-seen-during-the.jpg?s=612x612&w=0&k=20&c=FZv3KDWl4flKiXpm49-0QwZvXB9Sp2Cry3artg0Uw9o=",
        "https://media.gettyimages.com/id/1793796325/nl/foto/the-hague-netherlands-a-woman-walks-past-campaign-posters-on-november-20-2023-in-the-hague.jpg?s=612x612&w=0&k=20&c=tyylwgIawxWevTh-6WcVutNA-yhlPcz9GLWkT6Lwzc8=",
        "https://media.gettyimages.com/id/1165687569/nl/foto/voting-box-and-election-image-election.jpg?s=612x612&w=0&k=20&c=Xu3sE7U-wgcL-xWrS41yGXhMl6NLqw7xqsGNHIPzRbI=",
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
    prevImage() {
      this.activeIndex = (this.activeIndex - 1 + this.featuredImages.length) % this.featuredImages.length;
    },
    nextImage() {
      this.activeIndex = (this.activeIndex + 1) % this.featuredImages.length;
    },
  },
};
</script>
