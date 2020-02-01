<!--知识见解页面详情组件-->
<template>
  <div class="section-navigator">
    <!--手机端和电脑端显示的文字不同-->
    <div v-if="side.preSection !== ''">
      <el-button type="text" @click="handleMenu(side.preSection.sid)">
        <i class="el-icon-arrow-left"></i>
        {{$store.state.isMobile?'上一章':side.preSection.sectionName.substring(side.preSection.sectionName.indexOf(' '))}}
      </el-button>
    </div>
    <div v-if="side.preSection === ''"></div>
    <div v-if="side.nextSection !== ''">
      <el-button type="text" @click="handleMenu(side.nextSection.sid)">
        {{$store.state.isMobile?'下一章':side.nextSection.sectionName.substring(side.nextSection.sectionName.indexOf(' '))}}
        <i class="el-icon-arrow-right"></i>
      </el-button>
    </div>
    <div v-if="side.nextSection === ''"></div>
  </div>
</template>

<script>
  export default {
    name: "KnowledgeSectionNavigator",
    props: ["side"],
    methods: {
      //处理目录栏的点击事件
      handleMenu(sid) {
        this.$store.commit('setSectionId', sid);
        window.scrollTo(0, 0);
        this.$emit('setSectionView', sid);
        this.$emit('setCsdn', sid);
      }
    }
  }
</script>

<style scoped>
  .section-navigator {
    margin: 2vmin 5vmin;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
  }
</style>
