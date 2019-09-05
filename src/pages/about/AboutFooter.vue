<!--关于智库界面的页脚-->
<template>
  <div class="about-footer">
    <div class="about-footer-title">关注我们</div>
    <img class="about-footer-image" :src="QRCode"/>
    <p class="about-footer-text">{{content}}</p>
  </div>
</template>

<script>
  export default {
    name: "AboutFooter",
    data() {
      return {
        // 这里把二维码和介绍都抽象成data，以备日后能够直接从后台拉取这两个东西。
        QRCode: require('../../assets/about-pictures/QRCode.jpg'),
        content: '关注公众号即可随时追踪我们的最新动态 你也可以在后台留言，来说一说你的意见和遇到的问题。你的意见和建议会帮助我们更好的成长！'
      }
    }
  }
</script>

<style lang="less" scoped>
  .about-footer {
    width: 100vw;
    height: 50vmin;
    background-color: #000000;
    
    .about-footer-title {
      width: 100vw;
      height: 13vmin;
      line-height: 13vmin;
      text-align: center;
      font-size: 5vmin;
      color: #FFFFFF;
    }
    
    .about-footer-image {
      width: 20vmin;
      height: 20vmin;
      margin: 0 auto;
    }
    
    .about-footer-text {
      width: 80vw;
      margin: 4vmin auto;
      font-size: 2vmin;
      overflow: hidden;
      color: #FFFFFF;
    }
  }
</style>
