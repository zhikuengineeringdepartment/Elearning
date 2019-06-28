<!--用户-文件上传记录组件-->
<template>
  <el-card shadow="hover" style="margin: 10px 0px">
    <el-row style="margin-bottom: 0px">
      <el-col>
        <h3 style="margin: 0;">{{file_upload_record.fileName}}</h3>
      </el-col>
      <el-col>
        <p style="margin: 0">{{file_upload_record.fileDesc}}</p>
      </el-col>
      <el-col style="margin: 10px 0px 20px">
        <file-tag :tags="file_upload_record.fileKeys"></file-tag>
        <span style="float: right">{{file_upload_record.fileStatus}}</span>
      </el-col>
      <el-col>
        <span>
          <label>上传时间：</label>
          {{file_upload_record.fileUploadTime}}
        </span>
        <el-button size="small" style="float: right">编辑</el-button>
      </el-col>
    </el-row>
  </el-card>
</template>

<script>
  import FileTag from "../../../components/FileTag";
  
  export default {
    name: "UserUpload",
    components: {FileTag},
    data () {
      return {
        file_upload_record: {
          fileName: "数据结构",
          fileDesc: "这是一门非常有用的课程",
          fileKeys: ["软件工程", "很重要"],
          fileStatus: "正常",
          fileUploadTime: "2019-1-1 12:00"
        }
      }
    }
  }
</script>

<style scoped>

</style>
