<!--知识见解详情-段落组件-->
<!--TODO 收藏和笔记功能暂时注释掉了，因为没有想好如何做移动端适配-->
<template>
  <div class="section">
    <div class="section-name" v-html="sectionView.sectionNameHtml"></div>
    <div v-for="knowledge in sectionView.knowledgeViews" :key="knowledge.kid">
      <div class="section-knowledge-name" v-html="knowledge.knowledgeNameHtml"></div>
      <div v-for="paragraph in knowledge.paragraphs" :key="paragraph.pid">
        <div class="section-knowledge-paragraph" @mouseover="currentPid = paragraph.pid" @mouseout="currentPid = -1">
          <div class="section-knowledge-paragraph-content" v-html="paragraph.paragraphContentHtml"></div>
          <!--<div v-if="currentPid === paragraph.pid">-->
          <!--<div class="button-group">-->
          <!--<div class="button">收藏</div>-->
          <!--<div class="button">笔记</div>-->
          <!--</div>-->
          <!--</div>-->
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "KnowledgeSection",
    props: ["sectionView", "noteViews", "colParas"],
    data: function () {
      return {
        currentPid: -1
      }
    }
  }
</script>

<style lang="less">
  .section {
    text-align: left;
    margin: 1vmin 5vmin 0 5vmin;
    padding: 3vmin;
    border: 1px solid #A7D7F9;
  }
  
  .section-name {
    padding: 2vmin 0;
    font-size: 3.5vmin;
  }
  
  .section-knowledge-name {
    padding: 2vmin 0;
    font-size: 3vmin;
  }
  
  .section-knowledge-paragraph {
    display: flex;
    flex-direction: row;
  }
  
  .section-knowledge-paragraph:hover {
    background-color: #E3F3F7;
  }
  
  .section-knowledge-paragraph-content {
    padding: 3vmin 0;
    font-size: 2vmin;
    
    pre {
      overflow: scroll;
      width: 50vw;
    }
    
    img {
      width: 50vw;
      margin: 1vw;
    }
  }
  
  .button-group {
    float: right;
    width: 15vmin;
    position: absolute;
    left: 90vmin;
    display: flex;
    flex-direction: row;
  }
  
  .button {
    background-color: #A3F3F7;
    width: 5vmin;
    margin: 0 1vmin;
    text-align: center;
    height: 2.5vmin;
  }
</style>
