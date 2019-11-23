<template>
  <div class="container">
    <el-row type="flex" justify="center">
      <search-bar></search-bar>
    </el-row>

    <el-row type="flex" justify="center">
      <post></post>
    </el-row>
  </div>
</template>

<script>
import post from "./Post";
import searchBar from "./SearchBar";

export default {
  name: "Forum",
  components: { post, searchBar },
  data() {
    return {};
  }
};
</script>

<style scoped>
.container {
  margin: 3vmin 0;
}
</style>
