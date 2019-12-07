<!--上传智库周记图片-->
<template>
  <el-row type="flex">
    <upload @upload="upload"></upload>
    <img-table :tableData="tableData"></img-table>
  </el-row>
</template>


<script>
import ImgTable from "./ImgTable";
import Upload from "./Upload";
import { getInstance } from "../../../tools";

export default {
  name: "UploadImg",
  components: { Upload, ImgTable },
  inject: ["reload"],
  data() {
    return {
      tableData: []
    };
  },
  created() {
    this.getTableData();
  },
  methods: {
    upload(uploadForm) {
      let _this = this;

      if (!uploadForm.date || !uploadForm.fileList[0]) {
        _this.$message.warning(`请将信息填写完整`);
        return;
      }

      let form = new FormData();
      form.append("file", uploadForm.fileList[0].raw);
      form.append("date", uploadForm.date);

      for (var [a, b] of form.entries()) {
        console.log(a, b);
      }

      getInstance()
        .post("/picture/chronology/upload", form)
        .then(res => {
          console.log(res);
          if (res.data.code === 200) {
            _this.$message({ message: "上传成功", type: "success" });
            setTimeout(() => {
              _this.reload();
            }, 1500);
          } else {
            _this.$message({ message: res.data.message, type: "warning" });
          }
        })
        .catch(res => {
          console.log(JSON.stringify(res));
          _this.$message({ message: "请上传格式正确的文件", type: "warning" });
        });
    },
    getTableData() {
      let _this = this;
      _this.$http
        .get("/picture/chronology/list")
        .then(function(response) {
          if (response.data.code === 200) {
            let tem = [];
            response.data.data.pictures.forEach(item => {
              tem.push({
                date: item.date.split(" ")[0],
                url: item.url
              });
            });
            _this.tableData = tem;
          } else {
            _this.$message({
              showClose: true,
              message: response.data.message,
              type: "error"
            });
          }
        })
        .catch(function(err) {
          console.log(err);
        });
    }
  }
};
</script>