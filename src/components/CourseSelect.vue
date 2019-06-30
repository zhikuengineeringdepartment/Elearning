<!--课程选择组件-->
<template>
  <el-select v-model="course_value" @change="get_value" filterable placeholder="请选择课程">
    <el-option
      v-for="item in courses"
      :key="item.cid"
      :label="item.courseName"
      :value="item.cid">
    </el-option>
  </el-select>
</template>

<script>
  export default {
    name: "CourseSelect",
    data() {
      return {
        course_value: '',
        courses: []
      }
    },
    mounted () {
      this.getCourses();
    },
    methods: {
      get_value: function () {
        this.$emit('get-course-value', this.course_value)
      },
      // 获取所有课程
      getCourses: function () {
        var _this = this;
        
        // 写个假数据
        _this.courses = [
          {
            cid: 100,
            courseName: 'course1'
          },
          {
            cid: 101,
            courseName: 'course2'
          }
        ];
        
        // 在这里发起请求
        // this.$http.get('/course/getAllCourse').then(
        //   function (response) {
        //     if (response.data.code === 200) {
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
  }
</script>

<style scoped>

</style>
