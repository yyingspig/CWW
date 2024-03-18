<template>
  <div id="line-chart">
    <div id="selectSection">
      <input type="date" v-model="selectedDate" @change="getChartData"/>
      <button @click="getChartData">选择日期</button>
      <div class="echarts-chart" ref="chart"></div>
    </div>
  </div>
</template>

<script>
import { chartList } from '@/api/SysControl/data'
import * as echarts from '@/lib/echarts'

export default {
  data() {
    return {
      chartData: null,
      selectedDate: new Date().toISOString().substr(0, 10),
    }
  },

  mounted() {
    this.getChartData()
  },

  methods: {
    async getChartData() {
      try {
        const response = await chartList()

        const sensorDataList = response.data

        const selectedData = sensorDataList.filter(data => data.time.includes(this.selectedDate))

        const temperatureData = selectedData.filter(data => data.temperature).map(data => {
          return { x: new Date(data.time), y: data.temperature, label: "Temperature" };
        });

        const humidityData = selectedData.filter(data => data.humidity).map(data => {
          return { x: new Date(data.time), y: data.humidity, label: "Humidity" };
        });

        const pressureData = selectedData.filter(data => data.servo).map(data => {
          return { x: new Date(data.time), y: data.servo, label: "Pressure" };
        });


        this.chartData = [
          {
            label: 'Temperature',
            data: temperatureData,
            color: 'red'
          },
          {
            label: 'Humidity',
            data: humidityData,
            color: 'blue'
          },
          {
            label: 'Pressure',
            data: pressureData,
            color: 'green'
          }
        ]

        this.clearChart()  // 清除之前的折线图
        this.createChart()
      } catch (error) {
        console.error(error)
      }
    },

    clearChart() {
      if (this.chartInstance) {
        this.chartInstance.dispose();
      }
    },

    createChart() {
      this.chartInstance = echarts.init(this.$refs.chart);

      const option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            animation: false
          }
        },
        xAxis: {
          type: 'time',
          boundaryGap: false,
          splitLine: {
            show: false
          }
        },
        yAxis: {
          type: 'value',
          boundaryGap: ['0', '100%'],
          splitLine: {
            show: true
          }
        },
        series: this.chartData.map(series => ({
          name: series.label,
          type: 'line',
          showSymbol: true,
          hoverAnimation: false,
          data: series.data.map(d => ([d.x, d.y])),
          lineStyle: {
            color: series.color
          }
        }))
      };

      this.chartInstance.setOption(option);
    }
  }
}
</script>

<style scoped>
#selectSection {
  margin-top: 1.25rem;
}

input[type="date"] {
  color: #273747;
  font-family: helvetica;
  font-size: 0.875rem;
  width: 8.125rem;
  height: 1.75rem;
  border: 0.125rem solid rgb(43, 189, 218);
  appearance: none;
  background-color: rgb(235, 239, 242);
  padding-left: 0.3125rem;
}

button {
  color: white;
  background-color: #4CAF50;
  border: none;
  border-radius: 4px;
  padding: 10px 20px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
}

.echarts-chart {
  width: 800px;
  height: 400px;
}
</style>
