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
                                @open="handleOpen"
                                @close="handleClose">
                            <el-menu-item index="1">
                                <i class="el-icon-info"></i>
                                <span slot="title">个人信息</span>
                            </el-menu-item>
                            <el-menu-item index="2">
                                <i class="el-icon-message" style="order: 1;"></i>
                                <span slot="title" style="order: 2;">消息通知</span>
                                <el-badge :value="12" class="item" style="order:3;float:right">

                                </el-badge>
                            </el-menu-item>
                            <el-submenu index="3">
                                <template slot="title">
                                    <i class="el-icon-tickets"></i>
                                    <span>文件操作记录</span>
                                </template>
                                <el-menu-item index="3-1">文件上传记录</el-menu-item>
                                <el-menu-item index="3-2">文件下载记录</el-menu-item>
                            </el-submenu>
                            <el-submenu index="4">
                                <template slot="title">
                                    <i class="el-icon-star-on"></i>
                                    <span>学习记录</span>
                                </template>
                                <el-menu-item index="4-1">收藏的课程</el-menu-item>
                                <el-menu-item index="4-2">收藏的段落</el-menu-item>
                            </el-submenu>
                            <el-menu-item index="5">
                                <i class="el-icon-edit-outline"></i>
                                <span slot="title">个人笔记</span>
                            </el-menu-item>
                        </el-menu>
                    </el-col>
                </el-row>
            </el-col>

            <el-col :span="12" style="border: 1px solid #ebebeb;padding:20px;">
                <my_user_info_form v-if="module.user_info_see"></my_user_info_form>
                <my_message_list v-if="module.message_list_see"></my_message_list>
                <my_file_upload_record_list v-if="module.file_upload_record_list_see"></my_file_upload_record_list>
                <my_course v-if="module.course_list_see" v-for="course in courses" :course="course" :schedule="schedule"></my_course>
            </el-col>

        </el-row>
    </el-main>
`



var userMainModule = {
    data: function () {
        return {
            module:{
                user_info_see:true,
                message_list_see:false,
                file_upload_record_list_see:false ,
                course_list_see:false
            },
            courses:[],
            schedule:true
        }
    },
    props:[],
    template: userMainTemplate,
    created:function(){

    },
    methods:{

    }
}

Vue.component('my_user_main',userMainModule)

