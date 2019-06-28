/*
文件上传记录组件
 */
//TODO 编辑按钮不可用，原计划是在用户上传文件待审核期间，可以修改添加的标签等，在文件正常之后该按钮失效
var fileUploadRecordTemplate = `
<el-card shadow="hover" style="margin: 10px 0px">
                    <el-row style="margin-bottom: 0px">
                        <el-col>
                            <h3 style="margin: 0;">{{file_upload_record.fileName}}</h3>
                        </el-col>
                        <el-col>
                            <p style="margin: 0">{{file_upload_record.fileDesc}}</p>
                        </el-col>
                        <el-col style="margin: 10px 0px 20px">
                            <my_tag :file_tags="file_upload_record.fileKeys"></my_tag>
                            <span style="float: right">{{file_upload_record.fileStatus}}</span>
                        </el-col>
                        <el-col>
                            <span>
                                <label>上传时间：</label>
                                {{file_upload_record.fileUploadTime}}
                            </span>
                            <el-button size="small" style="float: right">编辑</el-button>
                        </el-col>
                    </el-row>
                </el-card>
`

var fileUploadRecordModule = {
    data:function () {
        return{

        }
    },
    props:["file_upload_record"],
    template: fileUploadRecordTemplate,
    methods:{

    },

}

Vue.component("my_file_upload_record",fileUploadRecordModule);