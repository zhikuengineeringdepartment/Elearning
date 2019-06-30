<!--用户-文件下载记录组件-->
<template>
  <el-card shadow="hover" class="user-download">
    <el-row>
      <el-col>
        <h3>{{file_download_record.fileView.fileName}}</h3>
      </el-col>
      <el-col>
        <span>下载时间：{{file_download_record.fopDate}}</span>
      </el-col>
      <el-col>
        <p>{{file_download_record.fileView.fileDesc}}</p>
      </el-col>
      <el-col class="user-download-detail">
        <file-tag :tags="file_download_record.fileView.fileKeys" status=""></file-tag>
      </el-col>
    </el-row>
  </el-card>
</template>

<script>
  import FileTag from "../../../components/FileTag";
  
  export default {
    name: "UserDownload",
    components: {FileTag},
    data() {
      return {
        file_download_record: {
          fopDate: "test date",
          fileView: {
            fileName: "test file",
            fileDesc: "test desc",
            fileKeys: ["key1", "key2"]
          }
        },
        commented: true,
        score: 0
      }
    },
  }
</script>

<style lang="less" scoped>
  .user-download {
    margin: 2vmin 0;
    
    h3 {
      margin: 1vmin;
      font-size: 3vmin;
    }
    
    p {
      margin: 1vmin;
      font-size: 1.5vmin;
    }
    
    span {
      margin: 1vmin;
      font-size: 2vmin;
    }
    
    .user-download-detail {
      margin: 5vmin 1vmin 1vmin 1vmin;
    }
  }
</style>
