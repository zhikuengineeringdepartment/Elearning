<template>
  <!--<el-menu :default-active="that.$route.params.sid" class="el-menu-vertical-demo">-->
  <el-menu class="section-select-menu">
    <div v-for="section in courseView.sections" :key="section.index">
      <el-submenu :index="section.index.toString()">
        <span slot="title">{{section.title}}</span>
        <div v-for="sub in section.sub" :key="sub.sid">
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
  // props: ["courseView"],
  props: {
    courseView: {
      type: [Object],
      required: true
    }
  },
  methods: {
    //处理目录栏的点击事件
    handleMenu(sid) {
      this.$store.commit("setSectionId", sid);
      window.scrollTo(0, 0);
      this.$emit("getSectionView", sid);
      this.$emit("getCsdn", sid);
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
