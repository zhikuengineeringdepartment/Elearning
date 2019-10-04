<!--智库专栏页面-->
<template>
  <div class="article-body">
    <article-item v-for="(article, index) in articles" :key="index" :article="article"></article-item>
  </div>
</template>

<script>
import ArticleItem from "./ArticleItem";

export default {
  name: "Article",
  components: { ArticleItem },
  data() {
    return {
      articles: [
        {
          title: "六一就该聊点羞羞哒R18少儿不宜鸭 | 山软杂谈",
          img: require("../../assets/article-pictures/img_1.jpg"),
          link: "https://mp.weixin.qq.com/s/drfJGm0W043P6btPZjcLLg"
        },
        {
          title: "软件运行老不好，多半是装的...装的不好！| 山软小便签",
          img: require("../../assets/article-pictures/img_2.jpg"),
          link: "https://mp.weixin.qq.com/s/ch-mgSf6OvNJ2AkGQooldQ"
        },
        {
          title: "18级导员直击未来规划 | 山软方向谈",
          img: require("../../assets/article-pictures/img_3.jpg"),
          link: "https://mp.weixin.qq.com/s/I21kC3l9keNGP1E9TEs0xw"
        },
        {
          title: "今天我就要喷死软件园的Tony老师 | 山软杂谈",
          img: require("../../assets/article-pictures/img_4.jpg"),
          link: "https://mp.weixin.qq.com/s/Gd1qcB1yWgTyQ6GFDMpLpg"
        },
        {
          title: "你的聊天记录被泄露了吗？| 山软小便签",
          img: require("../../assets/article-pictures/img_5.jpg"),
          link: "https://mp.weixin.qq.com/s/UqMswAaJxZezv_3KLhAFOA"
        },
        {
          title: "神经网络是什么？你的喜欢从何而来？| 智库科研路",
          img: require("../../assets/article-pictures/img_6.jpg"),
          link: "https://mp.weixin.qq.com/s/YVJDVjvZZnG4Gt0w-Xf1hQ"
        },
        {
          title: "计算机如何看懂图片？卷积！| 智库科研路",
          img: require("../../assets/article-pictures/img_7.jpg"),
          link: "https://mp.weixin.qq.com/s/maPwe37wGd6aj_uMxRjE_g"
        },
        {
          title: "纸上得来终觉浅，我们一起写模型！| 智库科研路",
          img: require("../../assets/article-pictures/img_8.jpg"),
          link: "https://mp.weixin.qq.com/s/JW-MBAvhk1HNe8oX6UXqOQ"
        },
        {
          title: "躲得过对酒当歌的夜，躲不过四下无人的街 | 智库科研路",
          img: require("../../assets/article-pictures/img_9.jpg"),
          link: "https://mp.weixin.qq.com/s/NDNPZJelUGS1IylhJ3s7KQ"
        },
        {
          title: "卷积卷积卷积，卷积都被用在哪？ | 山软方向谈",
          img: require("../../assets/article-pictures/img_10.jpg"),
          link: "https://mp.weixin.qq.com/s/omF1qkfJas7_o21aciDTPQ"
        },
        {
          title: "镜子对面那位，你是谁？ | Sharing Secrets",
          img: require("../../assets/article-pictures/img_11.jpg"),
          link: "https://mp.weixin.qq.com/s/lktXllRr5F4_Ds2jhpUj2Q"
        }
      ]
    };
  }
};
</script>

<style scoped>
.article-body {
  min-height: calc(100vh - 69vmin);
  margin: 10vmin 0;
}
</style>
