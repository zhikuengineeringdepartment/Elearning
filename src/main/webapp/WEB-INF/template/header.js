var headerTemplate = `
<el-header height="100px">
    <el-row type="flex" class="row-bg" justify="space-around">
        <el-col :span="12" style="display: flex;align-items: center;justify-content: center;">
            <img src="../img/logo.jpg" height="50px" width="auto" style="margin-right:20px">
            <i>让知识回归平凡</i>
        </el-col>
        <el-col :span="6"><el-input v-model="search" placeholder="搜索..."></el-input></el-col>
        <el-col :span="4">
            <el-button type="text">登录</el-button>
            <el-button type="text">注册</el-button>
        </el-col>
    </el-row>
    <el-row class="row-md">
        <el-col :span="20" :offset="3">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item><a href="/">文件资源</a></el-breadcrumb-item>
                <el-breadcrumb-item>练习测试</el-breadcrumb-item>
                <el-breadcrumb-item>活动详情</el-breadcrumb-item>
            </el-breadcrumb>
        </el-col>
    </el-row>
    </el-header>
`

var headerModule = {
    data:function () {
        return{

        }
    },
    props:[],
    template: headerTemplate,
    methods:{

    },

}

Vue.component("my_header",headerModule);