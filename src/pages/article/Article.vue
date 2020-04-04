<!--智库专栏页面-->
<template>
    <el-main>
        <el-row type="flex" justify="center">
            <el-col :xs="24" :sm="22" :md="20" :lg="18" :xl="18">
                <article-swiper :images="images"></article-swiper>
            </el-col>
        </el-row>

        <el-row type="flex" justify="center">
            <el-col class="article-body" :xs="24" :sm="22" :md="20" :lg="18" :xl="18">
                <article-item v-for="(article, index) in articles" :key="index" :article="article"></article-item>
            </el-col>
        </el-row>
    </el-main>
</template>

<script>
    import ArticleItem from "./ArticleItem";
    import ArticleSwiper from "./ArticleSwiper";

    export default {
        name: "Article",
        components: {ArticleItem, ArticleSwiper},
        data() {
            return {
                images: [require("../../assets/article-pictures/img_4.jpg")],
                articles: [
                    {
                        title: "软件运行老不好，多半是装的...装的不好！| 山软小便签",
                        img: require("../../assets/article-pictures/img_2.jpg"),
                        link: "https://mp.weixin.qq.com/s/ch-mgSf6OvNJ2AkGQooldQ"
                    }
                ]
            };
        },
        methods:{
            getImages(){},
            getArticles(){}
        }
    };
</script>

<style scoped>
    .article-body {
        min-height: calc(100vh - 69vmin);
        margin: 7vmin 0;
    }
</style>
