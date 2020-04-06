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
    <el-button type="primary" round size="small" style="margin-left:10px" @click="getOurData">确定</el-button>
    <!-- 网站所有页面总点击量、停留时间 -->
    <div id="chart_data_aDTotal" style="width:800px;height:400px"></div>
  </div>
</template>
<style scoped>
</style>
<script>
import echarts from "echarts";
import { getData } from "../../../app/apis/dataAnalysisApi";
export default {
  data() {
    return {
      DateValues: "", //传向服务端的时间
      aDTotal: [], //存储返回的数据中的aDTotal部分
      chart: null,
      chartName: ["总点击量", "总停留时间"]
    };
  },
  methods: {
    getOurData: function() {
      const _this = this;
      let startDate = this.DateValues[0];
      let endDate = this.DateValues[1];
      let DateRange = {
        beginDay: startDate,
        endDay: endDate
      };

      getData(DateRange, response => {
        console.log(response);
        this.aDTotal = response.data.aDTotal;

        //数据转为数组存储用于绘制
        let dateArr = new Array(); //日期，横坐标
        let numArr = new Array();
        let stayTimeArr = new Array();
        for (let i = 0; i < this.aDTotal.length; i++) {
          let date = this.aDTotal[i].date;
          let date_day = date.split(" ")[0];
          // console.log(date_day)
          dateArr.push(date_day);
          numArr.push(this.aDTotal[i]["number"]);
          stayTimeArr.push(this.aDTotal[i]["stayTime"]);
        }
        // 基于准备好的dom，初始化echarts实例
        this.chart = echarts.init(
          document.getElementById("chart_data_aDTotal")
        );
        // 绘制图表
        this.chart.setOption({
          title: {},
          tooltip: {
            trigger: "axis",
            axisPointer: {
              // 坐标轴指示器，坐标轴触发有效
              type: "shadow" // 默认为直线，可选为：'line' | 'shadow'
            }
          },
          legend: {
            data: _this.chartName,
            left: 20,
            // top: 12,
            textStyle: {
              color: "#000"
            },
            itemWidth: 12,
            itemHeight: 10
            // itemGap: 35
          },
          xAxis: {
            data: dateArr,
            grid: {
              buttom: "0%"
            }
          },
          yAxis: {},
          series: [
            {
              name: _this.chartName[0],
              type: "bar",
              barWidth: "30%",
              label: {
                show: true
              },
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
              },
              data: numArr //点击量
            },
            {
              name: _this.chartName[1],
              type: "bar",
              barWidth: "30%",
              label: {
                show: true
              },
              itemStyle: {
                normal: {
                  color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                    {
                      offset: 0,
                      color: "#248ff7"
                    },
                    {
                      offset: 1,
                      color: "#6851f1"
                    }
                  ]),
                  barBorderRadius: 11
                }
              },
              data: stayTimeArr //停留时间
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