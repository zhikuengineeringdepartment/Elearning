var indexMainTemplate = `
<el-main>
        <el-row type="flex" class="row-bg" justify="center">
            <!--专业目录展示部分-->
            <el-col :span="4">
                <el-row type="flex" justify="center">
                    <el-col :span="18">
                        <h5>专业目录</h5>
                        <el-menu
                                default-active="2"
                                class="el-menu-vertical-demo"
                                @open="handleOpen"
                                @close="handleClose">
                            <major v-for="(major,index) in majors" v-bind:major="major" key="index"></major>

                        </el-menu>
                    </el-col>
                </el-row>
            </el-col>
            <!--主要课程展示部分-->
            <el-col :span="14">
                <el-row :gutter="20">
                    <template v-for="(course,index) in courses">
                        <el-col :span="8" style='margin: 20px 0px'>
                            <course :course="course" key="index"></course>
                        </el-col>
                    </template>
                </el-row>
            </el-col>
        </el-row>
    </el-main>
`

var indexMainModule = {
    data:function () {
        return{

        }
    },
    props:["courses","majors"],
    template: indexMainTemplate,
    methods:{

    },

}

Vue.component("my_indexmain",indexMainModule);