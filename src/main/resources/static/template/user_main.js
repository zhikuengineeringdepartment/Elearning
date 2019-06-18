let userMainTemplate = `
<el-main>
        <el-row type="flex" class="row-bg" justify="center">
            <!--专业目录展示部分-->
            <el-col :span="4">
                <el-row type="flex" justify="center">
                    <el-col :span="18">
                        <el-menu
                                default-active="2"
                                class="el-menu-vertical-demo"
                                router>
                            <el-menu-item index="1" :route="{path:'/user/info/infoform'}">
                                <i class="el-icon-info"></i>
                                <span slot="title">个人信息</span>
                            </el-menu-item>
                            <el-menu-item index="2" :route="{path:'/user/info/message_list'}">
                                <i class="el-icon-message" style="order: 1;"></i>
                                <span slot="title" style="order: 2;">消息通知</span>
                               
                            </el-menu-item>
                            <el-submenu index="3">
                                <template slot="title">
                                    <i class="el-icon-tickets"></i>
                                    <span>文件操作记录</span>
                                </template>
                                <el-menu-item index="3-1" :route="{path:'/user/info/file_upload_record/true'}">文件上传记录</el-menu-item>
                                <el-menu-item index="3-2" :route="{path:'/user/info/file_upload_record/false'}">文件下载记录</el-menu-item>
                            </el-submenu>
                            <el-submenu index="4">
                                <template slot="title">
                                    <i class="el-icon-star-on"></i>
                                    <span>学习记录</span>
                                </template>
                                <el-menu-item index="4-1" :route="{path:'/user/info/course_list'}">收藏的课程</el-menu-item>
                                <el-menu-item index="4-2" :route="{path:'/user/info/col_paragraph'}">收藏的段落</el-menu-item>
                            </el-submenu>
                            <el-menu-item index="5" :route="{path:'/user/info/note'}">
                                <i class="el-icon-edit-outline"></i>
                                <span slot="title">个人笔记</span>
                            </el-menu-item>
                        </el-menu>
                    </el-col>
                </el-row>
            </el-col>

            <el-col :span="12" style="border: 1px solid #ebebeb;padding:20px;">
                <router-view></router-view>
            </el-col>

        </el-row>
    </el-main>
`



var userMainModule = {
    data: function () {
        return {
            courses:[],
            schedule:true
        }
    },
    props:[],
    template: userMainTemplate,
    created:function(){

    },
    watch:{
        '$route' (to,from) {
            console.log(to)
        }
    },
    methods:{

    }
}

Vue.component('my_user_main',userMainModule)


