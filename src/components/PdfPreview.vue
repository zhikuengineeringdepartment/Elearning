<template>
    <el-dialog :title="title" :visible="isPreviewShow" v-loading="loading" :before-close="close" center>
        <div>
            <pdf
                    :src="src"
                    :page="currentPage"
                    @num-pages="pageCount=$event"
                    @page-loaded="currentPage=$event"
                    @loaded="loadPdfHandler">
            </pdf>
        </div>
        <div slot="footer">
            <el-button type="primary" size="mini" @click="changePdfPage(0)" style="margin:0 10px">上一页</el-button>
            {{currentPage}} / {{pageCount}}
            <el-button type="primary" size="mini" @click="changePdfPage(1)" style="margin:0 10px">下一页</el-button>
        </div>
    </el-dialog>
</template>

<script>
    import pdf from 'vue-pdf'

    export default {
        name: "PdfPreview",
        components: {pdf},
        props: {
            isPreviewShow: Boolean,
            PreviewSrc: String,
            title: String
        },
        data() {
            return {
                currentPage: 0,
                pageCount: 0,
                fileType: 'pdf',
                src: '',
                loading: true,
                timer: null
            }
        },
        created() {
            console.log("预览")
            this.src = pdf.createLoadingTask(this.PreviewSrc)
            this.timer = setTimeout(() => {
                this.loading = false
                this.close()
                this.$message.warning("网络似乎不太好哦~")
            }, 15000)
        },
        watch: {
            pageCount(newVal) {
                if (newVal != undefined) {
                    this.loading = false
                    clearTimeout(this.timer)
                    this.timer = null
                }
            }
        },
        methods: {
            changePdfPage(val) {
                if (val === 0 && this.currentPage > 1)
                    this.currentPage--
                if (val === 1 && this.currentPage < this.pageCount)
                    this.currentPage++
            },
            loadPdfHandler(e) {
                this.currentPage = 1 // 加载的时候先加载第一页
            },
            close() {
                this.$emit("closePreview")
            }
        }
    }
</script>