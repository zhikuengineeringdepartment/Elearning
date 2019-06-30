<template>
  <el-main>
    <el-row type="flex" class="row-bg" justify="center">
      <el-col :span="15">
        <el-row :gutter="20">
          <div v-for="course in courses" class="course-row">
            <knowledge-item :course="course" :schedule="true"></knowledge-item>
          </div>
        </el-row>
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
        courses: [
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
        ]
      }
    },
    created() {
      this.getCourses();
    },
    methods: {
      getCourses: function () {
        var _this = this;
        axios.get('course/getAllCourse')
          .then(function (response) {
            _this.courses = response.data.data.courses;
          })
          .catch(function (err) {
            console.log(err);
          });
      }
    }
  }
</script>

<style scoped>

</style>
