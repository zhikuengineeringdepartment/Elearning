<!--上传图片或者知识见解组件-->
<template>
  <el-col class="resources-file-upload">
    <el-form ref="form" :model="uploadForm" label-width="80px">
      <el-form-item label="选择文件">
        <el-upload
          action="file/upload"
          ref="upload"
          name="fileList"
          :file-list="uploadForm.fileList"
          :limit="1"
          :on-exceed="handleExceed"
          :on-change="change_file_list"
          :on-remove="handleRemove"
          :auto-upload="false"
          :with-credentials="true"
        >
          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
          <div slot="tip" class="resources-file-upload-tips">{{tip}}</div>
        </el-upload>
      </el-form-item>
      <el-form-item label="所属课程">
        <course-select @get-course-value="setCourseValue"></course-select>
      </el-form-item>
      <el-form-item label="所属章节">
        <el-cascader v-model="uploadForm.section" :options="options" clearable></el-cascader>
      </el-form-item>
      <el-form-item>
        <el-button size="small" type="success" @click="submitUpload">上传到服务器</el-button>
      </el-form-item>
      <el-form-item v-if="uploadForm.url" label="URL">
        <span>{{uploadForm.url}}</span>
      </el-form-item>
    </el-form>
  </el-col>
</template>

<script>
import CourseSelect from "./CourseSelect";

export default {
  name: "Upload",
  props: {
    tip: String,
    options: Array
  },
  components: { CourseSelect },
  data() {
    return {
      courses: [],
      uploadForm: {
        fileList: [],
        course: -1,
        section: -1,
        url: ""
      },
    };
  },
  methods: {
    handleExceed(files, fileList) {
      this.$message.warning(`一次只能上传一个文件`);
    },
    change_file_list(file, flist) {
      console.log(this.$refs.upload.uploadFiles);
      this.uploadForm.fileList = flist;
      this.uploadForm.url = "";
    },
    setCourseValue: function(cid) {
      this.uploadForm.course = cid;
    },
    handlePreview(file) {
      console.log(file);
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    submitUpload() {
      console.log(this.uploadForm);
    }
  }
};
</script>

<style lang="less" scoped>
.resources-file-upload {
  text-align: left;
  width: 450px;

  .resources-file-upload-tips {
    display: inline;
    margin-left: 1vmin;
  }
}
</style>
