<template>
    <el-main class="user-change">
        <el-row type="flex" justify="center">
            <el-col class="user-change-form" :xs="18" :sm="12" :md="10" :lg="8" :xl="6">
                <el-input
                        class="user-change-form-item"
                        placeholder="邮箱"
                        prefix-icon="el-icon-info"
                        v-model="changeForm.email">
                </el-input>
                <el-input
                        class="user-change-form-item"
                        placeholder="验证码"
                        prefix-icon="el-icon-info"
                        v-model="changeForm.verificationCode">
                    <el-button slot="append" type="primary" size="mini" @click="getVerificationCode"
                               :disabled="isDisable">{{buttonText}}
                    </el-button>
                </el-input>
                <el-input
                        class="user-change-form-item"
                        type="password"
                        placeholder="新密码"
                        prefix-icon="el-icon-info"
                        v-model="changeForm.password">
                </el-input>
                <el-input
                        class="user-change-form-item"
                        type="password"
                        placeholder="请确认新密码"
                        prefix-icon="el-icon-info"
                        v-model="changeForm.password2">
                </el-input>
                <el-button class="user-change-form-button" type="primary" @click="change">确认修改</el-button>
            </el-col>
        </el-row>
        <el-row type="flex" justify="center">
            <el-col class="router-jump">
                <div @click="goLogin">返回登录</div>
            </el-col>
        </el-row>
    </el-main>
</template>

<script>
    import {sendCode, setPassword} from "../../app/apis/userApi";

    export default {
        name: "UserChangePassword",
        data() {
            return {
                isDisable: false,
                buttonText: "获取验证码",
                seconds: 60,
                changeForm: {
                    email: '',
                    verificationCode: '',
                    password: '',
                    password2: ''
                }
            }
        },
        methods: {
            change: function () {
                if (this.isForm()) {
                    this.$message.error("邮箱格式不正确/密码不一致/未填写完整");
                } else {
                    let params = {
                        code: this.changeForm.verificationCode,
                        password: this.changeForm.password,
                        email: this.changeForm.email
                    }
                    setPassword(params, response => {
                        this.$message.success("密码修改成功！")
                        this.$fn.routerChange("user/login", this)
                    })
                }
            },
            isForm() {
                return !this.changeForm.password || !this.changeForm.password2 ||
                    this.changeForm.password !== this.changeForm.password2 ||
                    !this.changeForm.email || !this.changeForm.verificationCode
                    || !this.isEmail(this.changeForm.email)
            },
            getVerificationCode() {
                console.log("获取验证码")
                if (this.isEmail(this.changeForm.email)) {
                    this.setButtonText()
                    sendCode({email: this.changeForm.email}, response => this.$message.success("验证码已发送"))
                } else {
                    this.$message.error("邮箱格式不正确")
                }
            },
            setButtonText() {
                this.isDisable = true
                let timer = setInterval(() => {
                    this.seconds--
                    this.buttonText = this.seconds + "秒后重发"
                }, 1000)
                setTimeout(() => {
                    this.isDisable = false
                    clearInterval(timer)
                    this.seconds = 60
                    this.buttonText = "获取验证码"
                }, 60000)
            },
            isEmail(email) {
                let reg = /^\w+@[a-zA-Z0-9]{2,10}(?:\.[a-z]{2,4}){1,3}$/;
                return reg.test(email);
            },
            goLogin() {
                this.$fn.routerChange('/user/login', this);
            }
        }
    }
</script>

<style lang="less" scoped>
    .user-change {
        padding: 10vmin 0 0 0;
        min-height: calc(100vh - 69vmin);

        .user-change-form {
            /*width: 80vw;*/
            border: 2px solid #ebebeb;

            .user-change-form-item {
                margin: 6% 10% 0 10%;
                width: 60%;
            }

            .user-change-form-button {
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
