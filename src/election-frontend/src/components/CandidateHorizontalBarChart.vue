<template>
  <div v-if="!chartData" class="text-center">
    <p>Loading chart...</p>
  </div>
  <div v-else>
    <Bar :data="chartData" :options="chartOptions" />
  </div>
</template>

<script>
import {Bar} from 'vue-chartjs';
import {BarElement, CategoryScale, Chart, Legend, LinearScale, Title, Tooltip} from 'chart.js';

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
      chartOptions: this.getChartOptions(),
    };
  },
  methods: {
    isDarkMode() {
      return localStorage.getItem("isDarkMode") === "true";
    },
    getChartOptions() {
      const isDark = this.isDarkMode();
      const textColor = isDark ? '#BDC3C7' : '#7F8C8D';
      const gridColor = isDark ? '#555' : '#ddd';

      return {
        responsive: true,
        indexAxis: 'y',  // Horizontal bar chart
        plugins: {
          title: {
            display: true,
            text: 'Top 10 Candidates by Party',
            font: {
              size: 18,
              family: 'Arial, sans-serif',
            },
            color: textColor,
          },
          tooltip: {
            titleColor: textColor,
            bodyColor: textColor,
          },
          legend: {
            labels: {
              color: textColor,
            },
          },
        },
        scales: {
          x: {
            beginAtZero: true,
            grid: {
              color: gridColor,
            },
            ticks: {
              color: textColor,
            },
          },
          y: {
            grid: {
              color: gridColor,
            },
            ticks: {
              color: textColor,
            },
          },
        },
      };
    },
  },
  watch: {
    'isDarkMode': function () {
      this.chartOptions = this.getChartOptions();
    },
  },
};
</script>
