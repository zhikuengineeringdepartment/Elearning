<template>
  <!-- 暂时每日每页面的数据图未进行页面的分类处理 -->
  <div>
    <el-collapse>
      <el-collapse-item title="每日网站总访问量与总访问时间">
        <DataChartADTotal></DataChartADTotal>
      </el-collapse-item>
      <el-collapse-item title="每日注册用户量">
        <RegistDataCharts></RegistDataCharts>
      </el-collapse-item>
      <el-collapse-item title="每日各页面访问相关数据">
        <!-- 通过props传递参数选择绘制的类型 -->
        <!-- <DataCharts chart-content="accessIpNumber"></DataCharts> -->
        <DataCharts></DataCharts>
      </el-collapse-item>
      <!-- <el-collapse-item title="每日各页面总停留时间">
        <DataCharts chart-content="stayTime"></DataCharts>
      </el-collapse-item>
      <el-collapse-item title="每日各页面平均停留时间">
        <DataCharts chart-content="stayTimeAvg"></DataCharts>
      </el-collapse-item>-->
    </el-collapse>
  </div>
</template>

<script>
import RegistDataCharts from "./RegistDataCharts";
import DataCharts from "./DataCharts";
import DataChartADTotal from "./DataChartADTotal";
// import echarts from "echarts";
export default {
  components: { RegistDataCharts, DataCharts, DataChartADTotal },
  data() {
    return {};
  },
  created() {
    this.$notify({
      title: "提示(该提示需手动关闭)",
      dangerouslyUseHTMLString: true,
      message:
        "<p>选取日期范围以显示可视化数据</p>" +
        "<p>生成的数据图下方的操作轴可伸缩以修改图片显示的时间范围</p>",
      duration: 0
    });
  }
};
</script>

<style></style>
