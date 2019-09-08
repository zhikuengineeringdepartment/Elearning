<!--用户-文件上传记录组件-->
<template>
  <div>
    <div v-if="fileUploadRecords.length === 0">
      <div>没有上传的文件记录哦！</div>
    </div>
    <div v-if="fileUploadRecords.length > 0">
      <el-card shadow="hover" class="user-upload" v-for="(fileUploadRecord, index) in fileUploadRecords" :key="index">
        <el-row>
          <el-col>
            <h3>{{fileUploadRecord.fileName}}</h3>
          </el-col>
          <el-col>
            <span>上传时间：{{fileUploadRecord.fileUploadTime}}</span>
          </el-col>
          <el-col>
            <p>{{fileUploadRecord.fileDesc}}</p>
          </el-col>
          <el-col class="user-upload-detail">
            <file-tag
              :status="fileUploadRecord.fileStatus"
              :tags="fileUploadRecord.fileKeys"
            />
          </el-col>
        </el-row>
      </el-card>
    </div>
  </div>
</template>

<script>
  import FileTag from "../../../components/FileTag";
  
  export default {
    name: "UserUpload",
    components: {FileTag},
    data() {
      return {
        fileUploadRecords: []
      }
    },
    mounted() {
      const _this = this;
      
      // 请求上传文件记录的数据
      this.$http.get('/user/getUploadRecords', {
        params: {
          uid: 0,
          page: 1
        }
      }).then(
        function (response) {
          if (response.data.code === 200) {
            console.log(response.data);
            _this.fileUploadRecords = response.data.data.fileUploadRecords;
          } else {
            this.$message({showClose: true, message: response.data.message, type: 'error'});
          }
        }
      ).catch(function (err) {
        console.log(err);
      });
    }
  }
</script>

<style lang="less" scoped>
  .user-upload {
    margin: 2vmin 0;
    
    h3 {
      margin: 1vmin;
      font-size: 2vmin;
    }
    
    p {
      margin: 1vmin;
      font-size: 1.5vmin;
    }
    
    span {
      margin: 1vmin;
      font-size: 1.5vmin;
    }
    
    .user-upload-detail {
      margin: 1vmin 1vmin 0 1vmin;
    }
  }
</style>
