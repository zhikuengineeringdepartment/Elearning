<!--知识见解详情-章节选择组件-->
<template>
  <!--<el-menu :default-active="that.$route.params.sid" class="el-menu-vertical-demo">-->
  <el-menu class="section-select-menu">
    <div v-for="(section, index) in courseView.sections" :key="index">
      <el-submenu :index="section.index.toString()">
        <template slot="title">
          <el-tooltip effect="dark" :content="section.title" placement="top-start">
            <span>{{section.title}}</span>
          </el-tooltip>
        </template>
        <div v-for="(sub, index) in section.sub" :key="index">
          <el-tooltip
            effect="dark"
            :content="sub.sectionName.substring(sub.sectionName.indexOf(' '))"
            placement="top-start"
          >
            <el-menu-item :index="sub.sid.toString()" @click="handleMenu(sub.sid)">
              <span slot="title">{{sub.sectionName.substring(sub.sectionName.indexOf(' '))}}</span>
            </el-menu-item>
          </el-tooltip>
        </div>
      </el-submenu>
    </div>
  </el-menu>
</template>

<script>
export default {
  name: "KnowledgeSectionSelect",
  props: ["courseView"],
  methods: {
    //处理目录栏的点击事件
    handleMenu(sid) {
      this.$store.commit("setSectionId", sid);
      window.scrollTo(0, 0);
      this.$emit("setSectionView", sid);
      this.$emit("setCsdn", sid);
    }
  }
};
</script>

<style scoped>
.section-select-menu {
  overflow: hidden;
  text-align: left;
}
</style>
