<template>
  <div v-if="!chartData" class="text-center">
    <p>Loading chart...</p>
  </div>
  <div v-else>
    <Bar :data="chartData" :options="chartOptions" />
  </div>
</template>

<script>
import { Bar } from 'vue-chartjs';
import { Chart, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale } from 'chart.js';

Chart.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale);

export default {
  name: 'PartyCandidateHorizontalBarChart',
  components: {
    Bar,
  },
  props: {
    chartData: Object,
  },
  data() {
    return {
      chartOptions: {
        responsive: true,
        indexAxis: 'y',
        plugins: {
          title: {
            display: true,
            text: 'Top 10 Candidates by Party',
          },
        },
        scales: {
          x: {
            beginAtZero: true,
          },
        },
      },
    };
  },
};
</script>
