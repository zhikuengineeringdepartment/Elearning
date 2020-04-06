<template>
    <div id="app">
        <router-view v-if="isRouterAlive"/>
    </div>
</template>

<script>
    export default {
        name: "app",
        provide() {
            return {
                reload: this.reload
            };
        },
        data() {
            return {
                isRouterAlive: true
            };
        },
        mounted() {
            console.log(this.$route.path);
            // 进入页面发请求
            this.EnterPageReq();
            // window.addEventListener("beforeunload", e => this.beforeunloadHandler(e));
            // console.log("Hello!mounted");
            // window.addEventListener("unload", e => this.unloadHandler(e));
        },
        methods: {
            //页面刷新
            reload() {
                this.isRouterAlive = false;
                this.$nextTick(function () {
                    console.log("reload");
                    this.isRouterAlive = true;
                });
            },
            //进入页面发请求，与离开相似
            //null to url
            EnterPageReq(){
                console.log("Welcome to sharingideas")
                let xhr;
                if (window.XMLHttpRequest) {
                // code for IE7+, Firefox, Chrome, Opera, Safari
                xhr = new XMLHttpRequest();
                } else {
                // code for IE6, IE5
                xhr = new ActiveXObject("Microsoft.XMLHTTP");
                }
                //请求的路径
                let apiPath = process.env.NODE_ENV === "production"
                ? "dataStatistics/access"
                : "api/dataStatistics/access" // 正式环境与开发环境的url
                xhr.open("POST",apiPath, false);
                xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                // lastURI: "/",          //上一个页面uri
                // nextURI: "/resources",      //即将跳转的页面uri
                //注意这里的值
                let lastURI = null;
                let nextURI = this.$route.path;
                // let parm = "lastURI=" + lastURI + "&nextURI=" + nextURI;
                let postData = {"lastURI":lastURI,"nextURI":nextURI};
                xhr.send(postData);
            },
        },
        // destroy(){
        //     window.removeEventListener("beforeunload", e =>this.beforeunloadHandler(e));
        // }
    };
</script>

<style>
    /*QQ的初始化CSS代码*/
    body,
    ol,
    ul,
    h1,
    h2,
    h3,
    h4,
    h5,
    h6,
    p,
    th,
    td,
    dl,
    dd,
    form,
    fieldset,
    legend,
    input,
    textarea,
    select {
        margin: 0;
        padding: 0;
    }

    /* TODO 移动端字体需要调整*/
    body {
        font-size: 12px;
        font-family: Lato, Helvetica Neue, Helvetica, sans-serif;
        background: #fff;
        -webkit-text-size-adjust: 100%;
    }

    a {
        color: #2d374b;
        text-decoration: none;
    }

    a:hover {
        /* color: #cd0200;
        text-decoration: underline; */
        cursor: pointer;
    }

    em {
        font-style: normal;
    }

    li {
        list-style: none;
    }

    img {
        border: 0;
        vertical-align: middle;
    }

    table {
        border-collapse: collapse;
        border-spacing: 0;
    }

    p {
        word-wrap: break-word;
    }

    #app {
        font-family: "Avenir", Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
    }
</style>
