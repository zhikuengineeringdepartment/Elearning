<!--文件标签组件-->
<template>
  <div>
    <el-tag v-if="status" class="file-tag">
      {{status}}
    </el-tag>
    <div v-for="(tag,name,index) in tags" :key="index">
      <div v-if="name !== 'fid' && tag !== null">
        <el-tag type="danger" class="file-tag">
          {{tag}}
        </el-tag>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "FileTag",
    props: ["tags", "status"]
  }
</script>

<style lang="less" scoped>
  div {
    display: flex;
    flex-direction: row;
    align-items: center;
    overflow: hidden;
    
    .file-tag {
      margin: 1vmin
    }
    
  }
</style>
