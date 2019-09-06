<!--用户-文件下载记录组件-->
<template>
  <div>
    <div v-if="fileDownloadRecords.length === 0">
      <div>没有下载的文件记录哦！</div>
    </div>
    <div v-if="fileDownloadRecords.length > 0">
      <el-card
        v-for="(fileDownloadRecord, index) in fileDownloadRecords" shadow="hover" class="user-download" :key="index">
        <el-row>
          <el-col>
            <h3>{{fileDownloadRecord.fileView.fileName}}</h3>
          </el-col>
          <el-col>
            <span>下载时间：{{fileDownloadRecord.fopDate}}</span>
          </el-col>
          <el-col>
            <p>{{fileDownloadRecord.fileView.fileDesc}}</p>
          </el-col>
          <el-col class="user-download-detail">
            <file-tag
              :tags="fileDownloadRecord.fileView.fileKeys"
              status=""
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
    name: "UserDownload",
    components: {FileTag},
    data() {
      return {
        fileDownloadRecords: [],
        commented: true,
        score: 0
      }
    },
    mounted() {
      const _this = this;
      
      // 请求下载文件记录的数据
      this.$http.get('/user/getDownloadRecords', {
        params: {
          uid: 0,
          page: 1
        }
      }).then(
        function (response) {
          if (response.data.code === 200) {
            console.log(response.data);
            _this.fileDownloadRecords = response.data.data.fileDownloadRecords;
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
  .user-download {
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
    
    .user-download-detail {
      margin: 1vmin 1vmin 0 1vmin;
    }
  }
</style>
