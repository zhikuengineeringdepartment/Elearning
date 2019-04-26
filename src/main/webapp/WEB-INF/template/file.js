/*
文件浏览时的文件组件
 */
var fileTemplate = `
<el-card shadow="hover" style="margin: 10px 0px">
                    <el-row>
                        <el-col>
                            <el-row>
                                <el-col :span="8">{{jfile.fileName}}</el-col>
                                <el-col :span="8">{{jfile.upper}}上传于{{jfile.date}}</el-col>
                                <el-col :span="8">下载量{{jfile.downloadCount}}</el-col>
                            </el-row>
                        </el-col>
                        <el-col>
                            <el-row>
                                <el-col :span="12">
                                    <el-tag v-for="tag in file_tags" type="danger">{{tag.tagName}}</el-tag>
                                </el-col>
                                <el-col :span="12" style="display: flex;justify-content: center">
                                    <el-button type="primary" icon="el-icon-document" circle @click="handlePreview(jfile.fid)"></el-button>
                                    <el-button type="success" icon="el-icon-download" circle @click="handleDownload(jfile.fid)"></el-button>
                                </el-col>
                            </el-row>
                        </el-col>
                    </el-row>
                </el-card>
`

var fileModule = {
    data:function () {
        return{
        }
    },
    props:["jfile","file_tags"],
    template: fileTemplate,
    methods:{
        handlePreview:function (fid) {
            console.log("预览文件"+fid);
        },
        handleDownload:function(fid){
            console.log("下载文件"+fid);
            window.open('file/download?fid='+fid+'&uid='+root.user.uid)
        }
    },

}

Vue.component("my_file",fileModule);