/*
消息列表组件
 */
var colCourseListTemplate = `
<div>
    <my_course v-for="col_course in col_courses" :course="col_course.course" :schedule="false"></my_course>
</div>
`

var colCourseListModule = {
    data:function () {
        return{
            col_courses:[]
        }
    },
    props:[],
    template: colCourseListTemplate,
    created:function(){
        this.getColCourses();
    },
    methods:{
        getColCourses(){
            var _this = this;
            axios.get("user/getColCourses",{
                params:{
                    uid:0
                },
                withCredentials:true
            })
                .then(function(response){
                    _this.col_courses = response.data.data.colCourseView;
                    for(var i=0;i<_this.col_courses.length;i++){
                        _this.col_courses[i].course.completedSection = _this.col_courses[i].completedSection
                        _this.col_courses[i].course.totalSection = _this.col_courses[i].totalSection
                    }
                })
                .catch(function(err){
                    console.log(err);
                })
        }
    },

}

Vue.component("my_col_course_list",colCourseListModule);