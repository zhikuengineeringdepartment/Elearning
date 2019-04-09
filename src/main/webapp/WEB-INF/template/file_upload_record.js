var fileUploadRecordTemplate = `
<el-card shadow="hover" style="margin: 10px 0px">
                    <el-row style="margin-bottom: 0px">
                        <el-col>
                            <h3 style="margin: 0;">{{_file.name}}</h3>
                        </el-col>
                        <el-col>
                            <p style="margin: 0">{{_file.desc}}</p>
                        </el-col>
                        <el-col style="margin: 10px 0px 20px">
                            <template v-for="tag in _file.tags">
                                <a><el-tag type="danger" size="small" style="margin: 0px 5px">{{tag.name}}</el-tag></a>
                            </template>
                            <span style="float: right">{{_file.status}}</span>
                        </el-col>
                        <el-col>
                            <span>
                                <label>上传时间：</label>
                                {{_file.uploadDate}}
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
    props:["_file"],
    template: fileUploadRecordTemplate,
    methods:{

    },

}

Vue.component("my_file_upload_record",fileUploadRecordModule);