<template>
  <el-row type="flex" justify="space-between">
    <el-col :span="9">
      <!-- 专栏管理和文章管理 -->
      <el-collapse>
        <el-collapse-item title="专栏管理">
          <!-- <el-button type="primary" plain size="small">新建专栏</el-button> -->
          <category-table
            :categoryData="categoryData"
            :editDialogVis="editDialogVisible"
            @edit="editCategory"
            @delete="deleteCategory"
          ></category-table>
          <el-form
            :model="addCategoryForm"
            label-position="left"
            label-width="80px"
            style="text-align:left;padding:10px;margin-top:2px; border-color: #E0E0E0;border-style:solid"
          >
            <div style="text-align:left;padding-bottom:10px;font-weight:bold">新增专栏：</div>
            <el-form-item label="专栏名称">
              <el-input v-model="addCategoryForm.specialcName"></el-input>
            </el-form-item>
            <el-form-item label="专栏描述">
              <el-input v-model="addCategoryForm.speaiclcRemark"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="addCategory">确认添加</el-button>
            </el-form-item>
          </el-form>
        </el-collapse-item>
        <el-collapse-item title="文章管理"></el-collapse-item>
      </el-collapse>
    </el-col>
    <article-table :tableData="tableData" :categoryData="categoryData"></article-table>
  </el-row>
</template>

<script>
import ArticleTable from "./ArticleTable";
import CategoryTable from "./CategoryTable";
import { getInstance } from "../../../tools";
export default {
  components: { ArticleTable, CategoryTable },
  data() {
    return {
      categoryDict: {},
      //控制子组件对话框的开关
      editDialogVisible: false,
      // 从后台获取的数据
      categoryData: [
        {
          specialcName: "方向谈",
          createTime: "2020-04-05 17:20:33",
          isDelete: 0,
          specialcRemark: "方向谈",
          updateTime: "2020-04-05 17:20:38",
          sid: 1
        },
        {
          specialcName: "知识说",
          createTime: "2020-04-05 17:21:00",
          isDelete: 0,
          specialcRemark: "知识说",
          updateTime: "2020-04-05 17:21:05",
          sid: 2
        }
      ],
      tableData: [
        {
          articleTitle: "测试标题1",
          createTime: "2020-02-13 20:12:52",
          specialColumnId: 1,
          isDelete: 1,
          updateTime: "2020-04-05 20:12:56",
          aid: 1,
          articlePicUrl: "aaa",
          articleUrl: "aaa"
        },
        {
          articleTitle: "测试标题233",
          createTime: "2020-04-02 20:13:38",
          specialColumnId: 1,
          isDelete: 0,
          updateTime: "2020-04-05 20:17:27",
          aid: 2,
          articlePicUrl:
            "../pictures/b657b89f9cd5c2e4f1137eae76d2037b/654773ca5a305afafa967307d103edd1/1b17e6713d844f4c8bae2cd560892041.jpg",
          articleUrl: "https://mp.weixin.qq.com/s/drfJGm0W043P6btPZjcLLg"
        },
        {
          articleTitle: "测试标题3",
          createTime: "2020-04-05 20:17:44",
          specialColumnId: 2,
          isDelete: 0,
          updateTime: "2020-04-08 20:17:49",
          aid: 3,
          articlePicUrl: "wffwe",
          articleUrl: "dcdf"
        },
        {
          articleTitle: "测试标题1",
          createTime: "2020-02-13 20:12:52",
          specialColumnId: 1,
          isDelete: 1,
          updateTime: "2020-04-05 20:12:56",
          aid: 1,
          articlePicUrl: "aaa",
          articleUrl: "aaa"
        },
        {
          articleTitle: "测试标题233",
          createTime: "2020-04-02 20:13:38",
          specialColumnId: 1,
          isDelete: 0,
          updateTime: "2020-04-05 20:17:27",
          aid: 2,
          articlePicUrl:
            "../pictures/b657b89f9cd5c2e4f1137eae76d2037b/654773ca5a305afafa967307d103edd1/1b17e6713d844f4c8bae2cd560892041.jpg",
          articleUrl: "https://mp.weixin.qq.com/s/drfJGm0W043P6btPZjcLLg"
        },
        {
          articleTitle: "测试标题3",
          createTime: "2020-04-05 20:17:44",
          specialColumnId: 2,
          isDelete: 0,
          updateTime: "2020-04-08 20:17:49",
          aid: 3,
          articlePicUrl: "wffwe",
          articleUrl: "dcdf"
        },
        {
          articleTitle: "测试标题1",
          createTime: "2020-02-13 20:12:52",
          specialColumnId: 1,
          isDelete: 1,
          updateTime: "2020-04-05 20:12:56",
          aid: 1,
          articlePicUrl: "aaa",
          articleUrl: "aaa"
        },
        {
          articleTitle: "测试标题233",
          createTime: "2020-04-02 20:13:38",
          specialColumnId: 1,
          isDelete: 0,
          updateTime: "2020-04-05 20:17:27",
          aid: 2,
          articlePicUrl:
            "../pictures/b657b89f9cd5c2e4f1137eae76d2037b/654773ca5a305afafa967307d103edd1/1b17e6713d844f4c8bae2cd560892041.jpg",
          articleUrl: "https://mp.weixin.qq.com/s/drfJGm0W043P6btPZjcLLg"
        },
        {
          articleTitle: "测试标题3",
          createTime: "2020-04-05 20:17:44",
          specialColumnId: 2,
          isDelete: 0,
          updateTime: "2020-04-08 20:17:49",
          aid: 3,
          articlePicUrl: "wffwe",
          articleUrl: "dcdf"
        },
        {
          articleTitle: "测试标题1",
          createTime: "2020-02-13 20:12:52",
          specialColumnId: 1,
          isDelete: 1,
          updateTime: "2020-04-05 20:12:56",
          aid: 1,
          articlePicUrl: "aaa",
          articleUrl: "aaa"
        },
        {
          articleTitle: "测试标题233",
          createTime: "2020-04-02 20:13:38",
          specialColumnId: 1,
          isDelete: 0,
          updateTime: "2020-04-05 20:17:27",
          aid: 2,
          articlePicUrl:
            "../pictures/b657b89f9cd5c2e4f1137eae76d2037b/654773ca5a305afafa967307d103edd1/1b17e6713d844f4c8bae2cd560892041.jpg",
          articleUrl: "https://mp.weixin.qq.com/s/drfJGm0W043P6btPZjcLLg"
        },
        {
          articleTitle: "测试标题3",
          createTime: "2020-04-05 20:17:44",
          specialColumnId: 2,
          isDelete: 0,
          updateTime: "2020-04-08 20:17:49",
          aid: 3,
          articlePicUrl: "wffwe",
          articleUrl: "dcdf"
        },
        {
          articleTitle: "测试标题1",
          createTime: "2020-02-13 20:12:52",
          specialColumnId: 1,
          isDelete: 1,
          updateTime: "2020-04-05 20:12:56",
          aid: 1,
          articlePicUrl: "aaa",
          articleUrl: "aaa"
        },
        {
          articleTitle: "测试标题233",
          createTime: "2020-04-02 20:13:38",
          specialColumnId: 1,
          isDelete: 0,
          updateTime: "2020-04-05 20:17:27",
          aid: 2,
          articlePicUrl:
            "../pictures/b657b89f9cd5c2e4f1137eae76d2037b/654773ca5a305afafa967307d103edd1/1b17e6713d844f4c8bae2cd560892041.jpg",
          articleUrl: "https://mp.weixin.qq.com/s/drfJGm0W043P6btPZjcLLg"
        },
        {
          articleTitle: "测试标题3",
          createTime: "2020-04-05 20:17:44",
          specialColumnId: 2,
          isDelete: 0,
          updateTime: "2020-04-08 20:17:49",
          aid: 3,
          articlePicUrl: "wffwe",
          articleUrl: "dcdf"
        }
      ],
      // 发送的数据
      addCategoryForm: {}
      // editCategoryForm: {}
    };
  },
  created() {
    let _this = this;
    /**
     * 记得改
     */
    // this.getCategory();
    // this.getArticleData();
    _this.categoryData.forEach(item => {
      let key = item.sid;
      let value = item.specialcName;
      _this.categoryDict[key] = value;
      item.isDelete === 0 ? (item.status = "正常") : (item.status = "已删除");
      item.specialColumnName = _this.categoryDict[item.specialColumnId];
    });
    _this.tableData.forEach(item => {
      item.isDelete === 0 ? (item.status = "正常") : (item.status = "已删除");
      item.specialColumnName = _this.categoryDict[item.specialColumnId];
    });
  },
  methods: {
    getCategory: function() {
      let _this = this;
      getInstance()
        .get("/backstage/specialColumn/getAll", {})
        .then(function(res) {
          if (res.data.code === 200) {
            console.log(res);
            _this.categoryData = res.data.data.specialColumns;
            //设置字典（对象）映射sid与specialcName
            _this.categoryData.forEach(item => {
              let key = item.sid;
              let value = item.specialcName;
              _this.categoryDict[key] = value;
              item.isDelete === 0
                ? (item.status = "正常")
                : (item.status = "已删除");
              item.specialColumnName = _this.categoryDict[item.specialColumnId];
            });
          } else {
            _this.$message({
              showClose: true,
              message: res.data.message,
              type: "error"
            });
          }
        })
        .catch(function(err) {
          console.log(err);
        });
    },
    getArticleData: function() {
      let _this = this;
      getInstance()
        .post("/backstage/specialColumnArticle/getAll", {})
        .then(res => {
          console.log(res);
          if (res.data.code === 200) {
            // 修改一下拿到的数据
            let resData = res.data.data.specialColumnArticles;
            resData.forEach(item => {
              item.isDelete === 0
                ? (item.status = "正常")
                : (item.status = "已删除");
              item.specialColumnName = _this.categoryDict[item.specialColumnId];
            });
            _this.tableData = resData;
          } else {
            _this.$message({
              showClose: true,
              message: res.data.message,
              type: "error"
            });
          }
        })
        .catch(function(err) {
          console.log(err);
        });
    },
    //对专栏进行相关操作
    //添加
    addCategory: function() {
      let _this = this;
      getInstance()
        .get("/backstage/specialColumn/add", { params: _this.addCategoryForm })
        .then(function(res) {
          if (res.data.code === 200) {
            console.log(res);
          } else {
            _this.$message({
              showClose: true,
              message: res.data.message,
              type: "error"
            });
          }
        })
        .catch(function(err) {
          console.log(err);
        });
    },
    //修改
    editCategory: function(editQueryData) {
      let _this = this;
      _this.editDialogVisible = true;
      getInstance()
        .get("/backstage/specialColumn/update", {
          params: editQueryData
        })
        .then(function(res) {
          if (res.data.code === 200) {
            _this.$message({
              showClose: true,
              message: "修改成功",
              type: "success"
            });
            _this.editDialogVisible = false;
          } else {
            console.log(res);
            _this.$message({
              showClose: true,
              message: res.data.message,
              type: "error"
            });
          }
        })
        .catch(function(err) {
          console.log(err);
          _this.$message({
            showClose: true,
            message: "请求出错，请稍后重试",
            type: "error"
          });
        });
    },
    //删除
    deleteCategory: function(req) {
      let _this = this;
      getInstance()
        .get("/backstage/specialColumn/delete", {
          params: req
        })
        .then(function(res) {
          if (res.data.code === 200) {
            _this.$message({
              showClose: true,
              message: "删除成功",
              type: "success"
            });
            _this.reload();
          } else {
            console.log(res);
            _this.$message({
              showClose: true,
              message: res.data.message,
              type: "error"
            });
          }
        })
        .catch(function(err) {
          console.log(err);
          _this.$message({
            showClose: true,
            message: "请求出错，请稍后重试",
            type: "error"
          });
        });
    }
  }
};
</script>

<style scoped>
.el-form-item {
  margin-bottom: 2px;
}
</style>