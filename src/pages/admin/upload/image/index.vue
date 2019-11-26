<template>
  <el-row type="flex">
    <upload :options="options" tip="限上传png/jpg等图片"></upload>
    <img-table :courses="courses" :chapters="chapters" :sections="sections"></img-table>
  </el-row>
</template>


<script>
import ImgTable from "./ImgTable";
import Upload from "../../../../components/Upload";

export default {
  components: { ImgTable, Upload },
  data() {
    return {
      courses: [],
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
      ],
      sections: [
        "第一节",
        "第二节",
        "第三节",
        "第四节",
        "第五节",
        "第六节",
        "第七节",
        "第八节",
        "第九节",
        "第十节"
      ]
    };
  },
  computed: {
    //所属章节
    options() {
      let options = [];
      let section = [];
      this.sections.forEach(item => {
        section.push({ value: item, label: item });
      });
      this.chapters.forEach(item => {
        options.push({ value: item, label: item, children: section });
      });
      return options;
    }
  }
};
</script>