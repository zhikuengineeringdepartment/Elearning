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