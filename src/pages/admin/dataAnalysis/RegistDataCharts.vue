<template>
  <div>
    <el-date-picker
      v-model="DateValues"
      type="daterange"
      start-placeholder="开始日期"
      end-placeholder="结束日期"
      format="yyyy / MM / dd"
      value-format="yyyy/MM/dd"
    ></el-date-picker>
    <el-button
      type="primary"
      round
      size="small"
      style="margin-left:10px"
      @click="getRegistrationData"
    >确定</el-button>
    <div id="chart_registration_data" style="width:800px;height:400px"></div>
  </div>
</template>
<style scoped>
</style>
<script>
import echarts from "echarts";
import { getRegistration } from "../../../app/apis/dataAnalysisApi";
export default {
  data() {
    return {
      DateValues: "",
      ourData: [],
      chart: null
    };
  },
  methods: {
    getRegistrationData: function() {
      const _this = this;
      let startDate = this.DateValues[0];
      let endDate = this.DateValues[1];
      let DateRange = {
        beginDay: startDate,
        endDay: endDate
      };
      let dateArr = new Array();
      let numArr = new Array();
      getRegistration(DateRange, response => {
        console.log(response);
        this.ourData = response.data;
        for (let i = 0; i < this.ourData.registers.length; i++) {
          dateArr.push(this.ourData.registers[i].date);
          numArr.push(this.ourData.registers[i].number);
        }
        // 基于准备好的dom，初始化echarts实例
        _this.chart = echarts.init(
          document.getElementById("chart_registration_data")
        );
        // 绘制图表
        _this.chart.setOption({
          title: {},
          tooltip: {},
          xAxis: {
            data: dateArr,
            grid: {
              buttom: "0%"
            }
          },
          yAxis: {},
          series: [
            {
              name: "注册数（已激活）",
              type: "bar",
              data: numArr,
              label: {
                show: true
              },
              barWidth: "40%",
              itemStyle: {
                normal: {
                  color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                    {
                      offset: 0,
                      color: "#fccb05"
                    },
                    {
                      offset: 1,
                      color: "#f5804d"
                    }
                  ]),
                  barBorderRadius: 12
                }
              }
            }
          ],
          dataZoom: [
            {
              show: true,
              height: 20,
              xAxisIndex: [0],
              bottom: "5%",
              start: 10,
              end: 90,
              handleIcon:
                "path://M306.1,413c0,2.2-1.8,4-4,4h-59.8c-2.2,0-4-1.8-4-4V200.8c0-2.2,1.8-4,4-4h59.8c2.2,0,4,1.8,4,4V413z",
              handleSize: "120%",
              handleStyle: {
                color: "#A9A9A9"
              },
              textStyle: {
                color: "#000"
              },
              borderColor: "#90979c"
            }
          ]
        });
      });
    }
  }
};
</script>