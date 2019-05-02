/*
文件浏览时的文件组件
 */
var fileTemplate = `
<el-card shadow="hover" style="margin: 10px 0px">
                    <el-row>
                        <el-col>
                            <el-row>
                                <el-col :span="8">{{jfile.fileName}}</el-col>
                                <el-col :span="8">{{jfile.upper}}上传于{{this.getFormatDate(jfile.fileUploadTime)}}</el-col>
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
    props:["jfile"],
    template: fileTemplate,
    methods:{
        handlePreview:function (fid) {
            console.log("预览文件"+fid);
            window.open("file/preview?fid="+fid)
        },
        handleDownload:function(fid){
            console.log("下载文件"+fid);
            window.open('file/download?fid='+fid+'&uid='+0)
        },
        getFormatDate:function(date) {
            if (date == undefined) {
                date = new Date();
            }else{
                date = new Date(date);
            }
            var pattern = "yyyy-MM-dd hh:mm:ss";

            var o = {
                "M+": date.getMonth() + 1,
                "d+": date.getDate(),
                "h+": date.getHours(),
                "m+": date.getMinutes(),
                "s+": date.getSeconds(),
                "q+": Math.floor((date.getMonth() + 3) / 3),
                "S":  date.getMilliseconds()
            };
            if (/(y+)/.test(pattern)) {
                pattern = pattern.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
            }

            for (var k in o) {
                if (new RegExp("(" + k + ")").test(pattern)) {
                    pattern = pattern.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
                }
            }

            return pattern;
                }

            },

}

Vue.component("my_file",fileModule);