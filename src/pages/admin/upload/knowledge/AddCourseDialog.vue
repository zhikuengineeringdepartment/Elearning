<!--添加课程对话框-->
<template>
  <el-dialog title="添加课程" @close="cancel" :visible="isVisible">
    <el-form :model="course">
      <el-form-item label="课程名" :label-width="formLabelWidth">
        <el-input v-model="course.title" autocomplete="off" style="width:50%;" placeholder="课程名称"></el-input>
      </el-form-item>
      <el-form-item label="描述" :label-width="formLabelWidth">
        <el-input
          v-model="course.describe"
          autocomplete="off"
          style="width:50%;"
          placeholder="课程描述"
        ></el-input>
      </el-form-item>
      <el-form-item label="初始版本号" :label-width="formLabelWidth">
        <el-input
          v-model="course.vid"
          autocomplete="off"
          style="width:50%;"
          placeholder="如 V1.0，V2.1"
        ></el-input>
      </el-form-item>
      <el-form-item label="封面URL" :label-width="formLabelWidth">
        <el-input
          v-model="course.iconPath"
          autocomplete="off"
          style="width:50%;"
          placeholder="不填则默认封面"
        ></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="cancel">取 消</el-button>
      <el-button type="primary" @click="submit">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  props: {
    isVisible: Boolean
  },
  data() {
    return {
      course: {
        title: "",
        describe: "",
        vid: "",
        iconPath: ""
      },
      formLabelWidth: "120px"
    };
  },
  methods: {
    cancel() {
      this.$emit("cancel");
    },
    submit() {
      this.$emit("submit", this.course);
    }
  }
};
</script>