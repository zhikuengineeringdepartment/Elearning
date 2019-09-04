<template>
  <el-main class="user-register">
    <el-row type="flex" justify="center">
      <el-col class="user-register-form">
        <el-input
          class="user-register-form-item"
          placeholder="用户名"
          prefix-icon="el-icon-info"
          v-model="registerForm.username">
        </el-input>
        <el-input
          class="user-register-form-item"
          placeholder="邮箱"
          prefix-icon="el-icon-info"
          v-model="registerForm.email">
        </el-input>
        <el-input
          class="user-register-form-item"
          type="password"
          placeholder="密码"
          prefix-icon="el-icon-info"
          v-model="registerForm.password">
        </el-input>
        <el-input
          class="user-register-form-item"
          type="password"
          placeholder="请确认密码"
          prefix-icon="el-icon-info"
          v-model="registerForm.password2">
        </el-input>
        <el-button class="user-register-form-button" type="primary" @click="userRegister">注册</el-button>
      </el-col>
    </el-row>
    <el-row type="flex" justify="center">
      <el-col class="router-jump">
        <div @click="goLogin"> 已有账号？点此登录</div>
      </el-col>
    </el-row>
  </el-main>
</template>

<script>
  import {routerChange} from "../../tools";
  
  export default {
    name: "UserRegister",
    data() {
      return {
        registerForm: {
          username: '',
          email: '',
          password: '',
          password2: ''
        }
      }
    },
    methods: {
      // 用户注册
      userRegister: function () {
        const _this = this;
  
        // 在这里发起请求
        if (!_this.registerForm.password || !_this.registerForm.password2 || _this.registerForm.password !== _this.registerForm.password2) {
          _this.$message({showClose: true, message: '密码不一致或为空', type: 'error'});
        } else {
          _this.$http.post('/user/registe', _this.registerForm).then(
            function (response) {
              if (response.data.code === 200) {
                _this.$message({showClose: true, message: '注册请求已收到，请进行邮箱验证', type: 'success'});
              } else {
                _this.$message({showClose: true, message: response.data.message, type: 'error'});
              }
            }
          );
        }
      },
      // 前往登录页面
      goLogin() {
        routerChange('/user/login', this);
      }
    }
  }
</script>

<style lang="less" scoped>
  .user-register {
    padding: 10vmin 0 0 0;
    min-height: calc(100vh - 69vmin);
    
    .user-register-form {
      width: 80vw;
      border: 2px solid #ebebeb;
      
      .user-register-form-item {
        margin: 5vmin 10vw 0 10vw;
        width: 60vw;
      }
  
      .user-register-form-button {
        margin: 5vmin 10vw;
        font-size: 2vmin;
      }
    }
    
    .router-jump {
      margin: 3vmin;
      text-decoration: underline;
      color: #333333;
    }
  }
</style>
