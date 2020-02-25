<!--知识见解页面组件-->
<template>
    <el-main>
        <el-row type="flex" justify="center">
            <el-col :span="24">
                <div v-for="(course, index) in courses" :key="index">
                    <knowledge-item :course="course"></knowledge-item>
                </div>
            </el-col>
        </el-row>
    </el-main>
</template>

<script>
    import KnowledgeItem from "./KnowledgeItem";
    import {queryCourseList} from "../../../app/apis/knowledgeApi";

    export default {
        name: "Knowledge",
        components: {KnowledgeItem},
        data() {
            return {
                courses: []
            };
        },
        created() {
            queryCourseList({uid: 0}, response => this.courses = response.data.courses)
        }
    };
</script>

<style scoped></style>
