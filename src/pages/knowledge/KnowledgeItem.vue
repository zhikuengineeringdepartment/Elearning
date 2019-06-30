<template>
  <el-card class="knowledge-item">
    <img :src="course.courseIcon">
    <div>
      <span>{{course.courseName}} - {{course.courseDesc}}</span>
    </div>
    <el-button @click="goKnowledgeDetail" type="text">learn</el-button>
  </el-card>
</template>

<script>
  import {routerChange} from "../../tools";
  
  export default {
    name: "KnowledgeItem",
    props: ['course'],
    methods: {
      goKnowledgeDetail: function () {
        routerChange("/knowledge/test", this);
      }
    },
  }
</script>

<style scoped>
  .knowledge-item {
    float: left;
    margin: 5vmin;
  }
</style>
