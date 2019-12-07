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
      <div class="el-upload__tip" slot="tip">只能上传md文件，且内容不宜过长</div>
    </el-upload>

    <knowledge-preview v-if="file[0]" :sectionViewMap="sectionViewMap"></knowledge-preview>
  </el-row>
</template>


<script>
import showdown from "showdown";
import KnowledgePreview from "./KnowledgePreview";
import { parse, getInstance } from "../../../tools";

export default {
  components: { KnowledgePreview },
  data() {
    return {
      file: [],
      sectionViewMap: [],
      converter: null
    };
  },
  created() {
    this.converter = new showdown.Converter();
    this.converter.setOption("tables", true);
    this.converter.setOption("simpleLineBreaks", true);
  },
  methods: {
    handleExceed(files, fileList) {
      this.$message.warning(`一次只能预览一个文件`);
    },
    change_file_list(file, flist) {
      let _this = this;
      _this.file = [file];

      let form = new FormData();
      form.append("file", file.raw);

      const loading = _this.$loading({
        lock: true,
        text: "加载中",
        spinner: "el-icon-loading"
      });

      getInstance()
        .post("/backstage/course/preview", form)
        .then(res => {
          console.log(res.data);
          if (res.data.code === 200) {
            let sectionViewMap = [];
            for (let key in res.data.data.sectionViewMap) {
              let sectionView = res.data.data.sectionViewMap[key];
              if (sectionView) {
                // 将markdown转换为html
                sectionView.sectionNameHtml = _this.converter.makeHtml(
                  parse(sectionView.sectionName)
                );
                for (const knowledge of sectionView.knowledgeViews) {
                  knowledge.knowledgeNameHtml = _this.converter.makeHtml(
                    parse(knowledge.knowledgeName)
                  );
                  for (const paragraph of knowledge.paragraphs) {
                    let paragraphContentHtml = _this.converter.makeHtml(
                      parse(paragraph.paragraphContent)
                    );
                    if (paragraphContentHtml.indexOf("<pre") !== -1) {
                      paragraphContentHtml = paragraphContentHtml.replace(
                        "<pre",
                        '<pre class="code-content"'
                      );
                    } else if (paragraphContentHtml.indexOf("<img") !== -1) {
                      paragraphContentHtml = paragraphContentHtml.replace(
                        "<img",
                        '<img class="img-content"'
                      );
                    }
                    paragraph.paragraphContentHtml = paragraphContentHtml;
                  }
                }
                sectionViewMap.push(sectionView);
              }
              _this.sectionViewMap = sectionViewMap;
              loading.close();
            }
          } else {
            loading.close();
            _this.$message({ message: res.data.message, type: "warning" });
          }
        })
        .catch(res => {
          loading.close();
          console.log(JSON.stringify(res));
          _this.$message({ message: "请上传格式正确的文件", type: "warning" });
        });
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