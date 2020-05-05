<template>
  <el-table :data="categoryData" max-height="300">
    <el-table-column label="专栏详情记录" align="center">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="table-expand">
            <el-form-item label="专栏id">
              <span>{{ props.row.sid }}</span>
            </el-form-item>
            <el-form-item label="专栏名称">
              <span>{{ props.row.specialcName }}</span>
            </el-form-item>
            <!-- <el-form-item label="文章描述">
                <span>{{ props.row.id }}</span>
            </el-form-item>-->
            <el-form-item label="创建时间">
              <span>{{ props.row.createTime }}</span>
            </el-form-item>
            <el-form-item label="更新时间">
              <span>{{ props.row.updateTime }}</span>
            </el-form-item>
            <el-form-item label="专栏描述">
              <span>{{ props.row.specialcRemark }}</span>
            </el-form-item>
            <el-form-item label="状态">
              <span>{{ props.row.status }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column prop="sid" label="专栏id" align="center" width="80"></el-table-column>
      <el-table-column prop="specialcName" label="专栏名称" align="center"></el-table-column>
      <el-table-column
        prop="status"
        label="状态"
        align="center"
        :filters="[{ text: '已删除', value: '已删除' }, { text: '正常', value: '正常' }]"
        :filter-method="filterStatue"
        filter-placement="bottom-end"
      ></el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <!-- edit需要先打开弹窗 -->
          <el-button @click="editBtn(scope.row)" type="text" size="small">编辑</el-button>
          <el-button @click="deleteReq(scope.row)" type="text" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table-column>
    <!-- edit对话框 -->
    <el-dialog title="修改专栏信息" :visible.sync="editDialogVisible" :append-to-body="true">
      <el-form :model="editCategoryForm">
        <label>原名称：{{editRowInfo.specialcName}}</label>
        <br />
        <label>原描述：{{editRowInfo.specialcRemark}}</label>
        <el-form-item label="新的专栏名称">
          <el-input v-model="editCategoryForm.specialcName"></el-input>
        </el-form-item>
        <el-form-item label="新的专栏描述">
          <el-input v-model="editCategoryForm.speaiclcRemark"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeDialog">取 消</el-button>
        <el-button type="primary" @click="editReq">确 定</el-button>
      </div>
    </el-dialog>
  </el-table>
</template>

<style>
.el-table__header tr,
.el-table__header th {
  padding: 0;
  height: 40px;
}
</style>

<script>
export default {
  props: {
    categoryData: Array,
    editDialogVis: Boolean
  },
  data() {
    return {
      editCategoryForm: {}, //用于绑定“编辑”的表单
      editRowInfo: {}, //编辑的那一行的相关信息
      editDialogVisible: false
    };
  },
  created() {
    this.editDialogVisible = this.editDialogVis;
  },
  //   watch
  watch: {
    editDialogVis: function(val) {
      this.editDialogVisible = val;
    }
  },
  methods: {
    filterStatue: function(value, row) {
      return row.status === value;
    },
    editBtn: function(row) {
      this.editDialogVisible = true;
      this.editRowInfo = row;
      //   this.$emit("edit", row);
      //   console.log("yep,emit");
    },
    //关闭对话框
    closeDialog: function() {
      this.editDialogVisible = false;
      this.editRowInfo = {};
      //视反馈加不加
      // this.editCategoryForm={}
    },
    editReq: function() {
      let _this = this;
      if (
        _this.editCategoryForm.specialcName &&
        _this.editCategoryForm.specialcName.trim() != ""
      )
        console.log(_this.editCategoryForm.specialcName);
      let editQueryData = {
        sid: _this.editRowInfo.sid,
        specialcName:
          _this.editCategoryForm.specialcName &&
          _this.editCategoryForm.specialcName.trim() != ""
            ? _this.editCategoryForm.specialcName
            : _this.editRowInfo.specialcName,
        speaiclcRemark:
          _this.editCategoryForm.speaiclcRemark &&
          _this.editCategoryForm.speaiclcRemark.trim() != ""
            ? _this.editCategoryForm.speaiclcRemark
            : _this.editRowInfo.speaiclcRemark
      };
      _this.$emit("edit", editQueryData);
      //   _this.editDialogVisible = false;
    },
    deleteReq: function(row) {
      console.log(row);
      let reqData = { id: row.sid };
      this.$emit("delete", reqData);
    }
  }
};
</script>