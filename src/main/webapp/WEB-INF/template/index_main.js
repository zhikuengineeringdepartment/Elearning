/*
首页界面组件
 */
var indexMainTemplate = `
<el-main>
        <el-row type="flex" class="row-bg" justify="center">
         <el-col :span="16">
            <template>
              <el-carousel :interval="4000" height="400px">
                <el-carousel-item v-for="item in 6" :key="item">
                    <img src="img/IMG_4025.JPG" style="width: 100%;height: 100%; object-fit: cover;object-position: center;">
                    <span>{{item}}</span>
                </el-carousel-item>
              </el-carousel>
            </template>
           </el-col>
        </el-row>
        <el-row type="flex" class="row-bg" justify="center">
            <!--专业目录展示部分-->
            <!--<el-col :span="6">-->
                <!--<el-row type="flex" justify="center">-->
                    <!--<el-col :span="18">-->
                        <!--<h5>专业目录</h5>-->
                        <!--<el-menu-->
                                <!--default-active="2"-->
                                <!--class="el-menu-vertical-demo">-->
                            <!--<major v-for="major in majors" v-bind:major="major":index="major.majorId"></major>-->

                        <!--</el-menu>-->
                    <!--</el-col>-->
                <!--</el-row>-->
            <!--</el-col>-->
            <!--主要课程展示部分-->
            <!--<el-col :span="15">-->
                <!--<el-row :gutter="20" >-->
                    <!--<template v-for="course in courses" class="course-row">-->
                        <!--&lt;!&ndash;<el-col style='margin: 20px 0px'>&ndash;&gt;-->
                            <!--<my_course :course="course" :schedule="true"></my_course>-->
                        <!--&lt;!&ndash;</el-col>&ndash;&gt;-->
                    <!--</template>-->
                <!--</el-row>-->
            <!--</el-col>-->
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