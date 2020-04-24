<!--文件资源页面-->
<template>
    <el-main class="resources">
        <el-col :span="20">
            <el-card shadow="always" class="resources-card">
                <h1>Hello , world</h1>
                <p>每一个不曾起舞的日子都是对生命的浪费</p>
                <el-button type="primary" @click="gotoUpload">
                    上传
                    <i class="el-icon-upload el-icon--right"></i>
                </el-button>
            </el-card>
        </el-col>

        <el-col :span="20">
            <el-row>
                <div class="resources-select">
                    <!--这里暂不支持课程选择-->
                    <!--<course-select v-on:get-course-value="setCourseValue"></course-select>-->
                    <el-input class="resources-search" placeholder="可输入文件名/tag" v-model="fileListForm.keyWord">
                        <el-button slot="append" icon="el-icon-search" @click="doSearch"></el-button>
                    </el-input>
                    <div v-if="fileListForm.order && $store.state.isMobile">
                        <el-button icon="el-icon-sort-down" @click="changeOrder"></el-button>
                    </div>
                    <div v-if="!fileListForm.order && $store.state.isMobile">
                        <el-button icon="el-icon-sort-up" @click="changeOrder"></el-button>
                    </div>
                    <div v-if="fileListForm.order && !$store.state.isMobile">
                        <el-button icon="el-icon-sort-down" @click="changeOrder">按时间降序</el-button>
                    </div>
                    <div v-if="!fileListForm.order && !$store.state.isMobile">
                        <el-button icon="el-icon-sort-up" @click="changeOrder">按时间升序</el-button>
                    </div>
                </div>
            </el-row>
        </el-col>

        <el-col :span="20" class="file-list-detail">
            <resources-file v-for="(fileItem, index) in myFiles" :key="index" :fileItem="fileItem"></resources-file>
        </el-col>
        <el-col :span="20" style="padding: 5px 0">
            <pagination style="float: right" :pageSize="10" :totalNumber="totalNumber"
                        @handleCurrentChange="getFileList"></pagination>
        </el-col>
    </el-main>
</template>

<script>
    // import CourseSelect from "../../components/CourseSelect";
    import ResourcesFile from './ResourcesFile'
    import Pagination from "../../components/Pagination";
    import {queryFileList} from '../../app/apis/resourceApi'

    export default {
        name: 'Resources',
        // components: {ResourcesFile, CourseSelect},
        components: {Pagination, ResourcesFile},
        data() {
            return {
                fileListForm: {
                    keyWord: '',
                    order: true,
                    fileCourse: '',
                    page: 1
                },
                myFiles: [],
                totalNumber: 10
            }
        },
        computed: {
            _keyWord() {
                return this.fileListForm.keyWord;
            }
        },
        watch: {
            _keyWord(val) {
                if (!val) {
                    this.fileListForm.page = 1
                    this.getFileList(this.fileListForm.page)
                }
            }
        },
        methods: {
            gotoUpload: function () {
                this.$fn.routerChange('/resources/upload', this)
            },
            changeOrder() {
                this.fileListForm.order = !this.fileListForm.order
                this.myFiles.reverse()
            },
            // setCourseValue: function (cid) {
            //   this.fileListForm.fileCourse = cid;
            // },
            doSearch: function () {
                this.myFiles = []
                this.fileListForm.page = 1
                this.getFileList(this.fileListForm.page)
            },
            getFileList: function (page) {
                let params = {
                    keyWord: this.fileListForm.keyWord,
                    fileCourse: this.fileListForm.fileCourse,
                    page: page,
                    order: this.fileListForm.order
                }
                queryFileList(params, response => {
                    this.totalNumber = response.data.numbers
                    if (response.data.files.length === 0)
                        this.$message.warning("已经没有更多了~")
                    else
                        this.myFiles = response.data.files
                })
            }
        },
        mounted() {
            this.getFileList(this.fileListForm.page)
        }
    }
</script>

<style lang="less" scoped>
    .resources {
        margin-top: 3vmin;
        display: flex;
        flex-direction: column;
        align-items: center;
        text-align: left;

        .resources-card {
            margin-bottom: 3vmin;
            padding: 20px;

            h1 {
                font-size: 5vmin;
                margin: 1vmin auto;
            }

            p {
                font-size: 2vmin;
                margin: 2vmin auto;
            }
        }

        .resources-select {
            display: flex;
            flex-direction: row;
            margin-bottom: 3vmin;

            .resources-search {
                margin: 0 5vmin 0 0;
            }
        }
    }
</style>
