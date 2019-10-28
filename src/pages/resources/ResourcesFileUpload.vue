<!--文件资源上传-->
<!--TODO 后台上传文件会返回500，需要解决，因此这里先disabled掉了上传文件按钮-->
<template>
  <el-main>
    <el-row type="flex" justify="center">
      <el-col :span="20" class="resources-file-upload">
        <el-form ref="form" :model="uploadForm" label-width="80px">
          <el-form-item label="选择文件">
            <el-upload
              action="file/upload"
              ref="upload"
              name="multipartFile"
              :file-list="uploadForm.multipartFile"
              :limit="3"
              :on-exceed="handleExceed"
              :on-change="change_file_list"
              :on-success="handleSuccess"
              :on-remove="handleRemove"
              :auto-upload="false"
              :with-credentials="true"
            >
              <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
              <!--手机端不显示此提示-->
              <div v-if="!$store.state.isMobile">
                <div slot="tip" class="resources-file-upload-tips">可上传word,ppt,pdf类型文件，且不超过100M</div>
              </div>
            </el-upload>
          </el-form-item>
          <el-form-item label="所属课程">
            <course-select @get-course-value="setCourseValue"></course-select>
          </el-form-item>
          <el-form-item label="任课教师">
            <el-input v-model="uploadForm.fileTeacher"></el-input>
          </el-form-item>
          <el-form-item label="添加标签">
            <el-tag
              :key="tag"
              v-for="tag in uploadForm.file_tags"
              closable
              :disable-transitions="false"
              @close="handleClose(tag)"
            >{{tag}}</el-tag>
            <el-input
              class="input-new-tag"
              v-if="tagVisible"
              v-model="tagValue"
              ref="saveTagInput"
              size="small"
              @keyup.enter.native="handleInputConfirm"
              @blur="handleInputConfirm"
            ></el-input>
            <el-button v-else class="button-new-tag" size="small" @click="showInput">+ New Tag</el-button>
          </el-form-item>
          <el-form-item>
            <el-button size="small" type="success" @click="submitUpload">上传到服务器</el-button>
            <el-button size="small" type="info" @click="returnBack">取消</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </el-main>
</template>

<script>
import CourseSelect from "../../components/CourseSelect";
import { routerChange } from "../../tools";

export default {
  name: "ResourcesFileUpload",
  components: { CourseSelect },
  data() {
    return {
      tagVisible: false,
      tagValue: "",
      uploadForm: {
        fileCourse: "",
        fileTeacher: "",
        multipartFile: [],
        file_tags: []
      }
    };
  },
  methods: {
    handleExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 3 个文件，本次选择了 ${
          files.length
        } 个文件，共选择了 ${files.length + fileList.length} 个文件`
      );
    },
    handleClose(tag) {
      this.uploadForm.file_tags.splice(
        this.uploadForm.file_tags.indexOf(tag),
        1
      );
    },
    change_file_list(file, flist) {
      console.log(this.$refs.upload.uploadFiles);
      this.uploadForm.multipartFile = flist;
    },
    setCourseValue: function(cid) {
      this.uploadForm.fileCourse = cid;
    },
    handleInputConfirm() {
      let tagValue = this.tagValue;
      if (tagValue) {
        this.uploadForm.file_tags.push(tagValue);
      }
      this.tagVisible = false;
      this.tagValue = "";
    },
    showInput() {
      this.tagVisible = true;
      this.$nextTick(() => {
        this.$refs.saveTagInput.$refs.input.focus();
      });
    },
    handleSuccess(response) {
      alert(response.data.data.message);
    },
    handlePreview(file) {
      console.log(file);
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    submitUpload() {
      const _this = this;

      if (this.$store.state.isLogin) {
        let form = new FormData();
        form.append(
          "multipartFile",
          document.getElementsByClassName("el-upload__input")[0].files[0]
        );
        form.append("uid", "0");
        form.append("fileCourse", this.uploadForm.fileCourse);
        form.append("fileTeacher", this.uploadForm.fileTeacher);
        console.log(this.uploadForm.file_tags);

        for (let i = 1; i <= this.uploadForm.file_tags.length; i++) {
          form.append("key" + i, this.uploadForm.file_tags[i - 1]);
        }
        for (var [a, b] of form.entries()) {
          console.log(a, b);
        }

        _this.$http
          .post("/file/upload", form)
          .then(res => {
            console.log(res);
            if (res.data.code === 200) {
              _this.$message({ message: "提交成功" });
              _this.$router.push({ path: "/" });
            } else {
              _this.$message({ message: res.data.message, type: "warning" });
            }
          })
          .catch(res => {
            console.log(JSON.stringify(res));
            _this.$message({ message: "请输入完整文件信息", type: "warning" });
          });
      } else {
        routerChange("/user/login", _this);
      }
    },
    returnBack() {
      this.$router.go(-1);
    }
  }
};
</script>

<style lang="less" scoped>
.resources-file-upload {
  border: 1px solid #ebebeb;
  padding: 8vmin;
  text-align: left;

  .resources-file-upload-tips {
    display: inline;
    margin-left: 1vmin;
  }
}
</style>
