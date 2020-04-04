<!--知识见解详情-->
<template>
    <div class="knowledge-detail-body">
        <el-main>
            <el-row type="flex" class="row-bg" justify="center">
                <!--手机端布局，使用drawer来进行章节选择-->
                <div v-if="$store.state.isMobile">
                    <div>
                        <div class="show-menu" @click="showDrawerClick">
                            章
                            <br/>节
                            <br/>选
                            <br/>择
                        </div>
                        <el-drawer
                                size="80%"
                                title="章节选择"
                                :visible.sync="drawerShow"
                                :direction="drawerDirection"
                        >
                            <KnowledgeSectionSelect
                                    :courseView="course.courseView"
                                    @setSectionView="setSectionView"
                                    @setCsdn="setCsdn"
                            />
                        </el-drawer>
                    </div>
                    <el-col :span="24">
                        <knowledge-section-navigator
                                :side="course.side"
                                @setSectionView="setSectionView"
                                @setCsdn="setCsdn"
                        />
                        <knowledge-section
                                :sectionView="course.sectionView"
                                :noteViews="course.noteViews"
                                :colParas="course.colParas"
                                @setNoteView="setNoteView"
                                @setColParas="setColParas"
                        ></knowledge-section>
                        <knowledge-section-navigator
                                :side="course.side"
                                @setSectionView="setSectionView"
                                @setCsdn="setCsdn"
                        />
                        <knowledge-section-blog :blog="csdn"/>
                    </el-col>
                </div>
                <!--电脑端布局-->
                <div v-if="!$store.state.isMobile" class="computer-knowledge">
                    <div class="computer-select">
                        <KnowledgeSectionSelect
                                :courseView="course.courseView"
                                @setSectionView="setSectionView"
                                @setCsdn="setCsdn"
                        />
                    </div>
                    <div class="computer-content">
                        <knowledge-section-navigator
                                :side="course.side"
                                @setSectionView="setSectionView"
                                @setCsdn="setCsdn"
                        />
                        <knowledge-section
                                :sectionView="course.sectionView"
                                :noteViews="course.noteViews"
                                :colParas="course.colParas"
                                @setNoteView="setNoteView"
                                @setColParas="setColParas"
                        ></knowledge-section>
                        <knowledge-section-navigator
                                :side="course.side"
                                @setSectionView="setSectionView"
                                @setCsdn="setCsdn"
                        />
                        <knowledge-section-blog :blog="course.csdn"/>
                    </div>
                </div>
            </el-row>
        </el-main>
    </div>
</template>

<script>
    import KnowledgeSection from './KnowledgeSection'
    import KnowledgeSectionBlog from './KnowledgeSectionBlog'
    import KnowledgeSectionNavigator from './KnowledgeSectionNavigator'
    import KnowledgeSectionSelect from './KnowledgeSectionSelect'
    import Course from '../../../app/modules/Course'

    export default {
        name: 'KnowledgeDetail',
        components: {
            KnowledgeSectionSelect,
            KnowledgeSectionNavigator,
            KnowledgeSectionBlog,
            KnowledgeSection
        },
        data: function () {
            return {
                drawerDirection: 'ltr',
                drawerShow: false,
                course: {}
            }
        },
        created() {
            let course = new Course(this.$store.state.course.courseId)
            course.init()
            this.course = course
        },
        methods: {
            showDrawerClick: function () {
                this.drawerShow = true
            },
            setSectionView: function (sid) {
                this.course.setSectionView(sid)
            },
            setCsdn: function (sid) {
                this.course.setCsdn(sid)
            },
            setNoteView: function (sid) {
                this.course.setNoteView(sid)
            },
            setColParas: function (sid) {
                this.course.setColParas(sid)
            }
        }
    }
</script>

<style scoped>
    .knowledge-detail-body {
        min-height: calc(100vh - 69vmin);
    }

    .show-menu {
        color: #409eff;
        border: #a7d7f9 1px solid;
        padding: 3vmin 0;
        position: fixed;
        left: 0;
        top: 20vmin;
        width: 6vmin;
    }

    .computer-knowledge {
        display: flex;
        flex-direction: row;
        width: 100%;
    }

    .computer-select {
        width: 25%;
    }

    .computer-content {
        width: 75%;
    }
</style>
