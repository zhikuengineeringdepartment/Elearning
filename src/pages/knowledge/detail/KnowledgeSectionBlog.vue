<template>
  <div class="blog">
    <h3>相关csdn推荐</h3>
    <div v-for="(item, index) in blog" :key="index">
      <a :href="item.url" class="blog-url">{{item.title}}</a><br>
    </div>
  </div>
</template>

<script>
  export default {
    name: "KnowledgeSectionBlog",
    props: ["blog"]
  }
</script>

<style scoped>
  .blog {
    text-align: left;
    margin: 7vmin 5vmin;
  }
  
  h3 {
    font-size: 3vmin;
    margin: 1vmin 0;
  }
  
  .blog-url {
    font-size: 2vmin;
    margin: 1vmin 0;
    color: #409EFF;
    text-decoration: underline;
  }

  .blog-url:hover {
    font-size: 2vmin;
    margin: 1vmin 0;
    color: #CC0000;
    text-decoration: underline;
  }
</style>
