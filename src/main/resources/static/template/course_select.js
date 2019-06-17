/*
文件模块的主页面组件
 */
var courseSelectTemplate = `
<el-select v-model="course_value" @change="get_value" filterable placeholder="请选择课程">
    <el-option
        v-for="item in courses"
        :key="item.cid"
        :label="item.courseName"
        :value="item.cid">
    </el-option>
</el-select>
`

var courseSelectModule = {
    data:function () {
        return{
            course_value:'',
            courses:[
                {
                    cid:100,
                    courseName:'course1'
                },
                {
                    cid:101,
                    courseName:'course2'
                }
            ]
        }
    },
    props:[],
    template: courseSelectTemplate,
    created:function(){
        this.getCourses();
    },
    methods:{
        get_value:function(){
            this.$emit('get-course-value', this.course_value)
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
        }
    },

}

Vue.component("my_course_select",courseSelectModule);