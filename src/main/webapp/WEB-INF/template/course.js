let courseTemplate = `
<el-card :body-style="{ padding: '0px' }">
      <img :src="course.icon" class="image">
      <div style="padding: 14px;">
        <span>{{course.courseName}}</span>
        <div class="bottom clearfix">
          <time class="time">{{course.courseDesc}}</time>
          <el-button type="text" class="button">learn</el-button>
        </div>
      </div>
    </el-card>
`

var courseModule = {
    data:function () {
        return{

        }
    },
    props:['course'],
    template: courseTemplate,
    methods:{

    },

}

Vue.component("course",courseModule);