<!--页面未找到组件-->
<template>
  <center>
    <h1 class="animated bounceInDown pulse slower" style="font-size: 9rem;margin:50px 0;">404</h1>
    <h3 class="animated bounceIn pulse slower">您访问的页面不存在,请检查或联系管理员!</h3>
    <hr class="animated bounceIn pulse slower" />
    <div>
      <el-button type="primary" class="animated bounceInLeft delay-1s" @click="$router.push('/')">首页</el-button>
      <el-button
        type="primary"
        plain
        class="animated bounceInRight delay-1s"
        @click="$router.back(-1)"
      >返回</el-button>
    </div>
  </center>
</template>

<script>
export default {
  name: "PageNotFound"
};
</script>

<style scoped>
center{
  padding: 10vh 0;
  color: rgb(16, 54, 110);
}

div {
  height: 200px;
  line-height: 200px;
}

.el-button{
  margin: 0 10px;
}
</style>
