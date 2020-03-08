<!--用户-文件上传记录组件-->
<template>
    <div>
        <div v-if="fileUploadRecords.length === 0">
            <div>没有上传的文件记录哦！</div>
        </div>
        <div v-if="fileUploadRecords.length > 0">
            <el-card shadow="hover" class="user-upload" v-for="(fileUploadRecord, index) in fileUploadRecords"
                     :key="index">
                <el-row>
                    <el-col>
                        <h3>{{fileUploadRecord.fileName}}</h3>
                    </el-col>
                    <el-col>
                        <span>上传时间：{{fileUploadRecord.fileUploadTime}}</span>
                    </el-col>
                    <el-col>
                        <p>{{fileUploadRecord.fileDesc}}</p>
                    </el-col>
                    <el-col class="user-upload-detail">
                        <file-tag
                                :status="fileUploadRecord.fileStatus"
                                :tags="fileUploadRecord.fileKeys"
                        />
                    </el-col>
                </el-row>
            </el-card>
            <pagination :pageSize="10" :totalNumber="totalNumber"
                        @handleCurrentChange="getUploadRecords"></pagination>
            <p v-if="loading">加载中...</p>
        </div>
    </div>
</template>

<script>
    import FileTag from "../../../components/FileTag";
    import {queryUploadRecords} from "../../../app/apis/userApi";

    export default {
        name: "UserUpload",
        components: {FileTag},
        data() {
            return {
                page: 1,
                fileUploadRecords: [],
                loading: true,
                totalNumber: 10
            }
        },
        mounted() {
            this.getUploadRecords(this.page)
            document.onscroll = () => {
                if (this.$route.path === '/user/upload') {
                    this.$fn.lazyLoading(() => {
                        this.loading = true
                        this.page++
                        this.getUploadRecords(this.page)
                    })
                }
            }
        },
        methods: {
            getUploadRecords(page) {
                let params = {
                    uid: 0,
                    page: page
                }
                queryUploadRecords(params, response => {
                    this.loading = false
                    this.totalNumber = response.data.numbers;
                    if (response.data.fileUploadRecords.length === 0)
                        this.$message.warning("已经没有更多了~")
                    else
                        this.fileUploadRecords = response.data.fileUploadRecords;
                })
            }
        }
    }
</script>

<style lang="less" scoped>
    .user-upload {
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

        .user-upload-detail {
            margin: 1vmin 1vmin 0 1vmin;
        }
    }
</style>
