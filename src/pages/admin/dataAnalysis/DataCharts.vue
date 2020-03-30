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
          @click="getOurData"
          >确定
        </el-button>
        <div id="chart_data" style="width:800px;height:500px"></div>
      </div>
</template>
<style scoped>

</style>
<script>
import echarts from "echarts";
import { getData } from "../../../app/apis/dataAnalysisApi";
export default {
    props:{
        chartContent:String,
    },
    data() {
        return {
        DateValues: "",
        ourData: [],
        myChart: null,
        // goodItems: ["衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子"],
        // goodItems2: ["1", "2", "3", "5", "4"]
        };
    },
     methods: {
        getOurData: function() {
            console.log(this.chartContent)
            let startDate = this.DateValues[0];
            let endDate = this.DateValues[1];
            let DateRange = {
              beginDay: startDate,
              endDay: endDate
            };
              getData(
                  DateRange,
                  response => {
                  this.ourData = response.data.accessData;
                  console.log(response);
                  }
              );
        },
    }
}
</script>