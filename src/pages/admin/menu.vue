<template>
  <el-menu class="tac">
    <el-col :span="24">
      <el-menu :default-active="act" class="el-menu-vertical-demo">
        <router-link to="/admin/upload/image">
          <el-menu-item index="#/admin/upload/image">
            <template slot="title">
              <i class="el-icon-upload"></i>
              <span>上传图片</span>
            </template>
          </el-menu-item>
        </router-link>

        <router-link to="/admin/preview/knowledge">
          <el-menu-item index="#/admin/preview/knowledge">
            <template slot="title">
              <i class="el-icon-notebook-2"></i>
              <span>预览知识见解</span>
            </template>
          </el-menu-item>
        </router-link>

        <router-link to="/admin/upload/knowledge">
          <el-menu-item index="#/admin/upload/knowledge">
            <template slot="title">
              <i class="el-icon-upload"></i>
              <span>上传知识见解</span>
            </template>
          </el-menu-item>
        </router-link>

        <router-link to="/admin/weekly/diary/upload">
          <el-menu-item index="#/weekly/diary/upload">
            <template slot="title">
              <i class="el-icon-upload"></i>
              <span>智库周记上传</span>
            </template>
          </el-menu-item>
        </router-link>

        <router-link to="/admin/file/review">
          <el-menu-item index="/admin/file/review">
            <template slot="title">
              <i class="el-icon-files"></i>
              <span>文件审核</span>
            </template>
          </el-menu-item>
        </router-link>

        <router-link to="/admin/upload/article">
          <el-menu-item index="/admin/upload/article">
            <template slot="title">
              <i class="el-icon-document"></i>
              <span>专栏文章管理</span>
            </template>
          </el-menu-item>
        </router-link>

        <router-link to="/admin/dataAnalysis">
          <el-menu-item index="/admin/dataAnalysis">
            <template slot="title">
              <i class="el-icon-data-line"></i>
              <span>网站数据分析</span>
            </template>
          </el-menu-item>
        </router-link>

        <router-link to="/user/info">
          <el-menu-item index="#/user/info">
            <template slot="title">
              <i class="el-icon-back"></i>
              <span>退出</span>
            </template>
          </el-menu-item>
        </router-link>
      </el-menu>
    </el-col>
  </el-menu>
</template>


<script>
export default {
  data() {
    return {
      act: window.location.hash
    };
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    }
  }
};
</script>