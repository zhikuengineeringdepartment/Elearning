<!--预览知识见解-->
<template>
  <div class="section">
    <div class="section-name" v-html="sectionView.sectionNameHtml"></div>
    <div v-for="knowledge in sectionView.knowledgeViews" :key="knowledge.kid">
      <div class="section-knowledge-name" v-html="knowledge.knowledgeNameHtml"></div>
      <div v-for="paragraph in knowledge.paragraphs" :key="paragraph.pid">
        <div class="section-knowledge-paragraph">
          <div class="section-knowledge-paragraph-content" v-html="paragraph.paragraphContentHtml"></div>
          <div class="button-group" v-if="currentPid === paragraph.pid"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "KnowledgeSection",
  props: ["sectionView"],
  data: function() {
    return {};
  },
  methods: {}
};
</script>

<style lang="less">
.section {
  text-align: left;
  margin: 1vmin 5vmin 0 5vmin;
  padding: 3vmin;
  border: 1px solid #a7d7f9;
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
  background-color: #e3f3f7;
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
</style>>
