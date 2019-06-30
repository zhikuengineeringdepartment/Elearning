<template>
  <el-main class="resources">
      <el-col :span="16">
        <el-card shadow="always" class="resources-card">
          <h1>Hello , world</h1>
          <p>每一个不曾起舞的日子都是对生命的浪费</p>
          <el-button type="primary" @click="gotoUpload">上传<i class="el-icon-upload el-icon--right"></i></el-button>
        </el-card>
      </el-col>
      
      <el-col :span="16" @scroll.native="lazyLoading">
        <el-row>
          <div class="resources-select">
            <course-select v-on:get-course-value="set_course_value"></course-select>
            <el-input placeholder="搜索" v-model="fileListForm.keyWord">
              <el-button slot="append" icon="el-icon-search" @click="doSearch"></el-button>
            </el-input>
            <div v-if="fileListForm.order">
              <el-button icon="el-icon-sort-down" @click="changeOrder">按时间降序</el-button>
            </div>
            <div v-if="!fileListForm.order">
              <el-button icon="el-icon-sort-up" @click="changeOrder">按时间升序</el-button>
            </div>
            
          </div>
        </el-row>
      </el-col>
      
      <el-col :span="16" class="file-list-detail">
        <resources-file v-for="fileItem in my_files" :fileItem="fileItem"></resources-file>
      </el-col>
  </el-main>
</template>

<script>
  import CourseSelect from "../../components/CourseSelect";
  import ResourcesFile from "./ResourcesFile";
  import {routerChange} from "../../tools";

  export default {
    name: "Resources",
    components: {ResourcesFile, CourseSelect},
    data() {
      return {
        fileListForm: {
          keyWord: '',
          order: true,
          fileCourse: '',
          page: 1
        },
        my_files: [
          {
            "fid": 44,
            "filePath": null,
            "fileName": "数据库系统_山软智库知识见解_V1.0.pdf",
            "fileCourse": 124,
            "fileTeacher": "",
            "fileType": "pdf",
            "fileUpper": 10038,
            "fileUploadTime": "2019-06-20 08:29:06",
            "fileDownloadCount": 15,
            "fileDesc": "",
            "fileStatus": "n",
            "fileScore": 3.0,
            "fileSha": null,
            "upperName": "宁叔",
            "fileKeys": {
              "fid": 44,
              "key1": "智库知识见解",
              "key2": "下载多",
              "key3": "好评多",
              "key4": null,
              "key5": null,
              "key6": null,
              "key7": null,
              "key8": null,
              "key9": null,
              "key10": null
            }
          },
          {
            "fid": 43,
            "filePath": null,
            "fileName": "操作系统_山软智库知识见解_V1.0.pdf",
            "fileCourse": 125,
            "fileTeacher": "",
            "fileType": "pdf",
            "fileUpper": 10038,
            "fileUploadTime": "2019-06-20 08:28:48",
            "fileDownloadCount": 16,
            "fileDesc": "",
            "fileStatus": "n",
            "fileScore": 3.0,
            "fileSha": null,
            "upperName": "宁叔",
            "fileKeys": {
              "fid": 43,
              "key1": "智库知识见解",
              "key2": "好评多",
              "key3": null,
              "key4": null,
              "key5": null,
              "key6": null,
              "key7": null,
              "key8": null,
              "key9": null,
              "key10": null
            }
          }
        ],
      }
    },
    methods: {
      gotoUpload: function () {
        routerChange('/resources/upload' , this);
      },
      changeOrder() {
        this.fileListForm.order = !this.fileListForm.order;
        this.my_files.reverse();
      },
      set_course_value: function (cid) {
        this.fileListForm.fileCourse = cid;
      },
      doSearch: function () {
        this.my_files = [];
        this.fileListForm.page = 1;
        this.getFileList(this.fileListForm.page);
      },
      getFileList: function (page) {
        var _this = this;
        axios.get('file/getFileList', {
          params: {
            keyWord: this.fileListForm.keyWord,
            fileCourse: this.fileListForm.fileCourse,
            page: page,
            order: this.fileListForm.order
          }
        })
          .then(function (response) {
            for (var i = 0; i < response.data.data.files.length; i++) {
              response.data.data.files[i].fileUploadTime = getFormatDate(response.data.data.files[i].fileUploadTime)
            }
            if (response.data.data.files.length != 0) {
              _this.my_files = _this.my_files.concat(response.data.data.files);
            } else {
              // alert("已经到最后了")
            }
          })
          .catch(function (err) {
            console.log(err);
          });
      },
      scrollToDown() {
        //TODO 目前通过判断路径的方式只在文件部分实现滚动事件，不过不是长久之计，应该修改为只对特定组件或者特定页面的滚动事件
        document.onscroll = e => {
          if (this.$route.path === '/fileMain') {
            this.lazyLoading();
          }
        }
      },
      lazyLoading() { // 滚动到底部，再加载的处理事件
        let scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
        let clientHeight = document.documentElement.clientHeight;
        let scrollHeight = document.documentElement.scrollHeight;
        if (scrollTop + clientHeight >= scrollHeight) { // 如果滚动到接近底部，自动加载下一页
          //事件处理
          this.fileListForm.page++;
          console.log(this.fileListForm.page)
          this.getFileList(this.fileListForm.page)
        }
      }
    }
  }
</script>

<style lang="less" scoped>
  .resources {
    margin-top: 3vmin;
    display: flex;
    flex-direction: column;
    align-items: center;
    
    .resources-card {
      margin-bottom: 3vmin;
      padding: 20px;
      
      h1 {
        font-size: 5vmin;
        margin: 1vmin auto;
      }
      
      p {
        font-size: 2vmin;
        margin: 2vmin auto;
      }
    }
  
    .resources-select {
      display: flex;
      flex-direction: row;
      margin-bottom: 3vmin;
    }
  
    .file-list-detail {
    }
  }
</style>
