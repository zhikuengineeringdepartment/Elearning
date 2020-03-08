<!--用户-文件下载记录组件-->
<template>
    <div>
        <div v-if="fileDownloadRecords.length === 0">
            <div>没有下载的文件记录哦！</div>
        </div>
        <div v-if="fileDownloadRecords.length > 0">
            <el-card
                    v-for="(fileDownloadRecord, index) in fileDownloadRecords" shadow="hover" class="user-download"
                    :key="index">
                <el-row>
                    <el-col>
                        <h3>{{fileDownloadRecord.fileView.fileName}}</h3>
                    </el-col>
                    <el-col>
                        <span>下载时间：{{fileDownloadRecord.fopDate}}</span>
                    </el-col>
                    <el-col>
                        <p>{{fileDownloadRecord.fileView.fileDesc}}</p>
                    </el-col>
                    <el-col class="user-download-detail">
                        <file-tag
                                :tags="fileDownloadRecord.fileView.fileKeys"
                                status=""
                        />
                    </el-col>
                </el-row>
            </el-card>
            <pagination :pageSize="10" :totalNumber="totalNumber"
                        @handleCurrentChange="getDownloadRecords"></pagination>
            <p v-if="loading">加载中...</p>
        </div>
    </div>
</template>

<script>
    import FileTag from "../../../components/FileTag";
    import Pagination from "../../../components/Pagination"
    import {queryDownloadRecords} from "../../../app/apis/userApi";

    export default {
        name: "UserDownload",
        components: {FileTag, Pagination},
        data() {
            return {
                page: 1,
                fileDownloadRecords: [],
                commented: true,
                score: 0,
                loading: false,
                totalNumber: 10
            }
        },
        mounted() {
            this.getDownloadRecords(this.page)
        },
        methods: {
            getDownloadRecords(page) {
                let params = {
                    uid: 0,
                    page: page
                }
                queryDownloadRecords(params, response => {
                    console.log(response)
                    this.loading = false
                    this.totalNumber = response.data.numbers;
                    if (response.data.fileDownloadRecords.length === 0)
                        this.$message.warning("已经没有更多了~")
                    else
                        this.fileDownloadRecords = response.data.fileDownloadRecords
                })
            }
        }
    }
</script>

<style lang="less" scoped>
    .user-download {
        margin: 2vmin 0;

        h3 {
            margin: 1vmin;
            font-size: 2vmin;
        }

        p {
            margin: 1vmin;
            font-size: 1.5vmin;
        }

        span {
            margin: 1vmin;
            font-size: 1.5vmin;
        }

        .user-download-detail {
            margin: 1vmin 1vmin 0 1vmin;
        }
    }
</style>
