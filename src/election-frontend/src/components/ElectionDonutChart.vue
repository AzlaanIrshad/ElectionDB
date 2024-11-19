<template>
  <div class="chart-container m-auto max-w-lg bg-white dark:bg-gray-700">
    <h2 class="text-xl font-bold text-center mb-4">Total Votes Distribution</h2>
    <Chart :type="'doughnut'" :data="chartData" :options="chartOptions" />
  </div>
</template>

<script>
import Chart from "primevue/chart";

export default {
  name: "ElectionDonutChart",
  components: { Chart },
  data() {
    return {
      chartData: null,
      chartOptions: null,
    };
  },
  mounted() {
    this.fetchElectionResults();
  },
  methods: {
    async fetchElectionResults() {
      try {
        const response = await fetch("http://localhost:8080/api/election-results");
        if (!response.ok) throw new Error("Failed to fetch election results");
        const electionData = await response.json();

        // Berekening van de totale stemmen per partij
        const partyVotes = {};
        electionData.forEach((transaction) => {
          const contests = transaction?.count?.election?.contests?.contests || [];
          contests.forEach((contest) => {
            contest.totalVotes.selections.forEach((selection) => {
              const party = selection.affiliationIdentifier?.registeredName;
              const votes = selection.validVotes || 0;
              if (party) {
                if (!partyVotes[party]) {
                  partyVotes[party] = 0;
                }
                partyVotes[party] += votes;
              }
            });
          });
        });

        // Data voor donut chart
        const labels = Object.keys(partyVotes);
        const data = Object.values(partyVotes);
        const backgroundColors = labels.map(
            (_, index) => `hsl(${(index / labels.length) * 360}, 70%, 70%)`
        );

        this.chartData = {
          labels,
          datasets: [
            {
              data,
              backgroundColor: backgroundColors,
            },
          ],
        };

        this.chartOptions = {
          responsive: true,
          plugins: {
            legend: {
              position: "top",
            },
          },
        };
      } catch (err) {
        console.error("Error fetching election results:", err);
      }
    },
  },
};
</script>