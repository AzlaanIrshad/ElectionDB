<template>
  <div class="homepage bg-gray-100 dark:bg-gray-900 font-sans text-gray-900 dark:text-gray-100">
    <!-- Hero Sectie -->
    <section class="relative max-h-[80vh] h-screen flex items-center justify-center">
      <div
          class="relative w-full h-full bg-cover bg-center shadow-lg flex flex-col md:flex-row"
          style="background-image: url('https://r.mobirisesite.com/963736/assets/images/photo-1631039302236-14327f30683a.jpeg');"
      >
        <!-- Overlay voor leesbaarheid -->
        <div class="absolute inset-0 bg-black bg-opacity-30"></div>

        <!-- Linker Content (geplaatst naar onderen links) -->
        <div class="absolute bottom-8 left-8 z-10 bg-white bg-opacity-90 p-8 rounded-lg shadow-md max-w-lg">
          <p class="text-lg font-medium text-gray-600 mb-2">Your Election Hub</p>
          <h1 class="text-3xl md:text-5xl font-extrabold text-gray-900 mb-4 leading-tight">
            Election Results
          </h1>
          <p class="text-sm md:text-base text-gray-700 mb-6">
            Stay informed on election outcomes. Explore results, maps, and real-time updates from the world of politics.
          </p>
          <a
              href="/"
              class="bg-blue-600 text-white px-4 py-2 rounded-full text-sm md:text-base font-medium hover:bg-blue-700 transition duration-300"
          >
            Explore Now
          </a>
        </div>

        <!-- Rechter Content: Kaarten (blijft rechts) -->
        <div class="absolute top-0 right-0 p-8 flex flex-col space-y-6 w-full md:w-1/2">
          <!-- Kaart 1 -->
          <div class="bg-white bg-opacity-90 p-6 rounded-lg shadow-md border border-gray-200">
            <span class="text-2xl md:text-3xl font-bold text-gray-500">01</span>
            <h2 class="text-sm md:text-lg font-bold text-gray-800 mb-1">Interactive Maps</h2>
            <p class="text-xs md:text-sm text-gray-600">
              Navigate through our dynamic maps that visualize election results by region. It’s like Google Maps, but for democracy!
            </p>
          </div>

          <!-- Kaart 2 -->
          <div class="bg-white bg-opacity-90 p-6 rounded-lg shadow-md border border-gray-200">
            <span class="text-2xl md:text-3xl font-bold text-gray-500">02</span>
            <h2 class="text-sm md:text-lg font-bold text-gray-800 mb-1">Vote Analysis</h2>
            <p class="text-xs md:text-sm text-gray-600">
              Understand voter behavior with in-depth analytics.
            </p>
          </div>

          <!-- Kaart 3 -->
          <div class="bg-white bg-opacity-90 p-6 rounded-lg shadow-md border border-gray-200">
            <span class="text-2xl md:text-3xl font-bold text-gray-500">03</span>
            <h2 class="text-sm md:text-lg font-bold text-gray-800 mb-1">Election News</h2>
            <p class="text-xs md:text-sm text-gray-600">
              Stay updated with real-time election headlines.
            </p>
          </div>
          <div class="bg-white bg-opacity-90 p-6 rounded-lg shadow-md border border-gray-200">
            <span class="text-2xl md:text-3xl font-bold text-gray-500">03</span>
            <h2 class="text-sm md:text-lg font-bold text-gray-800 mb-1">Election News</h2>
            <p class="text-xs md:text-sm text-gray-600">
              Stay updated with real-time election headlines.
            </p>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>
