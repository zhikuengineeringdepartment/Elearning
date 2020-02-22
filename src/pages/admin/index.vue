<template>
  <el-container>
    <el-header>
      <h1>
        sharing-ideas管理页面
        <sub>（仅供山软智库内部人员使用）</sub>
      </h1>
    </el-header>
    <el-container>
      <el-aside width="200px">
        <admin-menu></admin-menu>
      </el-aside>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import adminMenu from "./menu";

export default {
  name: "Admin",
  components: { adminMenu }
};
</script>

<style scoped>
.el-header {
  font-size: 1.5rem;
  padding: 20px 0;
  height: auto !important;
}

.el-container {
  min-width: 1000px;
}

.el-main {
  padding: 40px 80px;
  min-width: 1200px;
}

.el-aside {
  padding: 20px 0;
}
</style>