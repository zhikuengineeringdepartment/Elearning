/*
消息列表组件
 */
var colCourseListTemplate = `
<div>
    <my_course v-for="course in col_courses" :course="course" :schedule="false"></my_course>
</div>
`

var colCourseListModule = {
    data:function () {
        return{
            col_courses:[
                {
                    cid:1,
                    courseIcon:'img/logo.jpg',
                    courseName:'course1',
                    courseDesc:''
                }
            ]
        }
    },
    props:[],
    template: colCourseListTemplate,
    created:function(){
    },
    methods:{
    },

}

Vue.component("my_col_course_list",colCourseListModule);