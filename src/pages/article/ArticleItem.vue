<!--智库专栏中每一个详细的专栏-->
<!--这个组件的难点在于长和宽的计算-->
<template>
  <a :href="article.link" target="_blank">
    <div class="article-item">
      <div>{{article.title}}</div>
      <img :src="article.img" :alt="article.title">
    </div>
  </a>
</template>

<script>
  export default {
    name: "ArticleItem",
    props: ["article"]
  }
</script>

<style lang="less" scoped>
  a {
    text-decoration: none;
    
    .article-item {
      width: 80vw;
      height: 15vmin;
      margin: 3vmin 10vw;
      display: flex;
      flex-direction: row;
      border-radius: 1vw;
      box-shadow: 1vmin 1vmin 2vmin #888888;
      
      div {
        margin: 2vmin 2vw;
        width: 56vw;
        height: 11vmin;
        font-size: 2vmin;
        text-align: left;
        font-weight: bold;
        overflow: hidden;
      }
      
      img {
        margin: 2vmin calc(10vw - 7vmin);
        width: 14vmin;
        height: 11vmin;
      }
    }
  }

</style>
