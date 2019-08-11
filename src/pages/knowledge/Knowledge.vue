<!--知识见解页面组件-->
<template>
  <el-main>
    <el-row type="flex" justify="center">
      <el-col :span="20">
        <div v-for="(course,index) in courses" :key="index">
          <knowledge-item :course="course"></knowledge-item>
        </div>
      </el-col>
    </el-row>
  </el-main>
</template>

<script>
import KnowledgeItem from "./KnowledgeItem";

export default {
  name: "Knowledge",
  components: { KnowledgeItem },
  data() {
    return {
      courses: []
    };
  },
  created() {
    this.getCourses();
  },
  methods: {
    getCourses: function() {
      const _this = this;

      // 写个假数据
      _this.courses = [
        {
          cid: 112,
          courseName: "面向对象",
          courseDesc: "V1.0",
          courseIcon: "img/logo.jpg"
        },
        {
          cid: 124,
          courseName: "数据库",
          courseDesc: "V2.0",
          courseIcon: "img/logo.jpg"
        },
        {
          cid: 125,
          courseName: "操作系统",
          courseDesc: "V2.0",
          courseIcon: "img/logo.jpg"
        }
      ];

      // 在这里发起请求
      // _this.$http.get('course/getAllCourse', {
      //   params: {
      //     uid: 0
      //   }
      // }).then(
      //   function (response) {
      //     if (response.data.code === 200) {
      //       console.log(response.data);
      //       _this.courses = response.data.data.courses;
      //     } else {
      //       this.$message({showClose: true, message: response.data.message, type: 'error'});
      //     }
      //   }
      // ).catch(function (err) {
      //   console.log(err);
      // });
    }
  }
};
</script>

<style scoped>
</style>
