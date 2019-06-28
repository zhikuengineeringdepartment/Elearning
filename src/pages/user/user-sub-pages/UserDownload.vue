<!--用户-文件下载记录组件-->
<template>
  <el-card shadow="hover" style="margin: 10px 0px">
    <el-row style="margin-bottom: 0px">
      <el-col>
        <h3 style="margin: 0;">{{file_download_record.fileView.fileName}}</h3>
      </el-col>
      <el-col>
        <p style="margin: 0">{{file_download_record.fileView.fileDesc}}</p>
      </el-col>
      <el-col style="margin: 10px 0px 20px">
        <file-tag :tags="file_download_record.fileView.fileKeys"></file-tag>
      </el-col>
      <el-col>
        <span>
          <label>下载时间</label>
          {{file_download_record.fopDate}}
        </span>
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

<style scoped>

</style>
