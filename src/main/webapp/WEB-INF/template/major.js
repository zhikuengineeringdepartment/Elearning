let majorTemplate = `
<el-menu-item index="major.id">\
    <i class="el-icon-arrow-right"></i>
    <span slot="title">{{major.name}}</span>
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