<!--知识见解页面-每门课程组件-->
<template>
    <el-card class="knowledge-item">
        <div @click="goKnowledgeDetail">
            <img :src="course.courseIcon"/>
            <div>
                <span>{{course.courseName}} - {{course.courseDesc}}</span>
            </div>
        </div>
    </el-card>
</template>

<script>
    export default {
        name: "KnowledgeItem",
        props: ["course"],
        methods: {
            goKnowledgeDetail: function () {
                // console.log('current course id:', this.course.cid);
                this.$store.commit("setCourse", this.course);
                this.$fn.routerChange("/knowledge/detail", this);
            }
        }
    };
</script>

<style scoped>
    .knowledge-item {
        float: left;
        margin: 5vmin;
    }
</style>
