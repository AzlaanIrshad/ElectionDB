<template>
  <div class="charts-container max-w-full sm:max-w-7xl mx-auto bg-white dark:bg-gray-700 p-6 rounded-lg">
    <!-- Dropdowns voor partijselectie -->
    <div class="mb-6">
      <label class="block font-bold mb-2 text-gray-800 dark:text-gray-100">
        Selecteer Partijen:
      </label>
      <div class="flex flex-col gap-4">
        <div v-for="(party, index) in selectedParties" :key="index" class="flex items-center gap-2">
          <select
              v-model="selectedParties[index]"
              @change="updateLineChartData"
              class="bg-white dark:bg-gray-900 border border-gray-300 dark:border-gray-700 rounded px-2 py-1 flex-1"
          >
            <option
                v-for="partyOption in availableParties"
                :key="partyOption"
                :value="partyOption"
            >
              {{ partyOption }}
            </option>
          </select>
          <button
              v-if="index > 0"
              @click="removeParty(index)"
              class="text-red-500 hover:text-red-700"
          >
            Verwijderen
          </button>
        </div>
      </div>
      <!-- max 4 partijen -->
      <button
          v-if="selectedParties.length < 4"
          @click="addParty"
          class="mt-4 flex items-center justify-center px-3 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition duration-200"
      >
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 mr-2">
          <path stroke-linecap="round" stroke-linejoin="round" d="M12 4.5v15m7.5-7.5h-15" />
        </svg>
        Voeg een partij toe om te vergelijken
      </button>
    </div>

    <!-- Line Chart voor Zetels per Partij over de jaren -->
    <div class="chart-container">
      <h2 class="text-lg md:text-xl font-bold text-center mb-4 text-gray-800 dark:text-gray-100">
        Zetels per Partijen (Vergelijking)
      </h2>
      <Chart
          v-if="lineChartData"
          :type="'line'"
          :data="lineChartData"
          :options="lineChartOptions"
          class="max-w-4xl mx-auto"
      />
    </div>
  </div>
</template>

<script>
import Chart from "primevue/chart";
import config from "../config";

export default {
  name: "PartySeats",
  components: { Chart },
  data() {
    return {
      seatData: {},
      lineChartData: null,
      selectedParties: [],
      availableYears: [2010, 2012, 2017, 2021, 2023],
      availableParties: [],
    };
  },
  mounted() {
    this.fetchPartySeatsData();
  },
  methods: {
    async fetchPartySeatsData() {
      try {
        const response = await fetch(
            `${config.apiBaseUrl}/api/seat-allocation?years=${this.availableYears.join(",")}`
        );
        if (!response.ok) throw new Error("Ophalen van zetels per partij mislukt");
        const seatData = await response.json();

        this.seatData = seatData;

        const allParties = Object.keys(seatData[this.availableYears[0]] || {});
        this.availableParties = allParties.filter((party) =>
            this.availableYears.some((year) => seatData[year]?.[party] > 0)
        );

        if (this.availableParties.length > 0) {
          this.selectedParties = [this.availableParties[0]];
        }

        this.updateLineChartData();
      } catch (err) {
        console.error("Fout bij het ophalen van zetels per partij:", err);
      }
    },
    updateLineChartData() {
      if (!this.selectedParties.length || !this.seatData) return;

      this.lineChartData = {
        labels: this.availableYears,
        datasets: this.selectedParties.map((party) => ({
          label: party,
          data: this.availableYears.map(
              (year) => (this.seatData[year]?.[party] ?? 0)
          ),
          borderColor: this.getRandomColor(),
          tension: 0.1,
        })),
      };
    },
    addParty() {
      const nextParty = this.availableParties.find(
          (party) => !this.selectedParties.includes(party)
      );
      if (nextParty) {
        this.selectedParties.push(nextParty);
        this.updateLineChartData();
      } else {
        console.log("Geen extra partijen beschikbaar om toe te voegen.");
      }
    },
    removeParty(index) {
      this.selectedParties.splice(index, 1);
      this.updateLineChartData();
    },
    getRandomColor() {
      return `hsl(${Math.random() * 360}, 70%, 50%)`;
    },
  },
};
</script>
