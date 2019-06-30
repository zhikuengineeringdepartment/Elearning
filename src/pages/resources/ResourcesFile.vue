<template>
  <el-card shadow="hover" class="resources-file-item">
    <el-row>
      <el-col>
        <el-row>
          <el-col :span="8">{{fileItem.fileName}}</el-col>
          <el-col :span="8">{{fileItem.upper}}上传于{{fileItem.fileUploadTime}}</el-col>
          <el-col :span="8">下载量:{{fileItem.fileDownloadCount}}</el-col>
        </el-row>
      </el-col>
      <el-col>
        <el-row class="resources-file-footer">
          <el-col :span="16">
            <file-tag :tags="fileItem.fileKeys"></file-tag>
          </el-col>
          <el-col :span="8" class="resources-file-footer-button">
            <el-button type="primary" icon="el-icon-document" circle @click="handlePreview(fileItem.fid)"></el-button>
            <el-button type="success" icon="el-icon-download" circle @click="handleDownload(fileItem.fid)"></el-button>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </el-card>
</template>

<script>
  import FileTag from "../../components/FileTag";
  
  export default {
    name: "ResourcesFile",
    components: {FileTag},
    props: ["fileItem"],
    methods: {
      handlePreview: function (fid) {
        console.log("预览文件" + fid);
        // window.open("/preview.html?fid=" + fid)
      },
      handleDownload: function (fid) {
        if (this.$store.state.isLogin) {
          console.log("下载文件");
          // window.open('file/download?fid=' + fid + '&uid=' + 0)
        } else {
          // this.$router.push("/login")
        }
      }
    }
  }
</script>

<style lang="less" scoped>
.resources-file-item {
  margin: 2vmin 0;
  font-size: 2vmin;
  
  .resources-file-footer {
    margin: 5vmin 0 0 0;
    
    .resources-file-footer-button {
      display: flex;
      justify-content: center;
    }
  }
}
</style>
