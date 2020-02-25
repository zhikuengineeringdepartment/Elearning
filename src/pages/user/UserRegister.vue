<template>
    <el-main class="user-register">
        <el-row type="flex" justify="center">
            <el-col class="user-register-form" :xs="18" :sm="12" :md="10" :lg="8" :xl="6">
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
    import {register} from "../../app/apis/userApi";

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
                if (this.isForm()) {
                    this.$message.error("邮箱格式不正确/密码不一致/未填写完整");
                } else {
                    register(this.registerForm, response => {
                        this.$message({showClose: true, message: '注册请求已收到，请进行邮箱验证', type: 'success'});
                        this.goLogin()
                    })
                }
            },
            isForm() {
                return !this.registerForm.password || !this.registerForm.password2
                    || this.registerForm.password !== this.registerForm.password2
                    || !this.isEmail(this.registerForm.email)
                    || !this.registerForm.username
            },
            isEmail(email) {
                let reg = /^\w+@[a-zA-Z0-9]{2,10}(?:\.[a-z]{2,4}){1,3}$/;
                return reg.test(email);
            },
            // 前往登录页面
            goLogin() {
                this.$fn.routerChange('/user/login', this);
            }
        }
    }
</script>

<style lang="less" scoped>
    .user-register {
        padding: 10vmin 0 0 0;
        min-height: calc(100vh - 69vmin);

        .user-register-form {
            /*width: 80vw;*/
            border: 2px solid #ebebeb;

            .user-register-form-item {
                margin: 5% 10% 0 10%;
                width: 60%;
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

        .router-jump:hover {
            cursor: pointer;
        }
    }
</style>
