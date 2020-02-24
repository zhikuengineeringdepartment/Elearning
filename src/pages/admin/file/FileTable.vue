<template>
    <el-table :data="tableData" v-loading="loading" stripe style="width: 100%">
        <el-table-column
                prop="fileName"
                label="文件名"
                width="300"
                align="center"
        ></el-table-column>
        <el-table-column
                prop="upperName"
                label="上传者"
                width="150"
                align="center"
        ></el-table-column>
        <el-table-column label="操作" align="center">
            <!--            <template slot="header">-->
            <!--                <el-button @click="jumpToBeforePage" plain type="primary" size="mini"-->
            <!--                >上一页-->
            <!--                </el-button-->
            <!--                >-->
            <!--                <el-button @click="jumpToNextPage" plain type="primary" size="mini"-->
            <!--                >下一页-->
            <!--                </el-button-->
            <!--                >-->
            <!--            </template>-->
            <template slot-scope="scope">
                <el-button
                        @click="handlePreview(scope.row)"
                        v-if="buttonShow[0]"
                        type="primary"
                        size="mini"
                >预览
                </el-button>
                <el-button
                        @click="handleDownload(scope.row)"
                        v-if="buttonShow[1]"
                        type="primary"
                        size="mini"
                >下载
                </el-button>
                <el-button
                        @click="modifyFileStatus(scope.row, 1)"
                        v-if="buttonShow[2]"
                        type="success"
                        size="mini"
                >通过
                </el-button>
                <el-button
                        @click="modifyFileStatus(scope.row, 3)"
                        v-if="buttonShow[3]"
                        type="warning"
                        size="mini"
                >不通过
                </el-button>
                <el-button
                        @click="modifyFileStatus(scope.row, 2)"
                        v-if="buttonShow[4]"
                        type="warning"
                        size="mini"
                >重新审核
                </el-button>
                <el-button
                        @click="modifyFileStatus(scope.row, 4)"
                        v-if="buttonShow[5]"
                        type="danger"
                        size="mini"
                >删除
                </el-button>
            </template>
        </el-table-column>
    </el-table>
</template>

<script>
    export default {
        props: {
            tableData: Array,
            buttonShow: Array,
            loading: Boolean,
            status: Number
        },
        data() {
            return {
                // page: 1
            };
        },
        methods: {
            // jumpToBeforePage() {
            //     if (this.page === 1) {
            //         this.$message.warning("已经是第一页了")
            //         // this.$fn.toast("已经是第一页了", "warning", this)
            //         return;
            //     }
            //     this.page--;
            //     this.$emit("changePage", this.page, this.status);
            // },
            // jumpToNextPage() {
            //     this.page++;
            //     this.$emit("changePage", this.page, this.status);
            // },
            handlePreview(row) {
                this.$emit("handlePreview", row.fid, row.fileName);
            },
            handleDownload(row) {
                this.$emit("handleDownload", row.fid);
            },
            modifyFileStatus(row, status) {
                this.showBox(row, status);
            },
            showBox(row, status) {
                this.$confirm("此操作将更改文件状态, 是否继续?", "提示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                })
                    .then(() => {
                        this.$emit("modifyFileStatus", row.fid, status);
                    })
                    .catch(() => {
                        this.$message({
                            type: "info",
                            message: "已取消操作"
                        });
                    });
            }
        }
    };
</script>
