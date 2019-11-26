<template>
  <el-row type="flex">
    <upload :options="opthions" tip="仅支持上传md格式文件"></upload>
    <knowledge-table :courses="courses" :chapters="chapters"></knowledge-table>
  </el-row>
</template>


<script>
import KnowledgeTable from "./KnowledgeTable";
import Upload from "../../../../components/Upload";

export default {
  components: { KnowledgeTable, Upload },
  data() {
    return {
      courses: [],
      chapters: [],
      chapters: [
        "第一章",
        "第二章",
        "第三章",
        "第四章",
        "第五章",
        "第六章",
        "第七章",
        "第八章",
        "第九章",
        "第十章"
      ]
    };
  },
  computed: {
    opthions() {
      let opthions = [];
      this.chapters.forEach(item => {
        opthions.push({ label: item, value: item });
      });
      return opthions;
    }
  }
};
</script>