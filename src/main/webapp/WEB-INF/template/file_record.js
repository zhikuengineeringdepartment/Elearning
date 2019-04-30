/*
文件上传下载记录组件
 */
var fileRecordTemplate = `
<el-card shadow="hover" style="margin: 10px 0px">
                    <el-row style="margin-bottom: 0px">
                        <el-col>
                            <h3 style="margin: 0;">{{file_record.name}}</h3>
                        </el-col>
                        <el-col>
                            <p style="margin: 0">{{file_record.desc}}</p>
                        </el-col>
                        <el-col style="margin: 10px 0px 20px">
                            <my_tag :file_tags="file_record.tags"></my_tag>
                            <span v-if="is_upload_page === 'true'" style="float: right">{{file_record.status}}</span>
                        </el-col>
                        <el-col>
                            <span v-if="is_upload_page === 'true'">
                                <label>上传时间：</label>
                                {{file_record.uploadDate}}
                            </span>
                            <span v-else>
                                <label>下载时间</label>
                                {{file_record.downloadDate}}
                            </span>
                            <el-button v-if="is_upload_page === 'true'" size="small" style="float: right">编辑</el-button>
                            <template v-else>
                                <template v-if="commented">
                                    <el-rate
                                        v-model="value"
                                        disabled
                                        show-score
                                        text-color="#ff9900"
                                        score-template="{value}">
                                    </el-rate>
                                </template>
                                <template v-else>
                                    <div>
                                        <span size="small" style="float: right" @click="commentFile">立即评价</span>
                                    <el-rate
                                        v-model="score"
                                        allow-half
                                        text-color="#ff9900">
                                    </el-rate>
                                </div>
                                </template>
                            </template>
                        </el-col>
                    </el-row>
                </el-card>
`

var fileRecordModule = {
    data:function () {
        return{
            commented:true,
            value:3.7,
            score:0
        }
    },
    props:["file_record","is_upload_page"],
    template: fileRecordTemplate,
    methods:{
        commentFile:function () {

        }
    },

}

Vue.component("my_file_record",fileRecordModule);