let majorTemplate = `
<el-menu-item index="major.majorId">\
    <i class="el-icon-arrow-right"></i>
    <span slot="title">{{major.majorName}}</span>
</el-menu-item>
`

var majorModule = {
    data:function () {
        return{

        }
    },
    props:['major'],
    template: majorTemplate,
    methods:{

    },

}

Vue.component("major",majorModule);