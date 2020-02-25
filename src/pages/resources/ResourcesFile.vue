<!--文件资源列表项-->
<template>
    <el-card shadow="hover" class="resources-file-item">
        <el-row>
            <el-col>
                <el-row>
                    <!--手机端不显示上传时间-->
                    <div v-if="!$store.state.isMobile">
                        <el-col :span="8">{{fileItem.fileName}}</el-col>
                        <el-col :span="8">{{fileItem.upper}}上传于{{fileItem.fileUploadTime}}</el-col>
                        <el-col :span="8">下载量:{{fileItem.fileDownloadCount}}</el-col>
                    </div>
                    <div v-else>
                        <el-col :span="16">{{fileItem.fileName}}</el-col>
                        <el-col :span="8">下载量:{{fileItem.fileDownloadCount}}</el-col>
                    </div>
                </el-row>
            </el-col>
            <el-col>
                <el-row class="resources-file-footer">
                    <!--手机端不显示tag-->
                    <div v-if="!$store.state.isMobile">
                        <el-col :span="16">
                            <file-tag :tags="fileItem.fileKeys"></file-tag>
                        </el-col>
                        <el-col :span="8" class="resources-file-footer-button">
                            <el-button
                                    type="primary"
                                    icon="el-icon-document"
                                    circle
                                    @click="handlePreview(fileItem.fid,fileItem.fileName)"
                            ></el-button>
                            <el-button
                                    type="success"
                                    icon="el-icon-download"
                                    circle
                                    @click="handleDownload(fileItem.fid)"
                            ></el-button>
                        </el-col>
                    </div>
                    <div v-else>
                        <el-col :span="24" class="resources-file-footer-button">
                            <el-button
                                    type="primary"
                                    icon="el-icon-document"
                                    circle
                                    @click="handlePreview(fileItem.fid)"
                            ></el-button>
                            <el-button
                                    type="success"
                                    icon="el-icon-download"
                                    circle
                                    @click="handleDownload(fileItem.fid)"
                            ></el-button>
                        </el-col>
                    </div>
                </el-row>
            </el-col>
        </el-row>
        <pdf-preview v-if="isPreviewShow" @closePreview="closePreview" :isPreviewShow="isPreviewShow"
                     :PreviewSrc="previewSrc" :title="previewTitle"></pdf-preview>
    </el-card>
</template>

<script>
    import FileTag from '../../components/FileTag'
    import PdfPreview from "../../components/PdfPreview";

    export default {
        name: 'ResourcesFile',
        components: {FileTag, PdfPreview},
        props: ['fileItem'],
        data() {
            return {
                isPreviewShow: false,
                previewSrc: "",
                previewTitle: ""
            }
        },
        methods: {
            handlePreview(fid, fileName) {
                if (/\.(?:pdf)$/.test(fileName)) {
                    console.log('预览文件' + fid)
                    this.$store.commit('setFid', fid)
                    const location = this.$fn.getLocation(window.location.href)
                    const host =
                        process.env.NODE_ENV === 'production'
                            ? location.protocol + '//' + location.host
                            : 'http://sharingideas.cn'
                    this.previewSrc = host + '/file/preview?fid=' + fid
                    this.previewTitle = fileName + "（只能预览前十页）"
                    this.isPreviewShow = true
                } else {
                    this.$message.warning("目前只支持预览PDF文件哦~")
                }
            },
            closePreview() {
                this.isPreviewShow = false
            },
            handleDownload: function (fid) {
                if (this.$fn.isLogin()) {
                    console.log('下载文件')
                    const location = this.$fn.getLocation(window.location.href)
                    const host =
                        process.env.NODE_ENV === 'production'
                            ? location.protocol + '//' + location.host
                            : 'http://sharingideas.cn'
                    window.open(host + '/file/download?fid=' + fid + '&uid=' + 0)
                } else {
                    this.$fn.routerChange('/user/login', this)
                }
            }
        }
    }
</script>

<style lang="less" scoped>
    .resources-file-item {
        margin: 2vmin 0;
        font-size: 2vmin;

        .resources-file-footer {
            margin: 5vmin 0 0 0;

            .resources-file-footer-button {
                display: flex;
                justify-content: center;
            }
        }
    }
</style>
