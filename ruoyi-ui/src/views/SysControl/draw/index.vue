<template>
  <div id="line-chart">
    <div id="selectSection">
      <input type="date" v-model="selectedDate" @change="getChartData"/>
      <button @click="getChartData">选择日期</button>
      <div id="line-chart-graph" ref="chart"></div>
    </div>
  </div>
</template>

<script>
import { chartList } from '@/api/SysControl/data'
import * as d3 from 'd3'

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
      d3.select(this.$refs.chart).selectAll("*").remove()
    },

    createChart() {
      const margin = { top: 10, right: 30, bottom: 30, left: 50 }
      const totalWidth = 800
      const totalHeight = 400
      const width = totalWidth - margin.left - margin.right
      const height = totalHeight - margin.top - margin.bottom

      const svg = d3.select(this.$refs.chart)
        .append('svg')
        .attr('width', totalWidth)
        .attr('height', totalHeight)
        .append('g')
        .attr('transform', `translate(${margin.left}, ${margin.top})`)

      const x = d3.scaleTime()
        .domain([new Date(`${this.selectedDate}T00:00:00`), new Date(`${this.selectedDate}T24:00:00`)])
        .range([0, width])

      const y = d3.scaleLinear()
        .domain([0, d3.max(this.chartData, d => d3.max(d.data, c => c.y))])
        .nice()
        .range([height, 0])

      const xAxis = d3.axisBottom(x).tickFormat(d3.timeFormat('%H')).ticks(24)
      const yAxis = d3.axisLeft(y)

      const line = d3.line()
        .x(d => x(d.x))
        .y(d => y(d.y))

      this.chartData.forEach((series) => {
        svg.append('path')
          .datum(series.data)
          .attr('fill', 'none')
          .attr('stroke', series.color)
          .attr('stroke-width', 1.5)
          .attr('d', line)
      })

      svg.append('g')
        .attr('class', 'x-axis')
        .attr('transform', `translate(0, ${height})`)
        .call(xAxis)

      svg.append('g')
        .attr('class', 'y-axis')
        .call(yAxis)

      // 添加鼠标事件以绘制十字线并显示数据
      let focus = svg.append("g")
        .style("display", "none")

      let tooltip = d3.select("#line-chart").append("div")
        .attr("class", "tooltip")
        .style("opacity", 0)

      svg.append("rect")
        .style("fill", "none")
        .style("pointer-events", "all")
        .attr("width", width)
        .attr("height", height)
        .on("mouseover", () => { focus.style("display", null) })
        .on("mouseout", () => { focus.style("display", "none"); tooltip.transition().duration(200).style("opacity", 0); })

      // 添加圆点
      svg.selectAll(".dot")
        .data(this.chartData.flatMap(d => d.data))
        .enter().append("circle")
        .attr("class", "dot")
        .attr("r", 4)
        .attr("cx", d => x(d.x))
        .attr("cy", d => y(d.y))
        .style("fill", d => {
          if (d.label === 'Temperature') {
            return 'red';
          } else if (d.label === 'Humidity') {
            return 'blue';
          } else {
            return 'green';
          }
        })
        .on("mouseover", (event, d) => {
          tooltip.transition()
            .duration(200)
            .style("opacity", .9);
          const formatTime = d3.timeFormat("%Y-%m-%d %H:%M");
          tooltip.html(`<strong>${d.label}:</strong> ${d.y}<br/><strong>时间:</strong> ${formatTime(d.x)}`)
            .style("display", "block"); // 显示tooltip
        })
        .on("mouseout", () => {
          tooltip.transition()
            .duration(500)
            .style("opacity", 0)
            .on("end", () => {
              tooltip.style("display", "none"); // 鼠标移出时隐藏tooltip
            });
        });
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

svg {
  font-family: "Microsoft YaHei", Arial, Helvetica, sans-serif, "宋体";
}

.tooltip {
  position: fixed;
  background-color: white;
  border: 1px solid #ccc;
  border-radius: 5px;
  padding: 10px;
  pointer-events: none;
  z-index: 999; /* 确保tooltip在最顶层 */
  display: none; /* 初始化设置为隐藏 */

  /* 添加浮窗效果 */
  box-shadow: 2px 2px 6px rgba(0, 0, 0, 0.3);
}

</style>
