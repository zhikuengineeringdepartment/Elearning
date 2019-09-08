<!--知识见解页面组件-->
<template>
  <el-main>
    <el-row type="flex" justify="center">
      <el-col :span="24">
        <div v-for="(course, index) in courses" :key="index">
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
    components: {KnowledgeItem},
    data() {
      return {
        courses: []
      }
    },
    created() {
      this.getCourses();
    },
    methods: {
      getCourses: function () {
        const _this = this;
        
        // 请求所有的知识见解
        _this.$http.get('course/getAllCourse', {
          params: {
            uid: 0
          }
        }).then(
          function (response) {
            console.log('get course', response.data);
            if (response.data.code === 200) {
              _this.courses = response.data.data.courses;
            } else {
              this.$message({showClose: true, message: response.data.message, type: 'error'});
            }
          }
        ).catch(function (err) {
          console.log(err);
        });
      }
    }
  }
</script>

<style scoped>

</style>
