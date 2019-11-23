<!--帖子组件-->
<template>
  <el-col :xs="24" :sm="20" :md="16" :lg="14" :xl="12">
    <el-select
      v-model="course"
      clearable
      placeholder="请选择课程"
      class="input"
    >
      <el-option
        v-for="item in courselist"
        :key="item"
        :label="item"
        :value="item"
      >
      </el-option>
    </el-select>

    <el-input
      placeholder="请输入搜索内容"
      v-model="keyword"
      class="input-with-select input"
    >
      <el-button slot="append" icon="el-icon-search"></el-button>
    </el-input>
  </el-col>
</template>

<script>
export default {
  name: "SearchBar",
  data() {
    return {
      courselist: ["选项1", "黄金糕", "选项2", "双皮奶"],
      course: "",
      keyword: ""
    };
  }
};
</script>

<style scoped>
.input {
  width: 40%;
  min-width: 200px;
  margin: 0 5% 3vmin 5%;
}
</style>
