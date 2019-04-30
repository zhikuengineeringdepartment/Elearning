/*
显示文件关键字的标签组件，课跳转
 */
var tagTemplate = `
<div>
<el-tag v-for="tag in file_tags" type="danger" style="margin: 0px 5px">
    <router-link to="/fileMain" style="color: #f56c6c;text-decoration: none;">{{tag.tagName}}</router-link>
</el-tag>
</div>
`

var tagModule = {
    data:function () {
        return{
        }
    },
    props:["file_tags"],
    template: tagTemplate,
    methods:{
    },

}

Vue.component("my_tag",tagModule);