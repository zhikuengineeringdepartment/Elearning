<!--上传要预览的知识见解-->
<template>
  <el-row col="12">
    <el-upload
      action="/backstage/course/preview"
      ref="upload"
      name="fileList"
      drag
      :file-list="file"
      :limit="2"
      :on-exceed="handleExceed"
      :on-change="change_file_list"
      :on-remove="handleRemove"
      :on-success="handleSuccess"
      :with-credentials="true"
      :auto-upload="false"
    >
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">
        将文件拖到此处，或
        <em>点击预览</em>
      </div>
      <div class="el-upload__tip" slot="tip">
        只能上传md文件，且内容不宜过长
      </div>
    </el-upload>

    <knowledge-preview
      v-if="file[0]"
      :sectionViewMap="sectionViewMap"
    ></knowledge-preview>
  </el-row>
</template>

<script>
import KnowledgePreview from "./KnowledgePreview";
import { queryPreviewKnowledge } from "../../../app/apis/adminApi";

export default {
  components: { KnowledgePreview },
  data() {
    return {
      file: [],
      sectionViewMap: []
    };
  },
  methods: {
    handleExceed(files, fileList) {
      this.$message.warning(`一次只能预览一个文件`);
    },
    change_file_list(file, flist) {
      this.file = [file];

      let form = new FormData();
      form.append("file", file.raw);

      const loading = this.$loading({
        lock: true,
        text: "加载中",
        spinner: "el-icon-loading"
      });
      queryPreviewKnowledge(form)
        .then(response => {
            for (let index in response.data.data.sectionViewMap) {
                this.sectionViewMap.push(
                    this.$fn.markdown2Html(response.data.data.sectionViewMap[index])
                );
            }
            loading.close();
        })
        .catch(err => console.log(err));
    },
    handleSuccess(response, file, fileList) {
      console.log(file, response);
    },
    handlePreview(file) {
      console.log(file);
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    }
  }
};
</script>
