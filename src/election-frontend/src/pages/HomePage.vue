<template>
  <div class="homepage bg-gray-100 dark:bg-gray-900 font-sans text-gray-900 dark:text-gray-100">
    <!-- Hero Sectie -->
    <section class="hero text-center py-12 lg:py-16 bg-white dark:bg-gray-800 rounded-lg shadow-xl mt-5 mx-2 lg:mx-5">
      <h1 class="text-3xl sm:text-4xl lg:text-6xl text-gray-800 dark:text-gray-100 mb-5 font-extrabold">
        Welkom bij VerkiezingsDB
      </h1>
      <p class="text-sm sm:text-base lg:text-lg text-gray-700 dark:text-gray-300 my-6 sm:my-8 mx-4 sm:mx-12 lg:mx-24">
        Blijf op de hoogte met VerkiezingsDB 2024, jouw ultieme platform voor real-time verkiezingsupdates en deskundige analyses. Verken gedetailleerde kandidatenprofielen en volg elke belangrijke ontwikkeling in de verkiezingscyclus van 2024.
      </p>
      <a href="/dashboard">
        <button
            class="cta-button px-4 sm:px-6 py-2 sm:py-3 text-white bg-blue-700 dark:bg-blue-800 hover:bg-blue-600 dark:hover:bg-blue-700 rounded-full transition-all shadow-lg transform hover:scale-105">
          Dashboard
        </button>
      </a>

      <!-- Carrousel Sectie -->
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
              alt="Uitgelichte Afbeelding"
          />
        </div>

        <!-- Linker Pijl -->
        <button
            class="absolute left-4 top-1/2 transform -translate-y-1/2 bg-white dark:bg-gray-700 text-gray-800 dark:text-gray-200 p-2 rounded-full shadow-lg hover:bg-gray-200 dark:hover:bg-gray-600 transition"
            @click="prevImage"
        >
          &#10094;
        </button>

        <!-- Rechter Pijl -->
        <button
            class="absolute right-4 top-1/2 transform -translate-y-1/2 bg-white dark:bg-gray-700 text-gray-800 dark:text-gray-200 p-2 rounded-full shadow-lg hover:bg-gray-200 dark:hover:bg-gray-600 transition"
            @click="nextImage"
        >
          &#10095;
        </button>
      </div>

      <!-- Tegels Sectie -->
      <div class="tiles flex justify-center mt-5 gap-2 sm:gap-4">
        <div
            v-for="(image, index) in featuredImages"
            :key="index"
            class="tile w-1/4 sm:w-1/5 h-24 sm:h-32 lg:h-40 bg-gray-200 dark:bg-gray-700 text-center rounded-lg shadow-md transition-all cursor-pointer hover:bg-blue-500 dark:hover:bg-blue-600 transform hover:scale-105"
            :class="{ 'border-blue-500 dark:border-blue-400 border-4': activeIndex === index }"
            @click="setActiveIndex(index)"
        >
          <img :src="image" alt="Thumbnail" class="w-full h-full object-cover rounded-lg" />
        </div>
      </div>
    </section>

    <!-- Categorieën Sectie -->
    <section class="categories py-10 mb-10 bg-white dark:bg-gray-800 rounded-lg shadow-lg mt-10 mx-2 lg:mx-5">
      <h2 class="text-3xl lg:text-4xl text-center mb-8 font-extrabold text-gray-800 dark:text-gray-100">Verkiezingscategorieën</h2>
      <div class="blocks flex flex-col md:flex-row justify-around gap-4 lg:gap-6 mx-4 sm:mx-6 lg:mx-10">
        <div class="block w-full md:w-1/4 h-36 sm:h-44 flex justify-center items-center bg-blue-600 dark:bg-blue-700 text-center rounded-lg shadow-md cursor-pointer hover:bg-blue-800 dark:hover:bg-blue-600 transition-all text-white transform hover:scale-105">
          <a href="/"><span class="font-bold text-xl lg:text-2xl">Nationaal Overzicht</span></a>
        </div>
        <div class="block w-full md:w-1/4 h-36 sm:h-44 flex justify-center items-center bg-blue-600 dark:bg-blue-700 text-center rounded-lg shadow-md cursor-pointer hover:bg-blue-800 dark:hover:bg-blue-600 transition-all text-white transform hover:scale-105">
          <a href="/dashboard"><span class="font-bold text-xl lg:text-2xl">Resultaten per Staat</span></a>
        </div>
        <div class="block w-full md:w-1/4 h-36 sm:h-44 flex justify-center items-center bg-blue-600 dark:bg-blue-700 text-center rounded-lg shadow-md cursor-pointer hover:bg-blue-800 dark:hover:bg-blue-600 transition-all text-white transform hover:scale-105">
          <a href="/"><span class="font-bold text-xl lg:text-2xl">Kandidatenprofielen</span></a>
        </div>
      </div>
      <div class="text-center mt-10">
        <a href="/threads">
          <button class="cta-button px-6 sm:px-8 py-3 sm:py-4 text-white bg-blue-700 dark:bg-blue-800 hover:bg-blue-900 dark:hover:bg-blue-700 rounded-full transition-all shadow-lg transform hover:scale-105">Discussie Draadjes</button>
        </a>
      </div>
    </section>

    <!-- Nieuws Sectie -->
    <section class="news py-12 bg-white dark:bg-gray-800 rounded-lg shadow-lg mx-2 lg:mx-5 mb-10">
      <h2 class="text-3xl lg:text-4xl text-center mb-10 font-extrabold text-gray-800 dark:text-gray-100">Recente Verkiezingsnieuws</h2>
      <div class="news-content flex flex-col md:flex-row items-center gap-8 mx-4 sm:mx-6 lg:mx-10">
        <img
            src="https://media.gettyimages.com/id/1368872054/nl/vector/online-news-search-and-reading-news-updates-news-websites-information-on-newspapers-public.jpg?s=612x612&w=0&k=20&c=iZdnx4rKpTsJiSK8tFNyE1xztBXHXpHoBEXdDdT4ZFw="
            alt="Nieuwsafbeelding"
            class="news-image w-full md:w-2/6 lg:mr-28 h-40 sm:h-60 object-cover rounded-lg shadow-md"
        />
        <div class="news-text w-full md:w-3/5">
          <h3 class="text-2xl sm:text-3xl font-extrabold mb-5 text-gray-800 dark:text-gray-100">Nederlandse Verkiezingen Zien Opkomst van Nieuwe Politieke Partijen</h3>
          <p class="mb-4 text-base sm:text-lg text-gray-600 dark:text-gray-300 leading-relaxed">
            Nederland ziet een stijging in nieuwe politieke bewegingen, die de traditionele politieke verhoudingen uitdagen. Deze opkomende partijen richten zich op thema's zoals huisvesting, klimaatverandering en immigratie, die sterk resoneren met jongere kiezers.
          </p>
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
      candidates: [],
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
        this.nextImage();
      }, 5000); // 5 sec
    },

    setActiveIndex(index) {
      this.activeIndex = index;
    },

    nextImage() {
      this.activeIndex = (this.activeIndex + 1) % this.featuredImages.length;
    },

    prevImage() {
      this.activeIndex = (this.activeIndex - 1 + this.featuredImages.length) % this.featuredImages.length;
    },
  }
};
</script>
