<template>
  <el-card shadow="hover" style="margin: 10px 0px">
    <el-row>
      <el-col>
        <el-row>
          <el-col :span="8">{{jfile.fileName}}</el-col>
          <el-col :span="8">{{jfile.upper}}上传于{{jfile.fileUploadTime}}</el-col>
          <el-col :span="8">下载量:{{jfile.fileDownloadCount}}</el-col>
        </el-row>
      </el-col>
      <el-col>
        <el-row>
          <el-col :span="12">
            <my_tag :file_tags="jfile.fileKeys"></my_tag>
          </el-col>
          <el-col :span="12" style="display: flex;justify-content: center">
            <el-button type="primary" icon="el-icon-document" circle @click="handlePreview(jfile.fid)"></el-button>
            <el-button type="success" icon="el-icon-download" circle @click="handleDownload(jfile.fid)"></el-button>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </el-card>
</template>

<script>
  export default {
    name: "ResourcesFile",
    props: ["jfile"],
    methods: {
      handlePreview: function (fid) {
        console.log("预览文件" + fid);
        window.open("/preview.html?fid=" + fid)
      },
      handleDownload: function (fid) {
        if (root.login) {
          window.open('file/download?fid=' + fid + '&uid=' + 0)
        } else {
          this.$router.push("/login")
        }
      }
    }
  }
</script>

<style scoped>

</style>
