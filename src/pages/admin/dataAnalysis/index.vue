<template>
  <div>
    <el-collapse>
      <el-collapse-item title="每日注册用户量" name="1">
        <RegistDataCharts></RegistDataCharts>
      </el-collapse-item>
      <el-collapse-item title="每日访问量" name="2">
        <DataCharts chart-content="accessData"></DataCharts>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>
import RegistDataCharts from './RegistDataCharts';
import DataCharts from './DataCharts';
// import echarts from "echarts";
export default {
  components: {RegistDataCharts,DataCharts},
  data() {
    return {
    };
  },
};
</script>

<style></style>
