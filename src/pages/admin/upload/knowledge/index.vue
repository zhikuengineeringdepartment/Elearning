<!--上传知识见解-->
<template>
  <el-row type="flex">
    <upload @upload="upload" :isShow="false" tip="仅支持上传md格式文件">
      <el-form-item label="所属章节" slot="section">
        <el-tree accordion ref="tree" :data="treeData" show-checkbox :props="defaultProps"></el-tree>
      </el-form-item>
      <el-button
        slot="add"
        type="primary"
        size="small"
        style="font-size:17px;margin-left: 10px;"
        @click="showDialog"
      >+</el-button>
    </upload>
    <knowledge-table :courses="courses" :tableData="tableData"></knowledge-table>

    <add-course-dialog :isVisible="isDialogVisible" @cancel="hideDialog" @submit="createCourse"></add-course-dialog>
  </el-row>
</template>


<script>
import KnowledgeTable from "./KnowledgeTable";
import Upload from "../../../../components/Upload";
import AddCourseDialog from "./AddCourseDialog";
import { getInstance } from "../../../../tools";

export default {
  name: "UploadKnowledge",
  inject: ["reload"],
  components: { KnowledgeTable, Upload, AddCourseDialog },
  data() {
    return {
      tableData: [],
      courses: [],
      defaultProps: {
        children: "children",
        label: "label"
      },
      isDialogVisible: false
    };
  },
  computed: {
    //计算树状选择器的数据
    treeData() {
      let chapters = [];
      let sections = [];

      for (let c = 0; c < 15; c++) {
        sections = [];
        for (let s = 0; s < 15; s++) {
          sections.push({
            label: "第" + (s + 1) + "节",
            value: [c + 1, s + 1]
          });
        }
        chapters.push({
          label: "第" + (c + 1) + "章",
          value: c + 1,
          children: sections
        });
      }

      return [
        {
          label: "全选",
          children: chapters
        }
      ];
    }
  },
  created() {
    this.getTableData();
  },
  methods: {
    //上传知识讲解
    upload(uploadForm) {
      let _this = this;

      if (
        !_this.$refs.tree.getCheckedNodes()[0] ||
        uploadForm.course < 0 ||
        !uploadForm.fileList[0].raw
      ) {
        this.$message.warning(`请将信息填写完整`);
        return;
      }
      console.log(
        JSON.stringify(_this.getSeqs(_this.$refs.tree.getCheckedNodes()))
      );
      let form = new FormData();
      form.append("cid", uploadForm.course);
      form.append("file", uploadForm.fileList[0].raw);
      form.append(
        "seqs",
        JSON.stringify(_this.getSeqs(_this.$refs.tree.getCheckedNodes()))
      );

      for (var [a, b] of form.entries()) {
        console.log(a, b);
      }

      getInstance()
        .post("/backstage/course/save ", form)
        .then(res => {
          console.log(res);
          if (res.data.code === 200) {
            _this.$message({ message: "上传成功", type: "success" });
          } else {
            _this.$message({
              message: "请上传格式正确的文件",
              type: "warning"
            });
          }
        })
        .catch(res => {
          console.log(JSON.stringify(res));
          _this.$message({ message: "请上传格式正确的文件", type: "warning" });
        });
    },
    //处理数据,[{ chapter: 1, section: [] }, { chapter: 3, section: [4, 6, 8] }];
    getSeqs(sections) {
      let seqs = [];

      sections.forEach(item => {
        if (!item.children) seqs.push(item.value);
      });

      let count = -1;
      let collection = seqs.reduce((pre, cur) => {
        if (cur[0] === parseInt(Object.keys(pre)[count])) {
          pre[cur[0]].push(cur[1]);
        } else {
          count++;
          let arr = [];
          arr.push(cur[1]);
          pre[cur[0]] = arr;
        }
        return pre;
      }, {});

      seqs = [];
      for (let key in collection) {
        if (collection[key].length == 15) {
          seqs.push({
            chapter: parseInt(key),
            sections: []
          });
        } else {
          seqs.push({
            chapter: parseInt(key),
            sections: collection[key]
          });
        }
      }
      return seqs;
    },
    //添加课程对话框显示
    showDialog() {
      this.isDialogVisible = true;
    },
    //添加课程对话框隐藏
    hideDialog() {
      this.isDialogVisible = false;
    },
    //添加课程
    createCourse(course) {
      let _this = this;
      console.log(course);
      if (!course.iconPath) course.iconPath = "img/logo.jpg";
      if (!course.describe || !course.title || !course.vid) {
        this.$message({ message: "请将信息填写完整", type: "warning" });
        return;
      }

      this.$http
        .post("/backstage/course/create", course)
        .then(function(response) {
          console.log(response.data);
          if (response.data.code === 200) {
            _this.$message({ message: "创建课程成功", type: "success" });
            _this.isDialogVisible = false;
            setTimeout(() => {
              _this.reload();
            }, 1500);
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
    },
    //获取已上传知识讲解表格数据
    getTableData() {
      const _this = this;

      this.$http
        .get("/course/getAllCourse")
        .then(function(response) {
          if (response.data.code === 200) {
            let tem = [];
            response.data.data.courses.forEach(item => {
              tem.push({ text: item.courseName, value: item.courseName });
            });
            _this.courses = tem;
            return response.data.data.courses;
          } else {
            _this.$message({
              showClose: true,
              message: response.data.message,
              type: "error"
            });
          }
        })
        .then(courses => {
          _this.getCourseProgress(courses);
        })
        .catch(function(err) {
          console.log(err);
        });
    },
    getCourseProgress(courses) {
      let _this = this;
      courses.map(course => {
        _this.$http
          .post("/backstage/course/progress", {
            cid: course.cid,
            vid: course.vid
          })
          .then(function(response) {
            if (response.data.code === 200) {
              response.data.data.progress.forEach(item => {
                _this.tableData.push({
                  course: course.courseName,
                  chapter: item.chapter,
                  sections: item.sections
                });
              });
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
      });
    }
  }
};
</script>

<style>
.el-tree-node__content {
  height: 40px;
}
</style>