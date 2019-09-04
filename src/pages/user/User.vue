<!--用户详情页-->
<template>
  <div class="user-body">
    <!--左边栏-->
    <el-col :span="4" class="user-left-selection">
      <el-menu>
        <el-menu-item @click="toUserInfo">
          <i class="el-icon-user"></i>
          <span v-if="!$store.state.isMobile" slot="title">用户信息</span>
        </el-menu-item>
        <el-menu-item @click="toMessage">
          <i class="el-icon-message"></i>
          <span v-if="!$store.state.isMobile" slot="title">消息记录</span>
        </el-menu-item>
        <el-menu-item @click="toUpload">
          <i class="el-icon-upload"></i>
          <span v-if="!$store.state.isMobile" slot="title">文件上传</span>
        </el-menu-item>
        <el-menu-item @click="toDownload">
          <i class="el-icon-download"></i>
          <span v-if="!$store.state.isMobile" slot="title">文件下载</span>
        </el-menu-item>
        <el-menu-item @click="toCollection">
          <i class="el-icon-star-on"></i>
          <span v-if="!$store.state.isMobile" slot="title">收藏记录</span>
        </el-menu-item>
      </el-menu>
    </el-col>
    <!--右边栏，是路由界面-->
    <el-col :span="20" class="user-right-router">
      <router-view/>
    </el-col>
  </div>
</template>

<script>
  import {routerChange} from "../../tools";

  export default {
    name: "user",
    methods: {
      toUserInfo: function (){
        routerChange("/user/info", this);
      },
      toMessage: function (){
        routerChange("/user/message", this);
      },
      toUpload: function (){
        routerChange("/user/upload", this);
      },
      toDownload: function (){
        routerChange("/user/download", this);
      },
      toCollection: function (){
        routerChange("/user/collection", this);
      },
    }
  }
</script>

<style lang="less" scoped>
  .user-body {
    padding: 3vmin;
    display: flex;
    flex-direction: row;
    
    .user-left-selection {
      margin: 0 auto;
      
      span {
        font-size: 2vmin;
      }
    }
    
    .user-right-router {
      padding: 20px;
    }
  }
</style>
