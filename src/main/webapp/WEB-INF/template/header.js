/*
头部栏组件
 */
var headerTemplate = `
<el-header height="auto">
    <el-row type="flex" class="row-bg" justify="space-around">
        <el-col :span="12" style="display: flex;align-items: center;justify-content: center;">
            <router-link to="/"><img src="img/sharingIdea.jpg" height="50px" width="auto" style="margin-right:20px"></router-link>
            <i>让知识回归平凡</i>
        </el-col>
        <el-col :span="6"><el-input v-model="searchKey" placeholder="搜索..." @keyup.enter.native="handleSearch"></el-input></el-col>
        <el-col :span="4">
            <template v-if="login">
                <router-link to="/user/info"><img :src="user_icon" height="50px" width="auto" style="margin-right:20px"></router-link>
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
         <el-tabs :activeName ="this.$route.path" @tab-click="headerClick" >
            <el-tab-pane label="首页" name="/" ></el-tab-pane>
            <el-tab-pane label="在线教程" name="/courseMain" ></el-tab-pane>
            <el-tab-pane label="文件资源" name="/fileMain" ></el-tab-pane>
            <!--<el-tab-pane label="练习测试" name="/exercise"></el-tab-pane>-->
            <el-tab-pane label="活动详情" name="/activity"></el-tab-pane>
            <el-tab-pane label="个人中心" name="/user"></el-tab-pane>
          </el-tabs>
          </el-col>
     </el-row>
    </el-header>
`

var headerModule = {
    data:function () {
        return{
            searchKey:'',
        }
    },
    props:['login','user_icon'],
    template: headerTemplate,
    created(){
        this.keyupEnter()
    },

    methods:{
        headerClick(tab, event){
            console.log(tab.name !== '/user')
            console.log(tab.name == '/user')
            console.log(tab.name === '/user')
            console.log(this.login)
            if(tab.name !== '/user'){
                this.$router.push(tab.name);
            }else{
                if(this.login){
                    this.$router.push('/user/info');
                }else{
                    this.$router.push('/login');
                }
            }

        },
        keyupEnter(){
            document.onkeydown = e =>{
                let body = document.getElementsByTagName('body')[0]
                if (e.keyCode === 13 && e.target.baseURI.match(/inputbook/) && e.target === body) {
                    console.log('enter')
                    this.handleSearch()
                }
            }
        },
        handleSearch:function(){
           console.log(this.searchKey)
        },
        toLoginRegiste:function(to){
            root.$router.push(to);
        }
    },

}

Vue.component("my_header",headerModule);