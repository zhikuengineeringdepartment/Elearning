<template>
    <el-main class="user-login">
        <el-row type="flex" justify="center">
            <el-col :xs="18" :sm="12" :md="10" :lg="8" :xl="6" class="user-login-form">
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
            <el-col :xs="8" :sm="6" :md="3" :lg="3" :xl="3" class="router-jump">
                <div @click="goRegister">没有账号？点此注册</div>
            </el-col>
            <el-col :xs="8" :sm="6" :md="3" :lg="3" :xl="3" class="router-jump">
                <div @click="goChangePwd">忘记密码？点此修改</div>
            </el-col>
        </el-row>
    </el-main>
</template>

<script>
    import {login} from "../../app/apis/userApi";

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
            userLogin: function () {
                login(this.loginForm, response => {
                    this.$store.commit("setUser", {
                        username: this.loginForm.identity,
                        userIcon: response.data.userIcon
                    });
                    localStorage["userIcon"] = response.data.userIcon;
                    this.$fn.routerChange("/user/info", this);
                })
            },
            // 路由跳转到注册界面
            goRegister: function () {
                this.$fn.routerChange("/user/register", this);
            },
            goChangePwd: function () {
                this.$fn.routerChange("/user/change_password", this)
            }
        }
    };
</script>

<style lang="less" scoped>
    .user-login {
        padding: 10vmin 0 0 0;
        min-height: calc(100vh - 69vmin);

        .user-login-form {
            /*width: 80vw;*/
            border: 2px solid #ebebeb;

            .user-login-form-item {
                margin: 8% 10% 0 10%;
                width: 60%;
            }

            .user-login-form-button {
                margin: 5vmin 10vw;
                font-size: 2vmin;
            }
        }

        .router-jump {
            margin: 3vmin;
            text-decoration: underline;
            color: #333333;
        }

        .router-jump:hover {
            cursor: pointer;
        }
    }
</style>
