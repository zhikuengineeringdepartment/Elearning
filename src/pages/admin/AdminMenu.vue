<template>
  <el-menu class="tac">
    <el-col :span="24">
      <el-menu
        :default-active="$store.state.path"
        class="el-menu-vertical-demo"
      >
        <el-menu-item :index="$store.state.path">
          <template slot="title">
            <i class="el-icon-upload"></i>
            <span>上传</span>
          </template>
        </el-menu-item>
      </el-menu>
    </el-col>
  </el-menu>
</template>


<script>
export default {
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