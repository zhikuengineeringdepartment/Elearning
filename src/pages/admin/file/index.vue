<template>
    <el-row col="12">
        <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="待审核" name="review">
                <file-table
                        :buttonShow="[1, 1, 1, 1, 0, 0]"
                        :tableData="tableData"
                        :loading="loading"
                        :status="status"
                        @handlePreview="handlePreview"
                        @handleDownload="handleDownload"
                        @modifyFileStatus="modifyFileStatus"
                        @changePage="getFileList"
                ></file-table>
            </el-tab-pane>
            <el-tab-pane label="已通过" name="passed">
                <file-table
                        :buttonShow="[0, 0, 0, 0, 1, 0]"
                        :tableData="tableData"
                        :loading="loading"
                        :status="status"
                        @modifyFileStatus="modifyFileStatus"
                        @changePage="getFileList"
                ></file-table>
            </el-tab-pane>
            <el-tab-pane label="未通过" name="unpass">
                <file-table
                        :buttonShow="[0, 0, 0, 0, 1, 1]"
                        :tableData="tableData"
                        :loading="loading"
                        :status="status"
                        @modifyFileStatus="modifyFileStatus"
                        @changePage="getFileList"
                ></file-table>
            </el-tab-pane>
        </el-tabs>
        <!--        <pdf-preview v-if="isPreviewShow" @closePreview="closePreview" :isPreviewShow="isPreviewShow"-->
        <!--                     :PreviewSrc="previewSrc" :title="previewTitle"></pdf-preview>-->
    </el-row>
</template>

<script>
    import fileTable from './FileTable'
    import {updateFileStatus, queryFileList} from '../../../app/apis/adminApi'

    export default {
        components: {fileTable},
        data() {
            return {
                activeName: 'review',
                page: 1,
                status: 2,
                tableData: [],
                loading: true,
                // isPreviewShow: false,
                // previewSrc: "",
                // previewTitle: ""
            }
        },
        mounted() {
            this.getFileList(1, this.status)
        },
        methods: {
            handleClick(tab) {
                this.loading = true
                if (tab.name === 'review') {
                    this.getFileList(1, 2)
                    this.status = 2
                }

                if (tab.name === 'passed') {
                    this.getFileList(1, 1)
                    this.status = 1
                }

                if (tab.name === 'unpass') {
                    this.getFileList(1, 3)
                    this.status = 3
                }
            },
            handlePreview(fid, fileName) {
                if (/\.(?:pdf)$/.test(fileName)) {
                    console.log('预览文件' + fid)
                    this.$store.commit('setFid', fid)
                    const location = this.$fn.getLocation(window.location.href)
                    const host =
                        process.env.NODE_ENV === 'production'
                            ? location.protocol + '//' + location.host
                            : 'http://sharingideas.cn:10000'
                    window.open(host + "/admin/previewFile?fid=" + fid)
                } else {
                    this.$message.warning("目前只支持预览PDF文件哦~")
                }
            },
            handleDownload(fid) {
                if (this.$fn.isLogin()) {
                    const location = this.$fn.getLocation(window.location.href)
                    const host =
                        process.env.NODE_ENV === 'production'
                            ? location.protocol + '//' + location.host
                            : 'http://sharingideas.cn'
                    window.open(host + '/file/download?fid=' + fid + '&uid=' + 0)
                } else {
                    this.$fn.routerChange('/user/login', this)
                }
            },
            modifyFileStatus(fid, status) {
                console.log(fid, status)
                let params = {
                    fid: fid,
                    status: status
                }
                updateFileStatus(params, response => {
                    console.log(response)
                    if (response.code === 200) {
                        this.$message.success("修改成功")
                        this.loading = true
                        this.getFileList(this.page, this.status)
                    } else this.$message.error("修改失败")
                })
            },
            getFileList(page = 1, status = 1) {
                let params = {
                    keyWord: '',
                    fileCourse: '',
                    page: page,
                    order: true,
                    status: status
                }
                queryFileList(params, response => {
                    this.loading = false
                    this.tableData = response.data.files
                })
            }
        }
    }
</script>
