/*
文件上传下载记录组件
 */
var fileDownloadRecordTemplate = `
<el-card shadow="hover" style="margin: 10px 0px">
                    <el-row style="margin-bottom: 0px">
                        <el-col>
                            <h3 style="margin: 0;">{{file_download_record.fileView.fileName}}</h3>
                        </el-col>
                        <el-col>
                            <p style="margin: 0">{{file_download_record.fileView.fileDesc}}</p>
                        </el-col>
                        <el-col style="margin: 10px 0px 20px">
                            <my_tag :file_tags="file_download_record.fileView.fileKeys"></my_tag>
                        </el-col>
                        <el-col>
                            <span>
                                <label>下载时间</label>
                                {{file_download_record.fopDate}}
                            </span>
                            <!--<template v-if="commented">-->
                                <!--<el-rate-->
                                    <!--v-model="value"-->
                                    <!--disabled-->
                                    <!--show-score-->
                                    <!--text-color="#ff9900"-->
                                    <!--score-template="{value}">-->
                                <!--</el-rate>-->
                            <!--</template>-->
                            <!--<template v-else>-->
                                <!--<div>-->
                                    <!--<span size="small" style="float: right" @click="commentFile">立即评价</span>-->
                                <!--<el-rate-->
                                    <!--v-model="score"-->
                                    <!--allow-half-->
                                    <!--text-color="#ff9900">-->
                                <!--</el-rate>-->
                            <!--</div>-->
                            <!--</template>-->
                        </el-col>
                    </el-row>
                </el-card>
`

var fileDownloadRecordModule = {
    data:function () {
        return{
            commented:true,
            value:this.file_download_record.fileView.fileScore,
            score:0
        }
    },
    props:["file_download_record"],
    template: fileDownloadRecordTemplate,
    methods:{
        commentFile:function () {

        }
    },

}

Vue.component("my_file_download_record",fileDownloadRecordModule);