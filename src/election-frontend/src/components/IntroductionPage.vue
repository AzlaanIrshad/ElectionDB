<template>
  <div class="p-6 bg-white shadow-lg rounded-md max-w-4xl mx-auto mt-8 dark:bg-gray-800 dark:shadow-md">
    <h1 class="text-3xl font-bold mb-6 text-center text-gray-800 dark:text-gray-200">
      Introductie tot Verkiezingsstatistieken
    </h1>
    <p class="text-lg text-gray-700 mb-6 dark:text-gray-300 text-center">
      Ontdek diepgaande inzichten in de Nederlandse verkiezingsresultaten. Van interactieve kaarten tot trendanalyses, deze applicatie biedt een uniek perspectief op de prestaties van politieke partijen en hun zetelverdeling door de jaren heen.
    </p>

    <div class="bg-gray-100 p-4 rounded-md mb-6 dark:bg-gray-700">
      <h2 class="text-2xl font-semibold text-gray-800 mb-4 dark:text-gray-200 text-center">Hoe te navigeren:</h2>
      <ul class="list-disc pl-6 text-gray-700 dark:text-gray-300">
        <li><strong>Per Stemlocatie:</strong> Bekijk interactieve verkiezingsresultaten per stad op een kaart, inclusief leidende partijen en aantal stemmen.</li>
        <li><strong>Per Verkiezing:</strong> Een visuele weergave van de verkiezingsresultaten per jaar, met een overzicht van het totale aantal stemmen per partij.</li>
        <li><strong>Zetels Per Jaar:</strong> Zoek snel naar een partij en bekijk details over hun zetels en prestaties per jaar.</li>
        <li><strong>Zetels Per Partij:</strong> Vergelijk de zetelverdeling van verschillende partijen door de jaren heen en analyseer hun prestaties in meerdere verkiezingen.</li>
      </ul>
    </div>

    <div class="bg-blue-50 p-4 rounded-md mb-6 dark:bg-blue-900">
      <h3 class="text-xl font-semibold text-gray-800 mb-4 dark:text-gray-200 text-center">Waarom deze gegevens belangrijk zijn:</h3>
      <p class="text-gray-700 dark:text-gray-300 text-center">
        Verkiezingsstatistieken geven een beter begrip van politieke trends en veranderingen in het stemgedrag van burgers. Door data te visualiseren, kun je eenvoudig analyseren hoe partijen groeien of dalen in populariteit en hoe zetels door de jaren heen verdeeld zijn.
      </p>
    </div>

    <div class="text-center">
      <router-link
          to="/election-statistiek/per-stemlocatie"
          class="px-6 py-3 bg-blue-600 text-white rounded-lg shadow-lg hover:bg-blue-700 dark:bg-blue-700 dark:hover:bg-blue-800 dark:text-gray-200 transition duration-300"
      >
        Begin met Verkennen
      </router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: "IntroductionPage",
};
</script>