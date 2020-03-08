<template>
    <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="page"
            :page-size="pageSize"
            layout="prev, pager, next, jumper"
            :total="totalNumber"
    ></el-pagination>
</template>

<script>
    export default {
        name: "Pagination",
        props: {
            pageSize: Number,
            totalNumber: Number
        },
        data() {
            return {
                page: 1
            }
        },
        methods: {
            handleSizeChange(val) {
                console.log(`文件下载每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                this.$emit("handleCurrentChange", this.page)
                // this.getDownloadRecords(this.page);
                console.log(`文件下载当前页: ${val}`);
            }
        }
    }
</script>

<style scoped>

</style>