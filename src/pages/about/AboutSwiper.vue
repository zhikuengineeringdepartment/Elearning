<!--关于智库界面的轮播图-->
<template>
  <div class="about-swiper">
    <el-carousel :interval="4000" height="50vmin">
      <el-carousel-item v-for="(item,index) in departments" :key="index">
        <img :src="item.imgPath" class="about-swiper-image">
        <div class="about-swiper-show-text">
          <h3>{{item.content1}}</h3>
          <h4>{{item.content2}}</h4>
          <span>{{item.content3}}</span>
        </div>
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script>
  export default {
    name: "AboutSwiper",
    data() {
      return {
        departments: [{
          imgPath: require("../../assets/about-pictures/内容部.jpg"),
          content1: "内容部",
          content2: "设计与创作学习内容",
          content3: "不只是知识见解，线上线下全方位走入你的学习生活"
        }, {
          imgPath: require("../../assets/about-pictures/工程部.jpg"),
          content1: "工程部",
          content2: "产品调研与开发",
          content3: "有什么比新的世界在手中构建更让人愉悦的事情呢？"
        }, {
          imgPath: require("../../assets/about-pictures/科研部.jpg"),
          content1: "科研部",
          content2: "本科科研探索",
          content3: "专业知识图谱建模，学习行为数据挖掘，为智库的未来赋能。"
        }, {
          imgPath: require("../../assets/about-pictures/运营部.jpg"),
          content1: "运营部",
          content2: "让价值传播",
          content3: "把我们的产品轻轻放进你的怀里，引导和记录你与智库的今世今生。"
        }]
      }
    }
  }
</script>

<style lang="less" scoped>
  @keyframes fadeIn {
    0% {
      opacity: 0;
    }
    
    25% {
      opacity: 20%;
    }
    
    50% {
      opacity: 40%;
    }
    
    75% {
      opacity: 60%;
    }
    
    100% {
      opacity: 80%;
    }
  }
  
  .about-swiper {
    margin: 5vmin auto;
    width: 100vmin;
    height: 50vmin;
    overflow: hidden;
    
    .about-swiper-image {
      width: 100vmin;
      height: 50vmin;
    }
    
    .about-swiper-show-text {
      width: 100vmin;
      height: 50vmin;
      transform: translateY(-50vmin) scale(1);
      background-color: #000000;
      color: #FFFFFF;
      opacity: 0;
      display: flex;
      flex-direction: column;
      text-align: center;
      
      &:hover {
        animation: fadeIn 1s 1;
        -webkit-animation: fadeIn 1s 1;
        -moz-animation: fadeIn 1s 1;
        -o-animation: fadeIn 1s 1;
        opacity: 0.8;
      }
      
      h3 {
        font-weight: bold;
        margin-top: 15vmin;
        font-size: 4vmin;
      }
      
      h4 {
        margin-top: 5vmin;
        font-size: 3vmin;
      }
      
      span {
        margin-top: 5vmin;
        font-size: 2vmin;
      }
    }
  }
</style>
