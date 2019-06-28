<template>
  <el-card shadow="hover" class="user-collection-item">
    <h3>{{col_paragraph.sectionName}}</h3>
    <div :id="col_paragraph_content"></div>
    <div>
      <span style="margin-right: 10px">{{col_paragraph.courseName}}</span>
      <span style="margin-right: 10px">{{col_paragraph.colCount}}</span>
    </div>
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
  
    div {
      margin: 2vmin;
    }
  }
</style>
