/*
文件上传下载记录组件
 */
var noteTemplate = `
<el-card shadow="hover" style="margin: 10px 0px">
    <span>笔记时间: {{note.noteDate}}</span>
    <div v-html="note.noteContent"></div>
    <my_col_paragraph :col_paragraph="col_paragraph" :index="index"></my_col_paragraph>
</el-card>
`

var noteModule = {
    data:function () {
        return{
            note_content:'content'+this.index,
            col_paragraph:{
                sectionName:"第一节",
                courseName:"测试md",
                paragraphContent:"图片的描述”功能暂未计划开发，请用“#”代替，如1.1.2.结构框图中图片。图片要**单独**一段。",
                colCount:100
            }
        }
    },
    props:['note','index'],
    template: noteTemplate,
    mounted:function(){
    },
    methods:{

    },

}

Vue.component("my_note",noteModule);