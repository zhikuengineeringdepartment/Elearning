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
                    <template v-if="isLogin">
                        <el-form ref="form" :model="loginForm" label-width="80px" style="display: flex;flex-direction: column;align-items: center">
                            <el-form-item label="账号">
                                <el-input
                                        placeholder="用户名||邮箱"
                                        prefix-icon="el-icon-info"
                                        v-model="loginForm.identity">
                                </el-input>
                            </el-form-item>
                            <el-form-item label="密码">
                                <el-input
                                        placeholder="密码"
                                        prefix-icon="el-icon-info"
                                        v-model="loginForm.password">
                                </el-input>
                            </el-form-item>
                            <el-button type="primary" round>登录</el-button>
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
                                        placeholder="密码"
                                        prefix-icon="el-icon-info"
                                        v-model="registeForm.password">
                                </el-input>
                            </el-form-item>
                            <el-button  type="primary" round>注册</el-button>
                        </el-form>
                    </template>
                </div>
            </el-col>
        </el-row>
    </el-main>
`

var loginRegisteModule = {
    data:function () {
        return{
            isLogin:'',
            loginForm:{
                identity:'',
                password:''
            },
            registeForm:{
                username:'',
                email:'',
                password:''
            }
        }
    },
    created:function () {
        this.setLogin();
    },
    props:[],
    template: loginRegisteTemplate,
    watch: {
        '$route' (to) {
            if(to.fullPath === '/login'){
                this.isLogin = true;
            }else{
                this.isLogin = false;
            }
        }
        },
    methods:{
        setLogin(){
            this.$router.history.current.path === '/login'? this.isLogin = true : this.isLogin = false;
        }
    },

}

Vue.component("my_login_registe",loginRegisteModule);