<template>
  <div class="flex flex-col items-center text-center p-6 min-h-screen bg-gray-100 dark:bg-gray-900">
    <!-- Title -->
    <h2 class="text-gray-900 dark:text-gray-100 text-5xl font-extrabold mb-8 mt-6">About Our Project</h2>

    <!-- Project Introduction -->
    <div class="flex flex-col md:flex-row justify-center items-center mb-10 p-8 bg-white dark:bg-gray-800 rounded-xl shadow-lg transition-transform duration-200 hover:scale-105 hover:shadow-2xl w-full md:w-3/4 lg:w-2/3">
      <img class="w-72 h-auto m-4 rounded-lg object-cover" src="https://images.pexels.com/photos/3184456/pexels-photo-3184456.jpeg" alt="Project Overview">
      <div class="p-4 max-w-md">
        <h3 class="text-2xl font-bold text-gray-800 dark:text-gray-100 mb-4">What Our Project Offers</h3>
        <p class="text-gray-700 dark:text-gray-300 text-lg leading-relaxed">
          Our project is a comprehensive platform for viewing and discussing election results.
          We aim to make election data accessible and create a space for meaningful conversations
          and insights. Users can explore results, create threads for discussion, and engage with others.
        </p>
      </div>
    </div>

    <!-- Election Results Section -->
    <div class="flex flex-col md:flex-row-reverse justify-center items-center mb-10 p-8 bg-white dark:bg-gray-800 rounded-xl shadow-lg transition-transform duration-200 hover:scale-105 hover:shadow-2xl w-full md:w-3/4 lg:w-2/3">
      <img class="w-72 h-auto m-4 rounded-lg object-cover" src="https://images.pexels.com/photos/3184357/pexels-photo-3184357.jpeg" alt="Election Results">
      <div class="p-4 max-w-md">
        <h3 class="text-2xl font-bold text-gray-800 dark:text-gray-100 mb-4">View Election Results</h3>
        <p class="text-gray-700 dark:text-gray-300 text-lg leading-relaxed">
          Stay up-to-date with the latest election results through our dynamic dashboard.
          We provide accurate and detailed data, making it easy to follow the outcome of elections
          and analyze the results in depth.
        </p>
      </div>
    </div>

    <!-- Discussion & Threads Section -->
    <div class="flex flex-col md:flex-row justify-center items-center mb-10 p-8 bg-white dark:bg-gray-800 rounded-xl shadow-lg transition-transform duration-200 hover:scale-105 hover:shadow-2xl w-full md:w-3/4 lg:w-2/3">
      <img class="w-72 h-auto m-4 rounded-lg object-cover" src="https://images.pexels.com/photos/3184298/pexels-photo-3184298.jpeg" alt="Discussion Threads">
      <div class="p-4 max-w-md">
        <h3 class="text-2xl font-bold text-gray-800 dark:text-gray-100 mb-4">Engage in Discussions</h3>
        <p class="text-gray-700 dark:text-gray-300 text-lg leading-relaxed">
          Our platform allows users to create discussion threads and share their views on election topics.
          Connect with like-minded individuals, exchange ideas, and delve deeper into the implications
          of the election results in real-time.
        </p>
      </div>
    </div>

    <!-- Team Members Section -->
    <div class="text-center mb-10">
      <h3 class="text-3xl font-bold text-gray-800 dark:text-gray-100 mb-6">Meet Our Team</h3>
      <div class="flex flex-wrap justify-center gap-6">
        <div v-for="member in teamMembers" :key="member.name" class="flex flex-col items-center m-4 p-6 bg-white dark:bg-gray-800 rounded-lg shadow-lg w-64 transition-transform duration-300 hover:-translate-y-2 hover:shadow-2xl">
          <img :src="member.photo" :alt="member.name" class="w-36 h-36 rounded-full object-cover mb-4 transition-transform duration-300 hover:scale-105">
          <h4 class="text-lg font-semibold text-gray-800 dark:text-gray-100">{{ member.name }}</h4>
          <p class="text-gray-600 dark:text-gray-300 mb-2 text-sm">{{ member.position }}</p>
          <div class="mt-2">
            <a :href="member.linkedin" target="_blank" class="inline-block p-2 rounded-full bg-blue-600 dark:bg-blue-800 text-white hover:bg-blue-800 dark:hover:bg-blue-900 transition-colors duration-300">
              <svg xmlns="http://www.w3.org/2000/svg" class="w-6 h-6" fill="currentColor" viewBox="0 0 24 24">
                <path d="M19 0h-14c-2.761 0-5 2.239-5 5v14c0 2.761 2.239 5 5 5h14c2.762 0 5-2.239 5-5v-14c0-2.761-2.238-5-5-5zm-11 19h-3v-11h3v11zm-1.5-12.268c-.966 0-1.75-.79-1.75-1.764s.784-1.764 1.75-1.764 1.75.79 1.75 1.764-.783 1.764-1.75 1.764zm13.5 12.268h-3v-5.604c0-3.368-4-3.113-4 0v5.604h-3v-11h3v1.765c1.396-2.586 7-2.777 7 2.476v6.759z"/>
              </svg>
            </a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "AboutPage",
  data() {
    return {
      teamMembers: [
        {
          name: "Ersin Karaduman",
          position: "Software Engineer",
          photo: "https://media.licdn.com/dms/image/v2/C4E03AQHVghLly2-iHA/profile-displayphoto-shrink_200_200/profile-displayphoto-shrink_200_200/0/1647638165291?e=1736985600&v=beta&t=MzW5JK70zHH8hBU1__kJWazAPHPdsegmPghoYRi3T84",
          linkedin: "https://www.linkedin.com/in/ersin-karaduman/",
        },
        {
          name: "Azlaan Irshad",
          position: "Software Engineer",
          photo: "https://media.licdn.com/dms/image/v2/C4E03AQHYZPvErwWwyA/profile-displayphoto-shrink_200_200/profile-displayphoto-shrink_200_200/0/1632222645220?e=1736985600&v=beta&t=8aJ-xpNfLxJLIQ3bLjnZvZZg2vbIReNetDAi-JUCPkY",
          linkedin: "https://www.linkedin.com/in/azlaan-irshad/",
        },
        {
          name: "Erdem Berk Sahan",
          position: "Software Engineer",
          photo: "https://media.licdn.com/dms/image/v2/D4E03AQFKnprwnVtS4w/profile-displayphoto-shrink_200_200/profile-displayphoto-shrink_200_200/0/1731313043624?e=1736985600&v=beta&t=tWoCnMpTltf_TKo2J0l3NQf_URjNR60FjdGIrl2-cWQ",
          linkedin: "https://www.linkedin.com/in/erdem-sahan-1b0561334/",
        },
      ],
    };
  },
};
</script>
