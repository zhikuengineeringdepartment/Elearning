/**普通用户路由 */
import {isLogin} from "../app/modules/functions";
import store from "../store/store";

let router = [
    {
        path: "knowledge",
        name: "Knowledge",
        component: () =>
            import(
                /* webpackChunkName: "knowledge" */ "../pages/knowledge/overview/Knowledge"
                )
    },
    {
        path: "knowledge/detail",
        name: "KnowledgeDetail",
        component: () =>
            import(
                /* webpackChunkName: "knowledge-detail" */ "../pages/knowledge/detail/KnowledgeDetail"
                )
    },
    {
        path: "resources",
        name: "Resources",
        component: () =>
            import(/* webpackChunkName: "resources" */ "../pages/resources/Resources")
    },
    {
        path: "resources/upload",
        name: "ResourcesFileUpload",
        component: () =>
            import(
                /* webpackChunkName: "resources-file-upload" */ "../pages/resources/ResourcesFileUpload"
                )
    },
    {
        path: "article",
        name: "Article",
        component: () =>
            import(/* webpackChunkName: "article" */ "../pages/article/Article")
    },
    {
        path: "about",
        name: "About",
        component: () =>
            import(/* webpackChunkName: "about" */ "../pages/about/About")
    },
    {
        path: "user",
        name: "User",
        component: () =>
            import(/* webpackChunkName: "user" */ "../pages/user/User"),
        children: [
            {
                path: "info",
                component: () =>
                    import(
                        /* webpackChunkName: "user-info" */ "../pages/user/information/UserInfo"
                        )
            },
            {
                path: "message",
                component: () =>
                    import(
                        /* webpackChunkName: "user-message" */ "../pages/user/message/UserMessage"
                        )
            },
            {
                path: "upload",
                component: () =>
                    import(
                        /* webpackChunkName: "user-upload" */ "../pages/user/upload/UserUpload"
                        )
            },
            {
                path: "download",
                component: () =>
                    import(
                        /* webpackChunkName: "user-download" */ "../pages/user/download/UserDownload"
                        )
            },
            {
                path: "collection",
                component: () =>
                    import(
                        /* webpackChunkName: "user-collection" */ "../pages/user/collection/UserCollection"
                        )
            }
        ]
    },
    /* TODO  论坛模块暂未完成，先注释掉路由 */
    // {
    //   path: "forum",
    //   name: "Forum",
    //   component: () =>
    //     import(
    //       /* webpackChunkName: "user-login" */ "../pages/forum/overview/Forum"
    //     )
    // },
    {
        path: "weekly/diary",
        name: "WeeklyDiary",
        component: () =>
            import(/* webpackChunkName: "user-login" */ "../pages/weeklyDiary/index")
    },
    {
        path: "user/login",
        name: "UserLogin",
        component: () =>
            import(/* webpackChunkName: "user-login" */ "../pages/user/UserLogin")
    },
    {
        path: "user/register",
        name: "UserRegister",
        component: () =>
            import(
                /* webpackChunkName: "user-register" */ "../pages/user/UserRegister"
                )
    },
    {
        path: "user/change_password",
        name: "UserChangePassword",
        component: () => import(/* webpackChunkName: "user-change-password" */ "../pages/user/UserChangePassword")
    }
];

let comRouter = [
    {
        path: "/",
        name: "home",
        redirect: "/knowledge",
        component: () => import(/* webpackChunkName: "index" */ "../pages/index"),
        children: router
    }
];

let comRouterGuard = (to, next) => {
    if (to.path === "/knowledge/detail" && store.state.course.courseId === -1) {
        next({
            path: "/knowledge"
        });
    }

    if (to.path === "/user" || to.path === "/user/") {
        if (!isLogin()) {
            // 未登录却跳转到user的情况
            next({
                path: "/user/login"
            });
        } else {
            next({
                path: "/user/info"
            });
        }
    }

    if (
        to.path.includes("/user/") &&
        !isLogin() &&
        to.path !== "/user/login" &&
        to.path !== "/user/register"
    ) {
        // 未登录却跳转到user的情况
        next({
            path: "/user/login"
        });
    }

    if (
        (to.path === "/user/login" || to.path === "/user/register") &&
        isLogin()
    ) {
        // 已登录却跳转到login或register的情况
        next({
            path: "/user/info"
        });
    }
};

export {comRouter, comRouterGuard};
