/*
首页界面组件
 */
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
                                class="el-menu-vertical-demo">
                            <major v-for="major in majors" v-bind:major="major"></major>

                        </el-menu>
                    </el-col>
                </el-row>
            </el-col>
            <!--主要课程展示部分-->
            <el-col :span="14">
                <el-row :gutter="20">
                    <template v-for="course in courses">
                        <el-col :span="8" style='margin: 20px 0px'>
                            <my_course :course="course" :schedule="true"></my_course>
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
            courses: [
                {
                    "cid": 100,
                    "courseName": "java高级程序设计",
                    "courseDesc": "java语言基础",
                    "courseIcon": "./img/logo.jpg"
                },
                {
                    "cid": 101,
                    "courseName": "linux课程",
                    "courseDesc": "linux基础",
                    "courseIcon": "./img/logo.jpg"
                },
                {
                    "cid": 104,
                    "courseName": "测试md",
                    "courseDesc": "测试",
                    "courseIcon": "./img/logo.jpg"
                }
            ],
            majors:[
                {
                    id:1,
                    name:"软件工程"
                },
                {
                    id:2,
                    name:"数字媒体"
                },
                {
                    id:3,
                    name:"计算机科学"
                },
                {
                    id:4,
                    name:"马克思学院"
                },
                {
                    id:5,
                    name:"软件工程"
                },
                {
                    id:6,
                    name:"数字媒体"
                },
                {
                    id:7,
                    name:"计算机科学"
                },
                {
                    id:8,
                    name:"马克思学院"
                }
            ]
        }
    },
    props:[],
    template: indexMainTemplate,
    methods:{
        handleClose(tag) {
            this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
        },
        handleOpen(){}
    },

}

Vue.component("my_index_main",indexMainModule);