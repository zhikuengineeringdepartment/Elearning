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
    <!-- <div :id="'chart_data_'+chartContent" style="width:800px;height:400px"></div> -->
    <div id="chart_data_accessData" style="width:800px;height:400px"></div>
  </div>
</template>
<style scoped>
</style>
<script>
import echarts from "echarts";
import { getData } from "../../../app/apis/dataAnalysisApi";
export default {
  // props: {
  //   chartContent: String
  // },
  data() {
    return {
      DateValues: "",
      accessData: [],
      chart: null,
      chartName: [
        "访问量",
        "访问人数(按IP计算)",
        "页面停留时间",
        "页面平均停留时间"
      ]
    };
  },
  // mounted: function() {
  //   let chartContent = this.chartContent;
  //   switch (chartContent) {
  //     case "accessIpNumber":
  //       this.chartName = "访问量(按IP计算)";
  //       break;
  //     case "stayTime":
  //       this.chartName = "页面停留时间";
  //       break;
  //     case "stayTimeAvg":
  //       this.chartName = "页面平均停留时间";
  //       break;
  //     default:
  //       this.chartName = "数据";
  //       break;
  //   }
  // },
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
        const _this = this;
        console.log(response);
        this.accessData = response.data.accessData;

        let XArr = new Array();
        let numArr = new Array();
        let accessArr = new Array();
        let stayTimeArr = new Array();
        let stayTimeAvgArr = new Array();

        for (let i = 0; i < this.accessData.length; i++) {
          let date = this.accessData[i].date;
          let date_day = date.split(" ")[0];
          // let page = this.accessData[i].pageName;
          let page = this.accessData[i].pageName
            ? this.accessData[i].pageName
            : this.accessData[i].uri;
          let Xdata = date_day + page;
          // console.log(date_day)
          XArr.push(Xdata);
          // numArr.push(this.accessData[i][this.chartContent]);
          numArr.push(this.accessData[i].number);
          accessArr.push(this.accessData[i].accessIpNumber);
          stayTimeArr.push(this.accessData[i].stayTime);
          stayTimeAvgArr.push(this.accessData[i].stayTimeAvg);
        }
        // 基于准备好的dom，初始化echarts实例
        this.chart = echarts.init(
          document.getElementById("chart_data_accessData")
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
            data: XArr,
            axisLabel: {
              rotate: 0,
              formatter: function(params) {
                var newParamsName = ""; // 最终拼接成的字符串
                var paramsNameNumber = params.length; // 实际标签的个数
                var provideNumber = 10; // 每行能显示的字的个数
                var rowNumber = Math.ceil(paramsNameNumber / provideNumber); // 换行的话，需要显示几行，向上取整
                /**
                 * 判断标签的个数是否大于规定的个数， 如果大于，则进行换行处理 如果不大于，即等于或小于，就返回原标签
                 */
                // 条件等同于rowNumber>1
                if (paramsNameNumber > provideNumber) {
                  /** 循环每一行,p表示行 */
                  for (var p = 0; p < rowNumber; p++) {
                    var tempStr = ""; // 表示每一次截取的字符串
                    var start = p * provideNumber; // 开始截取的位置
                    var end = start + provideNumber; // 结束截取的位置
                    // 此处特殊处理最后一行的索引值
                    if (p == rowNumber - 1) {
                      // 最后一次不换行
                      tempStr = params.substring(start, paramsNameNumber);
                    } else {
                      // 每一次拼接字符串并换行
                      tempStr = params.substring(start, end) + "\n";
                    }
                    newParamsName += tempStr; // 最终拼成的字符串
                  }
                } else {
                  // 将旧标签的值赋给新标签
                  newParamsName = params;
                }
                //将最终的字符串返回
                return newParamsName;
              }
            },
            grid: {
              buttom: "0%"
            }
          },
          yAxis: {},
          series: [
            {
              name: _this.chartName[0],
              type: "bar",
              data: numArr,
              barWidth: "20%",
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
              }
            },
            {
              name: _this.chartName[1],
              type: "bar",
              data: accessArr,
              barWidth: "20%",
              label: {
                show: true
              },
              itemStyle: {
                normal: {
                  color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                    {
                      offset: 0,
                      color: "#ED4C67"
                    },
                    {
                      offset: 1,
                      color: "#833471"
                    }
                  ]),
                  barBorderRadius: 11
                }
              }
            },
            {
              name: _this.chartName[2],
              type: "bar",
              data: stayTimeArr,
              barWidth: "20%",
              label: {
                show: true
              },
              itemStyle: {
                normal: {
                  color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                    {
                      offset: 0,
                      color: "#8bd46e"
                    },
                    {
                      offset: 1,
                      color: "#09bcb7"
                    }
                  ]),
                  barBorderRadius: 11
                }
              }
            },
            {
              name: _this.chartName[3],
              type: "bar",
              data: stayTimeAvgArr,
              barWidth: "20%",
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
              }
            }
          ],
          dataZoom: [
            {
              show: true,
              height: 20,
              xAxisIndex: [0],
              bottom: "0%",
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