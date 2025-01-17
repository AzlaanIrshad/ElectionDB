<template>
  <div class="homepage bg-gray-100 dark:bg-gray-900 font-sans text-gray-900 dark:text-gray-100">
    <!-- Hero Sectie -->
    <section class="relative flex items-center justify-center" :style="{ height: `calc(100vh - 4rem)` }">
      <div
          class="relative w-full h-full bg-cover bg-center shadow-lg flex flex-col lg:flex-row"
          style="background-image: url('https://www.tweedekamer.nl/sites/default/files/styles/huge/public/2024-11/20240918%20APB%20dag%201%20.JPG?itok=bx6EaQ_-');"
      >
        <div class="absolute inset-0 bg-black bg-opacity-30"></div>

        <!-- Informatie boven de routes, dichter bij boven -->
        <div class="mt-16 absolute top-1/6 left-1/2 transform -translate-x-1/2 -translate-y-1/6 z-10 text-center p-6 sm:p-8 rounded-lg shadow-md max-w-[40rem] w-full bg-opacity-90 bg-gray-800">
          <p class="text-sm sm:text-base font-medium text-white mb-2">Jouw Election Hub</p>
          <h1 class="text-3xl sm:text-4xl md:text-5xl font-bold text-white mb-4 leading-tight">
            Welkom bij ElectionDB
          </h1>
          <p class="text-sm sm:text-base text-white mb-6">
            Blijf op de hoogte met VerkiezingsDB 2024, jouw ultieme platform voor real-time verkiezingsupdates en deskundige analyses.
          </p>
          <a
              href="/dashboard"
              class="bg-blue-600 text-white dark:text-white px-4 py-2 rounded-full text-sm sm:text-base font-medium hover:bg-gray-900 transition duration-300 border border-gray-700"
          >
            Dashboard
          </a>
        </div>

        <!-- Routes onder de bovenste tekst-->
        <div class="absolute top-1/2 left-0 right-0 p-4 sm:p-6 lg:p-8 flex flex-wrap justify-between gap-6 hidden lg:block">
          <div class="flex w-full justify-around gap-6 lg:flex-row flex-col">
            <router-link
                to="/election-statistiek/per-stemlocatie"
                class="flex flex-col bg-opacity-100 p-4 sm:p-6 rounded-lg shadow-md bg-gray-800 text-white w-full sm:w-48 lg:w-1/6 h-max transform transition-transform duration-300 hover:scale-105"
            >
              <span class="text-2xl sm:text-3xl font-bold pb-1">01</span>
              <h2 class="text-sm sm:text-base md:text-lg font-bold dark:text-white mb-1">Interactieve Kaarten</h2>
              <p class="text-xs sm:text-sm dark:text-white">
                Navigeer door onze dynamische kaart die verkiezingsresultaten per stad weergeeft en de leidende partij van de verkiezingen zien .
              </p>
            </router-link>

            <router-link
                to="/election-statistiek/per-verkiezing"
                class="flex flex-col bg-opacity-100 p-4 sm:p-6 rounded-lg shadow-md bg-gray-800 text-white w-full sm:w-48 lg:w-1/6 h-max transform transition-transform duration-300 hover:scale-105"
            >
              <span class="text-2xl sm:text-3xl font-bold pb-2">02</span>
              <h2 class="text-sm sm:text-base md:text-lg font-bold text-white mb-1">Totaal Stemmen Nederland</h2>
              <p class="text-xs sm:text-sm text-white">
                Zie hier de totale stemmen per verkiezingsjaar in Nederland van meerdere jaren.
              </p>
            </router-link>

            <router-link
                to="/election-statistiek/zetels-per-partij"
                class="flex flex-col bg-opacity-100 p-4 sm:p-6 rounded-lg shadow-md bg-gray-800 text-white w-full sm:w-48 lg:w-1/6 h-max transform transition-transform duration-300 hover:scale-105"
            >
              <span class="text-2xl sm:text-3xl font-bold pb-2">03</span>
              <h2 class="text-sm sm:text-base md:text-lg font-bold text-white mb-1">Aantal Zetels Per Partij</h2>
              <p class="text-xs sm:text-sm text-white">
                Hier vind je de zetels per partij over de jaren heen en hoe deze zich hebben ontwikkeld.
              </p>
            </router-link>

            <router-link
                to="/election-statistiek/zetels-per-jaar"
                class="flex flex-col bg-opacity-100 p-4 sm:p-6 rounded-lg shadow-md bg-gray-800 text-white w-full sm:w-48 lg:w-1/6 h-max transform transition-transform duration-300 hover:scale-105"
            >
              <span class="text-2xl sm:text-3xl font-bold pb-2">04</span>
              <h2 class="text-sm sm:text-base md:text-lg font-bold text-white mb-1">Aantal Zetels Per Jaar</h2>
              <p class="text-xs sm:text-sm text-white">
                Hier vind hoeveel zetels er per jaar zijn behaald en hoe deze zich hebben ontwikkeld.
              </p>
            </router-link>
          </div>
        </div>
      </div>
    </section>

    <!-- Categorieën Sectie -->
    <section class="categories py-10 mb-10 bg-white dark:bg-gray-800 rounded-lg shadow-lg mt-10 mx-2 lg:mx-5">
      <h2 class="text-3xl lg:text-4xl text-center mb-8 font-extrabold text-gray-800 dark:text-gray-100">Verkiezingscategorieën</h2>
      <div class="blocks flex flex-col md:flex-row justify-around gap-4 lg:gap-6 mx-4 sm:mx-6 lg:mx-10">
        <div class="block w-full md:w-1/4 h-36 sm:h-44 flex justify-center items-center bg-blue-600 dark:bg-blue-700 text-center rounded-lg shadow-md cursor-pointer hover:bg-blue-800 dark:hover:bg-blue-600 transition-all text-white transform hover:scale-105">
          <a href="/threads"><span class="font-bold text-xl lg:text-2xl">Election Discussies</span></a>
        </div>
        <div class="block w-full md:w-1/4 h-36 sm:h-44 flex justify-center items-center bg-blue-600 dark:bg-blue-700 text-center rounded-lg shadow-md cursor-pointer hover:bg-blue-800 dark:hover:bg-blue-600 transition-all text-white transform hover:scale-105">
          <a href="/dashboard"><span class="font-bold text-xl lg:text-2xl">Resultaten per Staat</span></a>
        </div>
        <div class="block w-full md:w-1/4 h-36 sm:h-44 flex justify-center items-center bg-blue-600 dark:bg-blue-700 text-center rounded-lg shadow-md cursor-pointer hover:bg-blue-800 dark:hover:bg-blue-600 transition-all text-white transform hover:scale-105">
          <a href="/"><span class="font-bold text-xl lg:text-2xl">Kandidatenprofielen</span></a>
        </div>
      </div>
    </section>

    <!-- Countdown Timer Section -->
    <section class="countdown-section py-10 mb-10 bg-white dark:bg-gray-800 rounded-lg shadow-lg mt-10 mx-2 lg:mx-5">
      <div class="countdown-container text-center px-4 sm:px-8 lg:px-16">
        <h2 class="text-2xl sm:text-3xl lg:text-4xl font-extrabold mb-6 text-gray-800 dark:text-gray-100">
          Tijd tot de volgende verkiezingen
        </h2>
        <div class="flex justify-center flex-wrap gap-6">
          <div class="countdown-item bg-gray-100 dark:bg-gray-700 px-6 py-4 rounded-lg shadow-md">
        <span class="text-4xl sm:text-5xl font-bold text-blue-600 dark:text-blue-400">
          {{ countdown.days }}
        </span>
            <p class="text-sm sm:text-base font-medium text-gray-700 dark:text-gray-300">Dagen</p>
          </div>
          <div class="countdown-item bg-gray-100 dark:bg-gray-700 px-6 py-4 rounded-lg shadow-md">
        <span class="text-4xl sm:text-5xl font-bold text-blue-600 dark:text-blue-400">
          {{ countdown.hours }}
        </span>
            <p class="text-sm sm:text-base font-medium text-gray-700 dark:text-gray-300">Uren</p>
          </div>
          <div class="countdown-item bg-gray-100 dark:bg-gray-700 px-6 py-4   shadow-md">
        <span class="text-4xl sm:text-5xl font-bold text-blue-600 dark:text-blue-400">
          {{ countdown.minutes }}
        </span>
            <p class="text-sm sm:text-base font-medium text-gray-700 dark:text-gray-300">Minuten</p>
          </div>
          <div class="countdown-item bg-gray-100 dark:bg-gray-700 px-6 py-4 rounded-lg shadow-md">
        <span class="text-4xl sm:text-5xl font-bold text-blue-600 dark:text-blue-400">
          {{ countdown.seconds }}
        </span>
            <p class="text-sm sm:text-base font-medium text-gray-700 dark:text-gray-300">Seconden</p>
          </div>
        </div>
      </div>
    </section>

    <!-- Logo Carousel -->
    <section class="logo py-12 bg-white dark:bg-gray-800 rounded-lg shadow-lg mx-2 lg:mx-5 mb-10">
    <h2 class="text-3xl lg:text-4xl text-center mb-8 font-extrabold text-gray-800 dark:text-gray-100">Top Politieke Partijen</h2>
    <section class="categories py-8 mb-10 bg-white dark:bg-gray-800 rounded-lg shadow-lg mt-10 mx-auto max-w-7xl relative">
      <div class="logo-carousel overflow-hidden w-full h-28 sm:h-32">
        <div class="logo-track flex items-center space-x-4">
           <!-- azlaan freaky -->
          <div
              v-for="(image, index) in [...featuredImages, ...featuredImages]"
              :key="index"
              class="tile w-24 sm:w-32 h-24 sm:h-32 text-center flex-shrink-0 p-2"
          >
            <img :src="image" alt="Thumbnail" class="w-full h-full object-contain rounded-lg" />
          </div>
        </div>
      </div>
      <!-- Schaduw op de zijkanten -->
      <div class="absolute top-0 left-0 w-8 h-full bg-gradient-to-r from-gray-300 via-transparent dark:from-gray-900"></div>
      <div class="absolute top-0 right-0 w-8 h-full bg-gradient-to-l from-gray-300 via-transparent dark:from-gray-900"></div>
    </section>
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
            Nederland beleeft een fascinerend verkiezingsjaar waarin nieuwkomers in de politiek hun stempel drukken op het democratische proces.
          </p>
          <button
              class="bg-blue-600 text-white px-6 py-2 rounded-full text-sm sm:text-base font-medium hover:bg-blue-700 transition duration-300"
          >
            Lees meer
          </button>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import config from "../config";

// Dynamisch importeren van alle logos
const partyLogos = import.meta.glob('@/assets/partyLogo/*.webp', { eager: true });

export default {
  data() {
    return {
      featuredImages: Object.values(partyLogos).map(logo => logo.default),

      countdown: {
        days: 0,
        hours: 0,
        minutes: 0,
        seconds: 0
      }
    };
  },
  methods: {
    async fetchCountdown() {
      try {
        const response = await fetch(`${config.apiBaseUrl}/api/countdown`);
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        const data = await response.json();
        this.countdown = {
          days: data.days,
          hours: data.hours,
          minutes: data.minutes,
          seconds: data.seconds
        };
      } catch (error) {
        console.error('Error fetching countdown:', error);
      }
    },
    startCountdown() {
      this.fetchCountdown();
      setInterval(this.fetchCountdown, 1000);
    }
  },
  mounted() {
    this.startCountdown();
  }
};
</script>