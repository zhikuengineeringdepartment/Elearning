/*
登录界面组件
 */
var loginRegisteTemplate = `
<el-main>
        <el-row type="flex" justify="center">
            <el-col :span="12" style="border: 1px solid #ebebeb;padding:20px;">
                <div style="display: flex;flex-direction: column;align-items: center">
                    <div style="margin: 20px;">
                        <router-link to="/login">登录</router-link>
                        <b>.</b>
                        <router-link to="/registe">注册</router-link>
                    </div>
                    <template v-if="isLoginPage">
                        <el-form ref="form" :model="loginForm" label-width="80px" style="display: flex;flex-direction: column;align-items: center">
                            <el-form-item label="账号">
                                <el-input
                                        placeholder="用户名||邮箱"
                                        prefix-icon="el-icon-info"
                                        v-model="loginForm.identity">
                                </el-input>
                            </el-form-item>
                            <el-form-item label="密码">
                                <el-input placeholder="请输入密码" v-model="loginForm.password" type="password"></el-input>
                            </el-form-item>
                            <el-button type="primary" round @click="handleLogin">登录</el-button>
                        </el-form>
                    </template>
                    <template v-else>
                        <el-form ref="form" :model="registeForm" label-width="80px"  style="display: flex;flex-direction: column;align-items: center">
                            <el-form-item label="用户名">
                                <el-input
                                        placeholder="用户名"
                                        prefix-icon="el-icon-info"
                                        v-model="registeForm.username">
                                </el-input>
                            </el-form-item>
                            <el-form-item label="邮箱">
                                <el-input
                                        placeholder="邮箱"
                                        prefix-icon="el-icon-info"
                                        v-model="registeForm.email">
                                </el-input>
                            </el-form-item>
                            <el-form-item label="密码">
                                <el-input
                                        type="password"
                                        placeholder="密码"
                                        prefix-icon="el-icon-info"
                                        v-model="registeForm.password">
                                </el-input>
                            </el-form-item>
                            <el-form-item label="确认密码">
                                <el-input
                                        type="password"
                                        placeholder="请确认密码"
                                        prefix-icon="el-icon-info"
                                        v-model="registeForm.password2"
                                        show-password>
                                </el-input>
                            </el-form-item>
                            <el-button  type="primary" round @click="handleRegiste">注册</el-button>
                        </el-form>
                    </template>
                </div>
            </el-col>
        </el-row>
    </el-main>
`

var loginRegisteModule = {
    data: function () {
        return {
            isLoginPage: '',
            loginForm: {
                identity: '',
                password: ''
            },
            registeForm: {
                username: '',
                email: '',
                password: '',
                password2: ''
            }
        }
    },
    created: function () {
        this.setLogin();
    },
    props: [],
    template: loginRegisteTemplate,
    watch: {
        '$route'(to) {
            if (to.fullPath === '/login') {
                this.isLoginPage = true;
            } else {
                this.isLoginPage = false;
            }
        }
    },
    methods: {
        setLogin() {
            console.log(this.$router)
            if (this.$router.history.current.path === '/login') {
                this.isLoginPage = true;
            } else {
                this.isLoginPage = false;
            }
        },

        // 处理登录请求
        handleLogin() {
            var _this = this;
            if (!this.registeForm.password || !this.registeForm.password2 || this.registeForm.password != this.registeForm.password2) {
                this.$message({
                    showClose: true,
                    message: '密码不一致或为空',
                    type: 'error'
                });
                return;
            }
            axios.post('/user/login',
                    this.loginForm, {
                        transformRequest: [
                            function (data) {
                                let ret = '';
                                for (let it in data) {
                                    ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&';
                                }
                                return ret;
                            }
                        ],
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        }
                    }
                )
                .then(function (response) {
                    if (response.data.code == 200) {
                        localStorage['user_icon'] = response.data.data.userIcon;
                        // root.user_icon = response.data.data.user_icon;
                        _this.$emit('login-state')
                        _this.$router.push("/");
                    } else {
                        alert(response.data.message)
                    }

                })
                .catch(function (err) {
                    console.log(err);
                });
        },
        // 处理注册请求
        handleRegiste() {
            var _this = this;
            axios.post('/user/registe', this.registeForm, {
                    transformRequest: [
                        function (data) {
                            let ret = '';
                            for (let it in data) {
                                ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&';
                            }
                            return ret;
                        }
                    ],
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                })
                .then(function (res) {
                    if (res.data.code == 400) {
                        alert(res.data.message)
                    } else if (res.data.code == 200) {
                        alert("请激活邮箱后登录")
                        _this.$router.push("/login");
                    }
                })
                .catch(function (err) {
                    console.log(err);
                });
        }
    },

}

Vue.component("my_login_registe", loginRegisteModule);