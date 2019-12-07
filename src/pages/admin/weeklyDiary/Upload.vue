<!--上传智库周记图片-->
<template>
  <el-col class="resources-file-upload">
    <el-form ref="form" :model="uploadForm" label-width="80px">
      <el-form-item label="选择图片">
        <el-upload
          action="file/upload"
          ref="upload"
          name="fileList"
          :file-list="uploadForm.fileList"
          :limit="2"
          :on-exceed="handleExceed"
          :on-change="change_file_list"
          :auto-upload="false"
          :with-credentials="true"
        >
          <el-button slot="trigger" size="small" type="primary">选取图片</el-button>
        </el-upload>
      </el-form-item>
      <el-form-item label="选择日期">
        <el-date-picker v-model="uploadForm.date" type="date" placeholder="选择日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button size="small" type="success" @click="submitUpload">上传</el-button>
      </el-form-item>
    </el-form>
  </el-col>
</template>

<script>
export default {
  name: "weeklyDiaryUpload",
  data() {
    return {
      uploadForm: {
        fileList: [],
        date: 0
      }
    };
  },
  methods: {
    handleExceed(files, fileList) {
      this.$message.warning(`一次只能上传一个文件`);
    },
    change_file_list(file, flist) {
      this.uploadForm.fileList = [file];
    },
    submitUpload() {
      this.$emit("upload", this.uploadForm);
    }
  }
};
</script>

<style lang="less" scoped>
.resources-file-upload {
  text-align: left;
  width: 450px;
  padding-right: 100px;

  .resources-file-upload-tips {
    display: inline;
    margin-left: 1vmin;
  }
}
</style>