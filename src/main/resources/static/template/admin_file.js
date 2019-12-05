/*
管理员文件浏览时的文件组件
 */
//TODO 套用的普通用户的文件浏览组件，相关方法已经修改，但是相关的样式还未修改
var adminFileTemplate = `
<el-card shadow="hover" style="margin: 10px 0px">
                    <el-row>
                        <el-col>
                            <el-row>
                                <el-col :span="8">{{jfile.fileName}}</el-col>
                                <el-col :span="8">{{jfile.upper}}上传于{{jfile.fileUploadTime}}</el-col>
                                <el-col :span="8">下载量:{{jfile.fileDownloadCount}}</el-col>
                            </el-row>
                        </el-col>
                        <el-col>
                            <el-row>
                                <el-col :span="12">
                                    <my_tag :file_tags="jfile.fileKeys"></my_tag>
                                </el-col>
                                <el-col :span="12" style="display: flex;justify-content: center">
                                    <el-button type="primary" icon="el-icon-document" circle @click="handlePreview(jfile.fid)"></el-button>
                                    <el-button type="success" icon="el-icon-download" circle @click="modifyStatus(jfile.fid)"></el-button>
                                </el-col>
                            </el-row>
                        </el-col>
                    </el-row>
                </el-card>
`

var adminFileModule = {
    data:function () {
        return{
        }
    },
    props:["jfile"],    //模板参数:文件对象
    template: adminFileTemplate,
    methods:{
        handlePreview:function (fid) {
            console.log("预览文件"+fid);
            window.open("admin/preview?fid="+fid)
        },
        //修改文件状态
        modifyStatus:function (fid) {
            var _this = this;
            //TODO 目前修改的状态默认是修改为正常，后续应该添加选择各种状态
            axios.post('admin/modifyFileStatus',{
                fid:fid,
                status:"n"  //设置文件为正常
            },{
                transformRequest: [
                    function(data) {
                        let ret = '';
                        for (let it in data) {
                            ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&';
                        }
                        return ret;
                    }
                ],
                withCredentials:true
            })
                .then(function(res){
                    console.log(res);
                })
                .catch(function(err){
                    console.log(err);
                });
        }
    },

}

Vue.component("my_admin_file",adminFileModule);