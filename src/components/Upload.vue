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
          :limit="2"
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
        <slot name="add"></slot>
      </el-form-item>
      <el-form-item label="所属章节" v-if="isShow">
        <el-cascader v-model="uploadForm.section" :options="options" clearable></el-cascader>
      </el-form-item>
      <slot name="section"></slot>
      <el-form-item>
        <el-button size="small" type="success" @click="submitUpload">上传到服务器</el-button>
      </el-form-item>
      <slot name="url"></slot>
    </el-form>
  </el-col>
</template>

<script>
import CourseSelect from "./CourseSelect";

export default {
  name: "Upload",
  props: {
    tip: String,
    options: Array,
    isShow: {
      type: Boolean,
      default: true
    }
  },
  components: { CourseSelect },
  data() {
    return {
      courses: [],
      uploadForm: {
        fileList: [],
        course: -1,
        section: 0
      }
    };
  },
  methods: {
    handleExceed(files, fileList) {
      this.$message.warning(`一次只能上传一个文件`);
    },
    change_file_list(file, flist) {
      // console.log(this.$refs.upload.uploadFiles);
      this.uploadForm.fileList = [file];
      this.$emit("clearUrl");
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
