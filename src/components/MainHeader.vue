<!--页面页眉组件-->
<template>
    <el-header class="menu-bar" style="height: 10vh;">
        <el-row style="height: 100%;">
            <!--移动端header-->
            <el-col :xs="4" :sm="4" :md="2" :lg="2" :xl="2" v-if="$store.state.isMobile">
                <el-dropdown trigger="click" @command="menuClick">
                    <i class="el-icon-s-operation"></i>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item v-for="menu in menuList" :key="menu.path" :command="menu.path">
                            {{menu.name }}
                        </el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </el-col>
            <el-col :xs="16" :sm="16" :md="20" :lg="20" :xl="20" style="height: 10vh;line-height: 10vh"
                    v-if="$store.state.isMobile">
                <img class="header-icon" :src="sharingIdeaImage" @click="menuClick('/knowledge')"/>
            </el-col>

            <!--pc端header-->
            <el-col :xs="4" :sm="4" :md="2" :lg="2" :xl="2" style="height: 10vh;line-height: 10vh"
                    v-if="!$store.state.isMobile">
                <img class="header-icon" :src="sharingIdeaImage" @click="menuClick('/knowledge')"/>
            </el-col>
            <el-col :xs=" 16" :sm="16" :md="20" :lg="20" :xl="20" v-if="!$store.state.isMobile">
                <el-menu :default-active="activeIndex" class="menu" mode="horizontal" :router="true">
                    <template v-for="menu in menuList">
                        <el-submenu v-if="menu.children" :index="menu.path" :key="menu.path">
                            <template slot="title">{{menu.name}}</template>
                            <el-menu-item v-for="child in menu.children" :key="child.path" :index="child.path">
                                {{child.name}}
                            </el-menu-item>
                        </el-submenu>
                        <el-menu-item v-else :index="menu.path" :key="menu.path">{{menu.name}}</el-menu-item>
                    </template>
                </el-menu>
            </el-col>

            <el-col :xs="4" :sm="4" :md="2" :lg="2" :xl="2" style="height: 100%;line-height: 10vh;">
                <img
                        class="header-user"
                        :src="userIcon || $store.state.user.userIcon"
                        @click="menuClick('/user')"
                />
            </el-col>
        </el-row>

    </el-header>
    <!--        <div class="menu-bar">-->
    <!--            &lt;!&ndash;智库logo&ndash;&gt;-->
    <!--            <img class="header-icon" :src="sharingIdeaImage"/>-->
    <!--            &lt;!&ndash;手机端tab&ndash;&gt;-->
    <!--            <ul class="header-menu" v-if="$store.state.isMobile">-->
    <!--                &lt;!&ndash; TODO width根据tab数目而改变&ndash;&gt;-->
    <!--                <li class="sub-menu" style="width:25%;" v-for="item in mobileList" :key="item.path">-->
    <!--            <span v-if="$store.state.path.includes(item.path)"-->
    <!--                  @click="menuClick(item.path)"-->
    <!--                  class="content active">{{ item.menu }}</span>-->
    <!--                    <span v-else @click="menuClick(item.path)" class="content">{{item.menu}}</span>-->
    <!--                </li>-->
    <!--            </ul>-->
    <!--            &lt;!&ndash;PC端tab&ndash;&gt;-->
    <!--            <ul class="header-menu" v-else>-->
    <!--                <li class="sub-menu" v-for="item in pcList" :key="item.path">-->
    <!--            <span v-if="$store.state.path.includes(item.path)"-->
    <!--                  @click="menuClick(item.path)"-->
    <!--                  class="content active">{{ item.menu }}</span>-->
    <!--                    <span v-else @click="menuClick(item.path)" class="content">{{item.menu}}</span>-->
    <!--                </li>-->
    <!--            </ul>-->
    <!--            &lt;!&ndash;用户头像/登录注册按钮&ndash;&gt;-->
    <!--            <img-->
    <!--                    class="header-user"-->
    <!--                    :src="userIcon || $store.state.user.userIcon"-->
    <!--                    @click="menuClick('/user')"-->
    <!--            />-->
    <!--        </div>-->
</template>

<script>
    import {changePageReq} from "../../src/app/apis/dataAnalysisApi";
    import {queryArticleTypes} from "../app/apis/articleApi";

    export default {
        name: "MainHeader",
        props: {
            userIcon: String
        },
        data() {
            return {
                sharingIdeaImage: require("../assets/sharing-idea-logo.png"),
                // userImage: require("../assets/user-default.png"),
                activeIndex: "/knowledge",
                menuList: [
                    {name: "知识见解", path: "/knowledge"},
                    {name: "文件资源", path: "/resources"},
                    // { menu: "在线论坛", path: "/forum" },
                    {
                        name: "智库专栏",
                        path: "/article",
                        children: []
                    },
                    {name: "智库周记", path: "/weekly/diary"},
                    {name: "关于智库", path: "/about"}
                ]
            };
        },
        created() {
            this.activeIndex = window.location.hash.substr(1)
            this.getArticleTypes()
        },
        methods: {
            menuClick: function (path) {
                this.activeIndex = path
                let postData = {"lastURI": this.$route.path, "nextURI": path};
                if (path.includes("/user")) {
                    if (this.$fn.isLogin()) {
                        this.$router.push("/user/info");
                        postData.nextURI = "/user/info"
                    } else {
                        this.$router.push("/user/login");
                        postData.nextURI = "/user/login";
                    }
                } else {
                    this.$router.push(path);
                }
                //大页面跳转时发送请求
                changePageReq(postData, response => {
                });
            },
            getArticleTypes: function () {
                queryArticleTypes({}, response => {
                    let {specialColumns} = response.data
                    this.menuList = this.menuList.map(item => {
                        if (item.children) {
                            item.children = specialColumns.map(type => ({
                                name: type.specialcName,
                                path: '/article?type=' + type.sid
                            }))
                        }
                        return item
                    })
                })
            }
        }
    };
</script>

<style>
    .el-menu--horizontal > .el-submenu.is-active .el-submenu__title {
        border-bottom: 2px solid #000099;
    }

    .el-menu--horizontal > .el-submenu .el-submenu__title {
        height: 10vh;
        line-height: 10vh;
    }
</style>

<style lang="less" scoped>
    /** {*/
    /*    margin: 0;*/
    /*    padding: 0;*/
    /*}*/

    li {
        list-style: none;
    }

    .menu-bar {
        height: 10vh;
        width: 100%;
        background: #fff;
        box-shadow: 0 2px 5px #ccc;
        /*display: flex;*/
        /*flex-direction: row;*/

        .menu {
            border: none;

            .el-menu-item {
                height: 10vh;
                line-height: 10vh;
                padding: 0 2vw;
            }

            .el-submenu {
                display: flex;
                align-items: center;
                height: 10vh;
            }

            .el-menu-item.is-active {
                border-bottom: 2px solid #000099;
            }
        }

        .header-icon {
            width: 6vh;
            height: 6vh;
            cursor: pointer;
        }

        .el-dropdown {
            height: 10vh;
            line-height: 10vh;
            font-size: 26px;
        }

        .header-user {
            width: 5vh;
            height: 5vh;
            cursor: pointer;
        }
    }
</style>
