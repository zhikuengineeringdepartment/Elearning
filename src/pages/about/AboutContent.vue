<!--关于智库-详情介绍-->
<template>
  <div class="about-content">
    <h3>改造线上线下学习模式的学生组织</h3>
    <div class="about-content-text">山软智库，创立于2017年1月1日</div>
    <div class="about-content-text">致力于围绕知识分享提供各种产品和服务</div>
    <div class="about-content-text">We take the best and spread it around so that</div>
    <div class="about-content-text">everybody in this world could grow up with better things</div>
  </div>
</template>

<script>
  export default {
    name: "AboutContent"
  }
</script>

<style lang="less" scoped>
  .about-content {
    width: 100vw;
    height: 50vmin;
    margin: 5vmin auto;
    
    h3 {
      width: 100vw;
      height: 15vmin;
      font-size: 6vmin;
    }
    
    .about-content-text {
      width: 100vw;
      height: 6vmin;
      line-height: 6vmin;
      font-size: 3vmin;
      color: #aa0000;
    }
  }
</style>
