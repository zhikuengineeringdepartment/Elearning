<!--用户-收藏界面组件-->
<template>
  <div>
    <course-select v-on:get-course-value="set_course_value"></course-select>
    <ul>
      <li v-for="(col_paragraph,index) in col_paragraph_views">
        <user-collection-item :col_paragraph="col_paragraph" :index="index"></user-collection-item>
      </li>
    </ul>
  </div>
</template>

<script>
  import UserCollectionItem from "./UserCollectionItem";
  import CourseSelect from "../../../components/CourseSelect";
  
  export default {
    name: "UserCollection",
    components: {UserCollectionItem, CourseSelect},
    data() {
      return {
        Course: null,
        col_paragraph_views: [
          {
            sectionName: "第一节",
            courseName: "测试md",
            paragraphContent: "图片的描述”功能暂未计划开发，请用“#”代替，如1.1.2.结构框图中图片。图片要**单独**一段。",
            colCount: 100
          },
          {
            sectionName: "第二节",
            courseName: "测试md",
            paragraphContent: "图片的描述”功能暂未计划开发，请用“#”代替，如1.1.2.结构框图中图片。图片要**单独**一段。",
            colCount: 100
          },
          {
            sectionName: "第三节",
            courseName: "测试md",
            paragraphContent: "图片的描述”功能暂未计划开发，请用“#”代替，如1.1.2.结构框图中图片。图片要**单独**一段。",
            colCount: 100
          }
        ]
      }
    },
    created: function () {
      this.getColParagraphViews();
    },
    methods: {
      set_course_value: function (cid) {
        this.Course = cid;
        this.getColParagraphViews();
      },
      getColParagraphViews: function () {
        // 获取用户的收藏信息
        const _this = this;
        this.$http.get(this.$store.state.backEndIp + '/paragraph/getColParagraphViews', {
          params: {
            uid: 0,
            cid: _this.Course,
            page: 1
          }
        }).then(
          function (response) {
            if (response.data.code === 200) {
              console.log(response.data);
              _this.col_paragraph_views = response.data.data.colParagraphViews;
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
li {
  list-style: none;
}
</style>
