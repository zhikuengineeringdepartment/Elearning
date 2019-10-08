<template>
  <el-main class="user-login">
    <el-row type="flex" justify="center">
      <el-col class="user-login-form">
        <el-input
          class="user-login-form-item"
          placeholder="用户名或邮箱"
          prefix-icon="el-icon-info"
          v-model="loginForm.identity"
        ></el-input>
        <el-input
          class="user-login-form-item"
          placeholder="请输入密码"
          prefix-icon="el-icon-info"
          v-model="loginForm.password"
          type="password"
        ></el-input>
        <el-button class="user-login-form-button" type="primary" @click="userLogin">登录</el-button>
      </el-col>
    </el-row>
    <el-row type="flex" justify="center">
      <el-col class="router-jump">
        <div @click="goRegister">没有账号？点此注册</div>
      </el-col>
    </el-row>
  </el-main>
</template>

<script>
import { routerChange } from "../../tools";

export default {
  name: "UserLogin",
  data() {
    return {
      loginForm: {
        identity: "",
        password: ""
      }
    };
  },
  methods: {
    // 处理登录请求
    userLogin: function() {
      const _this = this;

      // 在这里发起请求
      _this.$http.post("/user/login", this.loginForm).then(function(response) {
        if (response.data.code === 200) {
          console.log(response.data);
          _this.$store.commit("changeLoginStatus", true);
          _this.$store.commit("setUser", {
            username: _this.loginForm.identity,
            userIcon: response.data.data.userIcon
          });
          localStorage["userIcon"] = response.data.data.userIcon;
          routerChange("/user/info", _this);
        } else {
          _this.$message({
            showClose: true,
            message: response.data.message,
            type: "error"
          });
        }
      });
    },
    // 路由跳转到注册界面
    goRegister: function() {
      routerChange("/user/register", this);
    }
  }
};
</script>

<style lang="less" scoped>
.user-login {
  padding: 10vmin 0 0 0;
  min-height: calc(100vh - 69vmin);

  @media screen and (max-width: 1000px) {
    .user-login-form {
      width: 80vw;
      border: 2px solid #ebebeb;

      .user-login-form-item {
        margin: 5vmin 10vw 0 10vw;
        width: 60vw;
      }

      .user-login-form-button {
        margin: 5vmin 10vw;
        font-size: 2vmin;
      }
    }
  }
  @media screen and (min-width: 1001px) {
    .user-login-form {
      width: 40vw;
      border: 2px solid #ebebeb;

      .user-login-form-item {
        margin: 5vmin 10vw 0 10vw;
        width: 20vw;
      }

      .user-login-form-button {
        margin: 5vmin 10vw;
        font-size: 2vmin;
      }
    }
  }

  .router-jump {
    margin: 3vmin;
    text-decoration: underline;
    color: #333333;
  }
}
</style>
