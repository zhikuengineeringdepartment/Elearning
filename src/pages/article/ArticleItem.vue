<!--智库专栏中每一个详细的专栏-->
<!--这个组件的难点在于长和宽的计算-->
<template>
  <a :href="article.link" target="_blank">
    <div class="article-item">
      <div>{{article.title}}</div>
      <img :src="article.img" :alt="article.title" />
    </div>
  </a>
</template>

<script>
export default {
  name: "ArticleItem",
  props: ["article"]
};
</script>

<style lang="less" scoped>
a {
  text-decoration: none;

  .article-item {
    width: 80vw;
    height: 15vmin;
    margin: 0 10vw;
    display: flex;
    flex-direction: row;
    border-bottom: 1px solid #dcdfe6;

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

.article-item:hover {
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}
</style>
