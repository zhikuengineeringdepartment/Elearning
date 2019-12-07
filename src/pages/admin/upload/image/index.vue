<!--上传图片-->
<template>
  <el-row type="flex">
    <upload
      @upload="upload"
      @clearUrl="clearUrl"
      :options="options"
      :url="url"
      tip="限上传png/jpg/gif等格式文件"
    >
      <el-form-item slot="url" label="URL">
        <span v-if="url" class="url">{{url}}</span>
      </el-form-item>
    </upload>
    <img-table
      @loadData="getTableData"
      :courses="courses"
      :chapters="chapters"
      :sections="sections"
      :tableData="tableData"
      :loading="loading"
    ></img-table>
  </el-row>
</template>


<script>
import ImgTable from "./ImgTable";
import Upload from "../../../../components/Upload";
import { getInstance } from "../../../../tools";

export default {
  name: "UploadImg",
  components: { ImgTable, Upload },
  data() {
    return {
      courses: [],
      chapters: [],
      sections: [],
      tableData: [],
      url: null,
      loading: false
    };
  },
  created() {
    this.getCourses();
    this.getTableData();
  },
  computed: {
    //所属章节
    options() {
      return this.getOptions();
    }
  },
  methods: {
    upload(uploadForm) {
      let _this = this;

      if (
        !uploadForm.section ||
        !uploadForm.fileList[0] ||
        uploadForm.course === -1
      ) {
        _this.$message.warning(`请将信息填写完整`);
        return;
      }

      let form = new FormData();
      form.append("file", uploadForm.fileList[0].raw);
      form.append("cid", uploadForm.course);
      form.append("sections", uploadForm.section);

      for (var [a, b] of form.entries()) {
        console.log(a, b);
      }

      const loading = _this.$loading({
        lock: true,
        text: "上传中",
        spinner: "el-icon-loading"
      });

      getInstance()
        .post("/picture/upload", form)
        .then(res => {
          console.log(res);
          if (res.data.code === 200) {
            this.url = res.data.data.url;
          } else {
            _this.$message({ message: res.data.message, type: "warning" });
          }
          loading.close();
        })
        .catch(res => {
          loading.close();
          console.log(JSON.stringify(res));
          _this.$message({ message: "请上传格式正确的文件", type: "warning" });
        });
    },
    //获得级联选择器的options，表格过滤其的章节
    getOptions() {
      let arr = [
        "一",
        "二",
        "三",
        "四",
        "五",
        "六",
        "七",
        "八",
        "九",
        "十",
        "十一",
        "十二",
        "十三",
        "十四",
        "十五"
      ];
      let _chapters = [];
      let _sections = [];
      arr.forEach(i => {
        let c = {
          value: "第" + i + "章",
          label: "第" + i + "章",
          text: "第" + i + "章"
        };
        let s = {
          value: "第" + i + "节",
          label: "第" + i + "节",
          text: "第" + i + "节"
        };
        _chapters.push(c);
        _sections.push(s);
      });

      let options = [];
      _chapters.forEach((item, index) => {
        options.push({
          value: item.value,
          label: item.label,
          children: _sections
        });
      });

      this.chapters = _chapters;
      this.sections = _sections;
      return options;
    },
    //获得表格过滤器的课程
    getCourses() {
      let _this = this;
      _this.$http
        .get("/course/getAllCourse")
        .then(function(response) {
          if (response.data.code === 200) {
            let tem = [];
            response.data.data.courses.forEach(item => {
              tem.push({ text: item.courseName, value: item.courseName });
            });
            _this.courses = tem;
          } else {
            this.$message({
              showClose: true,
              message: response.data.message,
              type: "error"
            });
          }
        })
        .catch(function(err) {
          console.log(err);
        });
    },
    getTableData() {
      let _this = this;
      _this.loading = true;
      _this.$http
        .get("/picture/list")
        .then(function(response) {
          if (response.data.code === 200) {
            let tem = [];
            response.data.data.pictures.forEach(item => {
              tem.push({
                name: item.orgName,
                course: item.course,
                chapter: item.sectionList ? item.sectionList[0] : null,
                section: item.sectionList ? item.sectionList[1] : null,
                url: item.url
              });
            });
            _this.tableData = tem;
            _this.loading = false;
          } else {
            _this.loading = false;
            this.$message({
              showClose: true,
              message: response.data.message,
              type: "error"
            });
          }
        })
        .catch(function(err) {
          _this.loading = false;
          console.log(err);
        });
    },
    clearUrl() {
      this.url = null;
    }
  }
};
</script>

<style>
.url {
  word-break: normal;
  width: auto;
  display: block;
  white-space: pre-wrap;
  word-wrap: break-word;
  overflow: hidden;
}
</style>