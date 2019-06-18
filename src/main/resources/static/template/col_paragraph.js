/*
文件上传下载记录组件
 */
var colParagraphTemplate = `
<el-card shadow="hover" style="margin: 10px 0px">
    <div>
        <h3>{{col_paragraph.sectionName}}</h3>
        <div :id="col_paragraph_content" class="col_div"></div>
        <div>
            <router-link :to="toAddress"><span style="margin-right: 10px">{{col_paragraph.courseName}}</span></router-link>
            <span style="margin-right: 10px">{{col_paragraph.colCount}}</span>
            <span style="margin-right: 10px">取消收藏</span>
        </div>
    </div>
</el-card>
`

var colParagraphModule = {
    data:function () {
        return{
            toAddress:"/course/details/"+this.col_paragraph.cid+"/"+Math.floor(this.col_paragraph.paragraphSeq/100000),
            col_paragraph_content:'col_paragraph_content'+this.index
        }
    },
    props:["col_paragraph","index"],
    template: colParagraphTemplate,
    mounted:function(){
        this.load_content();
    },
    methods:{
        load_content:function () {
            document.getElementById(this.col_paragraph_content).innerHTML = marked(this.col_paragraph.paragraphContent);
        }
    },

}

Vue.component("my_col_paragraph",colParagraphModule);