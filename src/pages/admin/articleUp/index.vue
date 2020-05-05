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
            style="text-align:left;padding:10px;margin-top:2px; border-color: #E0E0E0;border-style:solid;border-width:thin"
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
        <el-collapse-item title="文章管理">
          <el-form
            :model="addArticleForm"
            label-position="left"
            label-width="80px"
            style="text-align:left;padding:10px;margin-top:2px; border-color: #E0E0E0;border-style:solid;border-width:thin"
          >
            <div style="text-align:left;padding-bottom:10px;font-weight:bold">上传文章：</div>
            <el-steps :active="addArticleStepActive" finish-status="success" align-center>
              <el-step title="上传文章图片"></el-step>
              <el-step title="上传其他信息"></el-step>
            </el-steps>
            <div v-if="addArticleStepActive===0">
              <el-form-item label="选择图片:">
                <el-upload
                  class="pic-uploader"
                  action="picture/upload"
                  :file-list="upPicList"
                  :auto-upload="false"
                  :limit="2"
                  :on-change="change_file_list"
                  :on-exceed="picNumOut"
                >
                  <span slot="tip" class="el-upload__tip">可上传jpg/png文件</span>
                  <el-button slot="trigger" size="small" type="primary">选取图片</el-button>
                </el-upload>
                <el-button size="small" type="primary" @click="picUpload">确定</el-button>
              </el-form-item>
            </div>
            <div v-if="addArticleStepActive===1">
              <el-form-item label="文章标题">
                <el-input v-model="addArticleForm.specialcName"></el-input>
              </el-form-item>
              <el-form-item label="文章所属">
                <el-select v-model="addArticleForm.specialColumnId" placeholder="请选择">
                  <el-option
                    v-for="item in categoryDataNotDelete"
                    :key="item.sid"
                    :label="item.specialcName"
                    :value="item.sid"
                  ></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="文章url">
                <el-input v-model="addArticleForm.speaiclcRemark"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button size="small" @click="stepBack" round type="info">返回上一步</el-button>
                <el-button type="primary" @click="addArticle">确认添加</el-button>
              </el-form-item>
            </div>
          </el-form>
        </el-collapse-item>
      </el-collapse>
    </el-col>
    <article-table :tableData="tableData" :categoryData="categoryData" @delete="deleteArticle"></article-table>
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
      //上传文章模块进度条
      addArticleStepActive: 0,
      //存放全部（已删+未删）专栏类型
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
          isDelete: 1,
          specialcRemark: "知识说",
          updateTime: "2020-04-05 17:21:05",
          sid: 2
        }
      ],
      //存放未删除的专栏类型
      categoryDataNotDelete: [],
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
      addCategoryForm: {},
      upPicList: [],
      addArticleForm: {}
      // editCategoryForm: {}
    };
  },
  created() {
    let _this = this;
    /**
     * 记得改
     */
    // this.getCategoryAll();
    // this.getArticleData();
    _this.categoryData.forEach(item => {
      let key = item.sid;
      let value = item.specialcName;
      _this.categoryDict[key] = value;
      item.isDelete === 0
        ? ((item.status = "正常"), _this.categoryDataNotDelete.push(item))
        : (item.status = "已删除");
      item.specialColumnName = _this.categoryDict[item.specialColumnId];
    });
    _this.tableData.forEach(item => {
      item.isDelete === 0 ? (item.status = "正常") : (item.status = "已删除");
      item.specialColumnName = _this.categoryDict[item.specialColumnId];
    });
  },
  methods: {
    picNumOut(files, fileList) {
      this.$message.warning(`一次只能上传一个文件`);
    },
    change_file_list(file, fileList) {
      this.upPicList = [file];
    },
    picUpload() {
      let _this = this;
      getInstance()
        .post("/picture/upload", { file: _this.upPicList[0] })
        .then(res => {
          console.log(res);
          if (res.data.code === 200) {
            _this.addArticleForm.articlePicUrl = res.data.data.url;
            if (++_this.addArticleStepActive > 1)
              _this.addArticleStepActive = 0;
          } else {
            _this.$message({
              showClose: true,
              message: "出错了，请重试",
              type: "error"
            });
          }
        })
        .catch(function(err) {
          console.log(err);
        });
    },
    stepBack() {
      if (--this.addArticleStepActive < 0) {
        this.addArticleStepActive = 0;
      }
    },
    addArticle() {
      let _this = this;
      if (
        _this.addArticleForm.articleTitle &&
        _this.addArticleForm.articleUrl &&
        _this.addArticleForm.articlePicUrl &&
        _this.addArticleForm.specialColumnId
      ) {
        let articleInfo = {
          articleTitle: _this.addArticleForm.articleTitle,
          articleUrl: _this.addArticleForm.articleUrl, //文章url
          articlePicUrl: _this.addArticleForm.articlePicUrl, //图片路径
          specialColumnId: _this.addArticleForm.specialColumnId //文章对应专栏类别
        };
        getInstance()
          .post("/backstage/specialColumnArticle/add", articleInfo)
          .then(res => {
            console.log(res);
            if (res.data.code === 200) {
              _this.$message({
                showClose: true,
                message: "上传成功",
                type: "success"
              });
              _this.addArticleStepActive = 0;
              //更新右侧的数据
              // _this.getArticleData();
            } else {
              _this.$message({
                showClose: true,
                message: "出错了，请重试",
                type: "error"
              });
            }
          })
          .catch(function(err) {
            console.log(err);
          });
      } else {
        _this.$message({
          showClose: true,
          message: "请将信息补充完整",
          type: "error"
        });
      }
    },
    deleteArticle: function(reqData) {
      let _this = this;
      console.log(reqData);
      getInstance()
        .post("/backstage/specialColumnArticle/delete", { id: reqData.id })
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
    },
    getCategoryAll: function() {
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
                ? ((item.status = "正常"),
                  _this.categoryDataNotDelete.push(item))
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
      if (
        !_this.addCategoryForm.specialcName ||
        !_this.addCategoryForm.speaiclcRemark ||
        _this.addCategoryForm.specialcName.trim() == "" ||
        _this.addCategoryForm.speaiclcRemark == ""
      ) {
        _this.$message({
          showClose: true,
          message: 无效输入,
          type: "error"
        });
      } else {
        getInstance()
          .get("/backstage/specialColumn/add", {
            params: _this.addCategoryForm
          })
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
      }
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
.el-steps {
  margin-bottom: 2em;
}
</style>