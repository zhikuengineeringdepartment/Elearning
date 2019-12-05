/*
头部栏组件
 */
//TODO 原先头部的全局搜索被隐藏了，此功能待开发
var headerTemplate = `
<el-header height="auto">
    <el-row type="flex" class="row-bg" justify="space-around">
        <el-col :span="12" style="display: flex;align-items: center;justify-content: center;">
            <router-link to="/"><img src="img/sharingIdea.jpg" height="50px" width="auto" style="margin-right:20px"></router-link>
            <i class="header-name">山软智库</i>
            <i class="header-slogan">让知识回归平凡</i>
        </el-col>
        <!-- <el-col :span="6"><el-input v-model="searchKey" placeholder="搜索..." @keyup.enter.native="handleSearch"></el-input></el-col> -->
        <el-col :span="4">
            <template v-if="login">
                <router-link to="/user/info"><img :src="user_icon" height="50px" width="auto" style="float:left;margin-right:10px;"></router-link>
                <el-button type="text" @click="loginOut" style="float:left;padding: 0;margin-top: 32px;">登出</el-button>
            </template>
            <template v-else>
                <el-button type="text" @click="toLoginRegiste('/login')">登录</el-button>
                <el-button type="text" @click="toLoginRegiste('/registe')">注册</el-button>
            </template>
        </el-col>
    </el-row>
    <!--<el-row class="row-md">-->
        <!--<el-col :span="20" :offset="3">-->
            <!--<el-breadcrumb separator="/">-->
                <!--<el-breadcrumb-item :to="{path:'/'}">首页</el-breadcrumb-item>-->
                <!--<el-breadcrumb-item :to="{path:'/fileMain'}">文件资源</el-breadcrumb-item>-->
                <!--<el-breadcrumb-item :to="{path:'/exercise'}">练习测试</el-breadcrumb-item>-->
                <!--<el-breadcrumb-item :to="{path:'/activity'}">活动详情</el-breadcrumb-item>-->
            <!--</el-breadcrumb>-->
        <!--</el-col>-->
    <!--</el-row>-->
    <el-row class="row-md">
    <el-col :span="20" :offset="3">
         <el-tabs :activeName="that.$route.path" @tab-click="headerClick" ref="aaa">
            <!--<el-tab-pane label="首页" name="/" ></el-tab-pane>-->
            <!--<el-tab-pane label="在线教程" name="/courseMain" ></el-tab-pane>-->
            <el-tab-pane label="知识见解" name="/" ></el-tab-pane>
            <el-tab-pane label="文件资源" name="/fileMain" ></el-tab-pane>
            <!--<el-tab-pane label="练习测试" name="/exercise"></el-tab-pane>-->
            <el-tab-pane label="智库专栏" name="/activity" ></el-tab-pane>
            <el-tab-pane label="关于智库" name="/aboutIdea" ></el-tab-pane>
            <el-tab-pane label="个人中心" :name="pname"></el-tab-pane>
          </el-tabs>
          </el-col>
     </el-row>
    </el-header>
`

var headerModule = {
    data: function () {
        return {
            searchKey: '',
            activeName: this.$route.path,
            that: this,
            pname: '/user/info'
        }
    },
    props: ['login', 'user_icon'],      //传入参数：是否登录，用户头像
    template: headerTemplate,
    created() {

        this.keyupEnter()
        if (this.$route.path.match(/\/user\/info/g)) {
            this.pname = window.location.href.match(/\/user\/info/g) ? window.location.hash.substr(1) : '/user/info';
        }
    },
    watch: {
        '$route'(to, from) {
            this.pname = window.location.href.match(/\/user\/info/g) ? window.location.hash.substr(1) : '/user/info';
        }
    },

    methods: {
        headerClick(tab, event) {
            if (tab.name !== '/user/info') {
                this.$router.push(tab.name);
            } else {
                if (this.login) {
                    this.$router.push('/user/info');
                } else {
                    this.$router.push('/login');
                }
            }

        },
        //为原搜索框提供的enter事件
        keyupEnter() {
            document.onkeydown = e => {
                let body = document.getElementsByTagName('body')[0]
                if (e.keyCode === 13 && e.target.baseURI.match(/inputbook/) && e.target === body) {
                    console.log('enter')
                    this.handleSearch()
                }
            }
        },
        handleSearch: function () {
            console.log(this.searchKey)
        },
        //跳转到登录注册界面
        toLoginRegiste: function (to) {
            root.$router.push(to);
        },
        //登出，现在的策略是删除前后的token，后台不做任何处理
        loginOut() {
            let _this = this;
            this.$confirm('是否退出登陆', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'info'
            }).then(() => {
                this.$message({
                    type: 'success',
                    message: '退出登陆成功!'
                });
                document.cookie = "token" + "=" + "";
                _this.$emit('change-login');
                _this.$router.push('/');
                console.log(_this.activeName);
                _this.activeName = "/login";
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '取消退出登陆'
                });
            });

        }
    },

}

Vue.component("my_header", headerModule);