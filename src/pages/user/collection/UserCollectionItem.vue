<!--用户-收藏项组件-->
<template>
  <el-card shadow="hover" class="user-collection-item">
    <h3>{{col_paragraph.courseName}} - {{col_paragraph.courseDesc}}</h3>
    <p>{{col_paragraph.paragraphContent}}</p>
  </el-card>
</template>

<script>
  export default {
    name: "UserCollectionItem",
    data() {
      return {
        toAddress: "/course/details/" + this.col_paragraph.cid + "/" + Math.floor(this.col_paragraph.paragraphSeq / 100000),
        col_paragraph_content: 'col_paragraph_content' + this.index
      }
    },
    props: ["col_paragraph", "index"]
  }
</script>

<style lang="less" scoped>
  .user-collection-item {
    margin: 3vmin 2vmin;
  
    p {
      text-align: left;
      margin: 2vmin;
    }
  }
</style>
