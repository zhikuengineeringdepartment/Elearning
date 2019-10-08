<!--页面页脚组件-->
<template>
  <div class="footer" @click="colorEgg">&copy;山软智库&nbsp;让知识回归平凡&nbsp;&nbsp;since&nbsp;2017</div>
</template>

<script>
  export default {
    name: "MainFooter",
    methods: {
      colorEgg: function () {
        alert('呀！恭喜你发现了彩蛋！我是智库网站前端的作者，智库最咸鱼的人，尽情享受这个有趣的网站吧！——by shadowingszy');
      }
    }
  }
</script>

<style scoped>
  .footer {
    width: 100%;
    height: 8vh;
    line-height: 8vh;
    background-color: #000000;
    font-size: 2vh;
    color: #FFFFFF;
  }
</style>
