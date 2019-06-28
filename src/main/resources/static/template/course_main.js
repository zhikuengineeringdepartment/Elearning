/*
课程模块组件
 */
var courseMainTemplate = `
<el-main>
    <el-row type="flex" class="row-bg" justify="center">
        <el-col :span="15">
            <el-row :gutter="20" >
                <template v-for="course in courses" class="course-row">
                    <!--<el-col style='margin: 20px 0px'>-->
                        <my_course :course="course" :schedule="true"></my_course>
                    <!--</el-col>-->
                </template>
            </el-row>
        </el-col>
    </el-row>
</el-main>
`

var courseMainModule = {
    data:function () {
        return{
            courses: []     //课程列表
        }
    },
    props:[],
    template: courseMainTemplate,
    created(){
        this.getCourses();
    },
    methods:{
        //获得所有课程
        getCourses:function(){
            var _this =this;
            axios.get('course/getAllCourse')
                .then(function(response){
                    _this.courses = response.data.data.courses;
                })
                .catch(function(err){
                    console.log(err);
                });
        }
    },

}

Vue.component("my_course_main",courseMainModule);