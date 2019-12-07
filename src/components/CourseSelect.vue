<!--课程选择组件-->
<template>
  <el-select v-model="courseValue" @change="getValue" filterable placeholder="请选择课程">
    <el-option v-for="item in courses" :key="item.cid" :label="item.courseName" :value="item.cid"></el-option>
  </el-select>
</template>

<script>
export default {
  name: "CourseSelect",
  data() {
    return {
      courseValue: -1,
      courses: [{ cid: -1, courseName: "全部" }]
    };
  },
  mounted() {
    this.getCourses();
  },
  methods: {
    getValue: function() {
      this.$emit("get-course-value", this.courseValue);
    },
    // 获取所有课程
    getCourses: function() {
      const _this = this;

      // 在这里发起请求
      this.$http
        .get("/course/getAllCourse")
        .then(function(response) {
          if (response.data.code === 200) {
            _this.courses = response.data.data.courses;
            _this.courses.unshift({ cid: -1, courseName: "全部" });
          } else {
            _this.$message({
              showClose: true,
              message: response.data.message,
              type: "error"
            });
          }
        })
        .catch(function(err) {
          console.log(err);
        });
    }
  }
};
</script>

<style scoped>
</style>
