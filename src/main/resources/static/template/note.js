/*
个人中心中的笔记组件
 */
var noteTemplate = `
<el-card shadow="hover" style="margin: 10px 0px">
    <span>笔记时间: {{note.noteDate}}</span>
    <div v-html="note.noteContent"></div>
    <my_col_paragraph :col_paragraph="note.colParagraphView" :index="index"></my_col_paragraph>
</el-card>
`

var noteModule = {
    data:function () {
        return{
            note_content:'content'+this.index,
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