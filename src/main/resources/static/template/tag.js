/*
显示文件关键字的标签组件，课跳转
 */
var tagTemplate = `
<div>
<template v-for="(tag,name,index) in file_tags">
    <template v-if="name != 'fid' && tag != null">
        <el-tag  type="danger" style="margin: 0px 5px">
            {{tag}}
        </el-tag>
    </template>
</template>
</div>
`
//TODO 缺少点击标签可以跳转的功能
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