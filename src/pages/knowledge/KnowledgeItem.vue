<template>
  <el-card :body-style="{ padding: '0px' }" style="float: left;margin:10px 10px">
    <a @click="getCourseDetails(course.cid)" style="text-decoration: none;color: #409EFF;">
      <img :src="course.courseIcon" class="image">
    </a>
    <div style="padding: 14px;">
      <span style="color: #000;">{{course.courseName}}</span>
      <div v-if="schedule" class="bottom clearfix">
        <time class="time">{{course.courseDesc}}</time>
        <el-button type="text" class="button" @click="getCourseDetails(course.cid)">learn</el-button>
      </div>
      <div v-else style="margin-top: 10px;">
        <el-progress :stroke-width="12" :percentage="completed" :show-text="false"></el-progress>
        <span>已学习{{course.completedSection}}/{{course.totalSection}}小节</span>
      </div>
    </div>
  </el-card>
</template>

<script>
  import {routerChange} from "../../tools";

  export default {
    name: "KnowledgeItem",
    data() {
      return {
        completed: this.course.completedSection / this.course.totalSection * 100
      }
    },
    props: ['course', 'schedule'],
    methods: {
      getCourseDetails(cid) {
        routerChange("/knowledge/detail");
      }
    },
  }
</script>

<style scoped>

</style>
