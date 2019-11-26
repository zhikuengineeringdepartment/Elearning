<template>
  <el-row>
    <el-upload
      action="http://jsonplaceholder.typicode.com/posts/"
      ref="upload"
      name="fileList"
      drag
      :file-list="file"
      :limit="1"
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
      <div class="el-upload__tip" slot="tip">只能上传md文件，且内容不宜过长</div>
    </el-upload>

    <knowledge-preview v-if="file[0]" :sectionView="sectionView"></knowledge-preview>
  </el-row>
</template>


<script>
import KnowledgePreview from "./KnowledgePreview";

export default {
  components: { KnowledgePreview },
  data() {
    return {
      file: [],
      sectionView: {}
    };
  },
  methods: {
    handleExceed(files, fileList) {
      this.$message.warning(`一次只能预览一个文件`);
    },
    change_file_list(file, flist) {
      let _this = this;
      this.file = flist;
      let form = new FormData();
      form.append(
        "file",
        document.getElementsByClassName("el-upload__input")[0].files[0]
      );
    //   _this.$http
    //     .post("/file/upload", form)
    //     .then(res => {
    //       console.log(res);
    //       if (res.data.code === 200) {
    //         console.log(res.data);
    //         _this.sectionView = response.data.data.sectionView;

    //         // 将markdown转换为html
    //         let converter = new showdown.Converter();
    //         _this.sectionView.sectionNameHtml = converter.makeHtml(
    //           _this.sectionView.sectionName.toString()
    //         );
    //         for (const knowledge of _this.sectionView.knowledgeViews) {
    //           knowledge.knowledgeNameHtml = converter.makeHtml(
    //             knowledge.knowledgeName.toString()
    //           );
    //           for (const paragraph of knowledge.paragraphs) {
    //             let paragraphContentHtml = converter.makeHtml(
    //               paragraph.paragraphContent.toString()
    //             );
    //             if (paragraphContentHtml.indexOf("<pre") !== -1) {
    //               paragraphContentHtml = paragraphContentHtml.replace(
    //                 "<pre",
    //                 '<pre class="code-content"'
    //               );
    //               console.log("===", paragraphContentHtml);
    //             } else if (paragraphContentHtml.indexOf("<img") !== -1) {
    //               paragraphContentHtml = paragraphContentHtml.replace(
    //                 "<img",
    //                 '<img class="img-content"'
    //               );
    //               console.log("====", paragraphContentHtml);
    //             }
    //             paragraph.paragraphContentHtml = paragraphContentHtml;
    //           }
    //         }
    //       } else {
    //         _this.$message({ message: res.data.message, type: "warning" });
    //       }
    //     })
    // .then(() => {
    //       MathJax.typesetPromise();
    //     })
    //     .catch(res => {
    //       console.log(JSON.stringify(res));
    //       _this.$message({ message: "请上传格式正确的文件", type: "warning" });
    //     });
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