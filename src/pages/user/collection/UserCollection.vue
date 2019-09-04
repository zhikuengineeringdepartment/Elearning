<!--用户-收藏界面组件-->
<template>
  <div>
    <course-select @get-course-value="setCourseValue"></course-select>
    <ul>
      <li
        v-for="(col_paragraph,index) in col_paragraph_views"
        :key="index"
      >
        <user-collection-item
          :col_paragraph="col_paragraph"
          :index="index"
        />
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
        course: -1,
        col_paragraph_views: []
      }
    },
    created: function () {
      this.getColParagraphViews();
    },
    methods: {
      setCourseValue: function (cid) {
        this.course = cid;
        this.getColParagraphViews();
      },
      getColParagraphViews: function () {
        // 获取用户的收藏信息
        const _this = this;
        
        // 在这里发起请求
        this.$http.get('/paragraph/getColParagraphViews', {
          params: {
            uid: 0,
            cid: _this.course === -1 ? null : _this.course,
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
