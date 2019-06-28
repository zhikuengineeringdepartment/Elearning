<template>
  <div>
    <div v-for="(tag,name,index) in tags">
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
    props: ["tags"]
  }
</script>

<style scoped>
  .file-tag {
    margin: 0 2vmin
  }
</style>
