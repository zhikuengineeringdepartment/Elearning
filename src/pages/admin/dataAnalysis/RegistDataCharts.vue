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
          >确定</el-button
        >
        <div id="chart_registration_data" style="width:1000px;height:500px"></div>
      </div>
</template>
<style scoped>

</style>
<script>
import echarts from "echarts";
import {getRegistration } from "../../../app/apis/dataAnalysisApi";
export default {
    data() {
        return {
        DateValues: "",
        ourData: [],
        };
    },
    methods: {
        getRegistrationData: function() {
            let startDate = this.DateValues[0];
            let endDate = this.DateValues[1];
            let DateRange = {
              beginDay: startDate,
              endDay: endDate
            };
            let dateArr=new Array();
            let numArr = new Array();
            getRegistration(
                DateRange,
                response => {
                this.ourData = response.data;
                }
            );
            for(let i=0;i<this.ourData.registers.length();i++){
              dateArr.push(this.ourData.registers[i].date);
              numArr.push(this.ourData.registers[i].number);
            }
            // 基于准备好的dom，初始化echarts实例
            let myChart = echarts.init(document.getElementById("chart_registration_data"));
            // 绘制图表
            myChart.setOption({
              title: {},
              tooltip: {},
              xAxis: {
                data: dateArr
              },
              yAxis: {},
              series: [
                {
                  name: "注册数（已激活）",
                  type: 'bar',
                  data: numArr
                }
              ]
            });

        },
    }
}
</script>