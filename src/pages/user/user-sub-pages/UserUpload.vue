<!--用户-文件上传记录组件-->
<template>
  <el-card shadow="hover" class="user-upload">
    <el-row>
      <el-col>
        <h3>{{file_upload_record.fileName}}</h3>
      </el-col>
      <el-col>
        <span>上传时间：{{file_upload_record.fileUploadTime}}</span>
      </el-col>
      <el-col>
        <p>{{file_upload_record.fileDesc}}</p>
      </el-col>
      <el-col class="user-upload-detail">
        <file-tag class="user-upload-tag" :status="file_upload_record.fileStatus" :tags="file_upload_record.fileKeys"></file-tag>
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

<style lang="less" scoped>
.user-upload {
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
  
  .user-upload-detail {
    margin: 5vmin 1vmin 1vmin 1vmin;
  }
}
</style>
