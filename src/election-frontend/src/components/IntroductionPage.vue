<template>
  <div class="p-6 bg-white shadow-lg rounded-md max-w-4xl mx-auto mt-8 dark:bg-gray-800 dark:shadow-md">
    <h1 class="text-3xl font-bold mb-6 text-center text-gray-800 dark:text-gray-200">
      Welkom bij Verkiezingsstatistieken
    </h1>
    <p class="text-lg text-gray-700 mb-6 dark:text-gray-300">
      Dit platform biedt gedetailleerde statistieken over de verkiezingen in Nederland. Je kunt door verschillende tabbladen in het menu aan de linkerkant navigeren om de gegevens per stemlocatie, per verkiezing, en meer te bekijken. Ontdek de trends en inzichten die we hebben verzameld en krijg diepgaande informatie over verkiezingsresultaten.
    </p>

    <div class="bg-gray-100 p-4 rounded-md mb-6 dark:bg-gray-700">
      <h2 class="text-2xl font-semibold text-gray-800 mb-4 dark:text-gray-200">Hoe te navigeren:</h2>
      <ul class="list-disc pl-6 text-gray-700 dark:text-gray-300">
        <li><strong>Per Stemlocatie:</strong> Bekijk gedetailleerde resultaten per stemlocatie, inclusief het aantal stemmen en de leidende partij.</li>
        <li><strong>Per Verkiezing:</strong> Een overzicht van de verkiezingsresultaten door de jaren heen, inclusief de behaalde zetels per partij.</li>
        <li><strong>Zetels Per Jaar:</strong> Analyseer de trends in de zetelverdeling per jaar om politieke verschuivingen te begrijpen.</li>
        <li><strong>Zetels Per Partij:</strong> Ontdek hoe de zetelverdeling per partij is veranderd over de jaren heen en vergelijk de prestaties van verschillende partijen.</li>
      </ul>
    </div>

    <div class="bg-blue-50 p-4 rounded-md mb-6 dark:bg-blue-900">
      <h3 class="text-xl font-semibold text-gray-800 mb-4 dark:text-gray-200">Waarom deze gegevens belangrijk zijn:</h3>
      <p class="text-gray-700 dark:text-gray-300">
        Verkiezingsstatistieken bieden waardevolle inzichten in de politieke trends en de verschuivingen in de publieke opinie. Door deze gegevens te analyseren, kun je beter begrijpen hoe de politieke situatie in Nederland zich ontwikkelt. Of je nu een politiek geïnteresseerde bent of gewoon nieuwsgierig naar de resultaten van de afgelopen verkiezingen, dit platform biedt alles wat je nodig hebt.
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