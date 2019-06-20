/*
首页界面组件
（已更改为关于智库页面）
 */
// var indexMainTemplate = `
// <el-main>
//         <el-row type="flex" class="row-bg" justify="center">
//          <el-col :span="16">
//             <template>
//               <el-carousel :interval="4000" height="400px">
//                 <el-carousel-item v-for="item in 6" :key="item">
//                     <img src="img/IMG_4025.JPG" style="width: 100%;height: 100%; object-fit: cover;object-position: center;">
//                     <span>{{item}}</span>
//                 </el-carousel-item>
//               </el-carousel>
//             </template>
//            </el-col>
//         </el-row>
//         <el-row type="flex" class="row-bg" justify="center">
//             <!--专业目录展示部分-->
//             <!--<el-col :span="6">-->
//                 <!--<el-row type="flex" justify="center">-->
//                     <!--<el-col :span="18">-->
//                         <!--<h5>专业目录</h5>-->
//                         <!--<el-menu-->
//                                 <!--default-active="2"-->
//                                 <!--class="el-menu-vertical-demo">-->
//                             <!--<major v-for="major in majors" v-bind:major="major":index="major.majorId"></major>-->

//                         <!--</el-menu>-->
//                     <!--</el-col>-->
//                 <!--</el-row>-->
//             <!--</el-col>-->
//             <!--主要课程展示部分-->
//             <!--<el-col :span="15">-->
//                 <!--<el-row :gutter="20" >-->
//                     <!--<template v-for="course in courses" class="course-row">-->
//                         <!--&lt;!&ndash;<el-col style='margin: 20px 0px'>&ndash;&gt;-->
//                             <!--<my_course :course="course" :schedule="true"></my_course>-->
//                         <!--&lt;!&ndash;</el-col>&ndash;&gt;-->
//                     <!--</template>-->
//                 <!--</el-row>-->
//             <!--</el-col>-->
//         </el-row>
//     </el-main>
// `

var indexMainTemplate = `
<el-row type="flex" class="row-bg" justify="center">
<div>
    <div class="agile_banner">
        <div  class="header_div">
            <img class="headerPic" src="img/header.png">
        </div>
		
	</div>
	<div class="about" id="about">
		<div class="container">
			<h3>改造线上线下学习模式的学生组织</h3>
			<div class="col-md-6 w3ls_ar">
				<p>山软智库，创立于2017年1月1日</p>
				<p>致力于围绕知识分享提供各种产品和服务</p>
				<p>We take the best and spread
					it around so that everybody
					in this world could grow up with better things
				</p>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
	<!-- /About-->

	<!--部门介绍-->
	<el-row type="flex" class="row-bg" justify="center">
		<el-col :span="16">
			<template>
				<el-carousel :interval="4000" height="400px">
					<el-carousel-item v-for="(item,index) in departments" :key="index" style="background-color:#f9fafc">
						<img :src="item.imgPath"
							style="width: 100%;height: 100%; object-fit: scale-down;object-position: center;">
						<div class="showText">
							<h3 style="margin-top:15vh;">{{item.content1}}</h3>
							<h4 style="margin-top:3%;">{{item.content2}}</h4>
							<span style="margin-top:3%;">{{item.content3}}</span>
						</div>
					</el-carousel-item>
				</el-carousel>
			</template>
		</el-col>
	</el-row>

	<!-- footer -->
	<div class="contact_us" id="contact_us">
		<div class="container">
			<div class="f-bg-w3l">
                <h3>关注我们</h3>
				<div class="col-md-4  w3layouts_footer_grid">
					<img src="img/QRcode.jpg" width="262" height="262"></img>
				</div>
				<div class="col-md-4  w3layouts_footer_grid">
					<p>
                        关注公众号即可随时追踪我们的最新动态
                        你也可以在后台留言，来说一说你的意见和遇到的问题
                        你的意见和建议会帮助我们更好的成长！
                    </p>
                </div>
                
				<div class="clearfix"> </div>
			</div>
		</div>
	</div>
</div>
</el-row >
`

var indexMainModule = {
    data: function () {
        return {
            courses: [],
            majors: [],
            departments: [{
                imgPath: "img/知识部.jpg",
                content1: "内容部",
                content2: "设计与创作学习内容",
                content3: "不只是知识见解，线上线下全方位走入你的学习生活"
            }, {
                imgPath: "img/工程部.jpg",
                content1: "工程部",
                content2: "产品调研与开发",
                content3: "有什么比新的世界在手中构建更让人愉悦的事情呢？"
            }, {
                imgPath: "img/科研部.jpg",
                content1: "科研部",
                content2: "本科科研探索",
                content3: "专业知识图谱建模，学习行为数据挖掘，为智库的未来赋能。"
            }, {
                imgPath: "img/运营部.jpg",
                content1: "运营部",
                content2: "让价值传播",
                content3: "把我们的产品轻轻放进你的怀里，引导和记录你与智库的今世今生。"
            }],
        }
    },
    props: [],
    template: indexMainTemplate,
    created: function () {
        loadjscssfile("css/bootstrap.min.css","css");

    },
    destroyed(){
        removejscssfile("css/bootstrap.min.css","css")
    }
    // methods: {
    //     getMajors() {
    //         var _this = this;
    //         axios.get('getAllMajors')
    //             .then(function (response) {
    //                 _this.majors = response.data.data.majors;
    //             })
    //             .catch(function (err) {
    //                 console.log(err);
    //             });
    //     },
    //     getCourses: function () {
    //         var _this = this;
    //         axios.get('course/getAllCourse')
    //             .then(function (response) {
    //                 _this.courses = response.data.data.courses;
    //             })
    //             .catch(function (err) {
    //                 console.log(err);
    //             });
    //     },
    //     handleClose(tag) {
    //         this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
    //     },
    //     handleOpen() { }
    // },

}

Vue.component("my_index_main", indexMainModule);