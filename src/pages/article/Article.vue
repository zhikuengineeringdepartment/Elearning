<!--智库专栏页面-->
<template>
    <el-main>
        <el-row type="flex" justify="center" v-if="images[0]">
            <el-col :xs="24" :sm="22" :md="20" :lg="18" :xl="18">
                <article-swiper :images="images"></article-swiper>
            </el-col>
        </el-row>

        <el-row type="flex" justify="center">
            <el-col class="article-body" :xs="24" :sm="22" :md="20" :lg="18" :xl="18" v-if="articles[0]">
                <article-item v-for="(article, index) in articles" :key="index" :article="article"></article-item>
            </el-col>
            <h1 style="margin: 30vh 0" v-else>NULL</h1>
        </el-row>

        <el-col :span="24" style="padding: 5px 0" v-if="!$store.state.isMobile&&articles[0]">
            <pagination :pageSize="5" :totalNumber="totalNumber"
                        @handleCurrentChange="getArticles"></pagination>
        </el-col>

    </el-main>
</template>

<script>
    import ArticleItem from "./ArticleItem";
    import ArticleSwiper from "./ArticleSwiper";
    import Pagination from "../../components/Pagination";
    import {queryAllArticles, queryArticles} from "../../app/apis/articleApi";

    export default {
        name: "Article",
        components: {ArticleItem, ArticleSwiper, Pagination},
        data() {
            return {
                images: [require('../../assets/article-pictures/banner_1.jpg'),
                    require('../../assets/article-pictures/banner_2.jpg'),
                    require('../../assets/article-pictures/banner_3.jpg'),
                    require('../../assets/article-pictures/banner_4.jpg')],
                articles: [],
                totalNumber: 10
            };
        },
        created() {
            if (this.$store.state.isMobile)
                this.getAllArticles()
            else
                this.getArticles()
        },
        watch: {
            '$route'() {
                this.getArticles();
            }
        },
        methods: {
            getImages() {
            },
            getArticles(page = 1) {
                let specialColumnId = this.$route.query.type
                let params = {
                    specialColumnId,
                    start: page,
                    size: 5
                }
                queryArticles(params, response => {
                    this.articles = response.data.specialColumns
                    this.totalNumber = response.data.total
                })
            },
            getAllArticles() {
                queryAllArticles({}, response => {
                    this.articles = response.data.specialColumnArticleList
                    this.totalNumber = response.data.specialColumnArticleList.length
                })
            }
        }
    };
</script>

<style scoped>
    .article-body {
        min-height: calc(100vh - 69vmin);
        margin: 7vmin 0;
    }
</style>
