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
            courses: [],
            majors:[]
        }
    },
    props:[],
    template: indexMainTemplate,
    created:function(){
        this.getMajors();
        this.getCourses();
    },
    methods:{
        getMajors(){
            var _this =this;
            axios.get('getAllMajors')
                .then(function(response){
                    _this.majors = response.data.data.majors;
                })
                .catch(function(err){
                    console.log(err);
                });
        },
        getCourses:function(){
            var _this =this;
            axios.get('course/getAllCourse')
                .then(function(response){
                    _this.courses = response.data.data.courses;
                })
                .catch(function(err){
                    console.log(err);
                });
        },
        handleClose(tag) {
            this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
        },
        handleOpen(){}
    },

}

Vue.component("my_index_main",indexMainModule);