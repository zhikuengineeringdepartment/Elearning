/*
文件上传下载记录组件
 */
var noteTemplate = `
<el-card shadow="hover" style="margin: 10px 0px">
    <div></div>
</el-card>
`

var noteModule = {
    data:function () {
        return{
        }
    },
    props:['note'],
    template: noteTemplate,
    mounted:function(){
    },
    methods:{

    },

}

Vue.component("my_note",noteModule);