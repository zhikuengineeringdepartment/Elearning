<template>
    <el-carousel :interval="4000" height="50vmin">
        <el-carousel-item v-for="(img,index) in images" :key="index">
            <el-image :src="img" fit="fill" style="width: 100%; height: 100%;border-radius: 4px;">
                <div slot="placeholder" class="image-slot">
                    加载中<span class="dot">...</span>
                </div>
            </el-image>
        </el-carousel-item>
    </el-carousel>
</template>

<script>
    export default {
        name: "ArticleSwiper",
        props: {
            images: Array
        }
    }
</script>

<style scoped>

</style>