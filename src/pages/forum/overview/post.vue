<!--帖子组件-->
<template>
  <el-col :xs="24" :sm="20" :md="16" :lg="14" :xl="12">
    <div class="post">
      <div class="title">{{ title }}</div>
      <el-collapse>
        <el-collapse-item :title="content">
          <div>
            与现实生活一致：与现实生活的流程、逻辑保持一致，遵循用户习惯的语言和概念；
          </div>
        </el-collapse-item>
      </el-collapse>
      <div class="user-info">
        <el-avatar size="small" :src="circleUrl"></el-avatar>
        <span>bobking</span>
      </div>
    </div>
  </el-col>
</template>

<script>
export default {
  name: "Post",
  data() {
    return {
      title: "100 美元一行代码，开源软件到底咋赚钱",
      content: "  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)...",
      circleUrl:
        "https://imgsa.baidu.com/forum/w%3D580%3B/sign=44a183bdbdfb43161a1f7a72109f4723/b7003af33a87e9504ce6101a1f385343faf2b4d7.jpg"
    };
  }
};
</script>

<style scoped lang="less">
.post {
  padding: 20px;
  text-align: left;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  cursor: pointer;
}

.post:hover {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.title {
  font-size: 1.8vmin;
  font-weight: 700;
}

.user-info {
  width: 100%;
  display: flex;
  align-items: center;
}
</style>

<style>
.el-collapse,
.el-collapse-item__wrap,
.el-collapse .el-collapse-item__header {
  border: none !important;
}

.el-collapse-item__content {
  padding: 0 !important;
}
</style>
