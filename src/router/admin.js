/**管理路由*/
import {getCookie} from "../app/modules/functions";

let adminRouter = [
    {
        path: "/admin",
        name: "Admin",
        redirect: "/admin/upload/image",
        component: () =>
            import(/* webpackChunkName: "admin" */ "../pages/admin/index"),
        children: [
            {
                path: "upload/image",
                component: () =>
                    import(
                        /* webpackChunkName: "admin-upload-image" */ "../pages/admin/upload/image/index"
                        )
            },
            {
                path: "upload/knowledge",
                component: () =>
                    import(
                        /* webpackChunkName: "admin-upload-knowledge" */ "../pages/admin/upload/knowledge/index"
                        )
            },
            {
                path: "preview/knowledge",
                component: () =>
                    import(
                        /* webpackChunkName: "admin-preview-knowledge" */ "../pages/admin/preview/index"
                        )
            },
            {
                path: "weekly/diary/upload",
                component: () =>
                    import(
                        /* webpackChunkName: "admin-weekly-diary-upload" */ "../pages/admin/weeklyDiary/index"
                        )
            },
            {
                path: "file/review",
                component: () =>
                    import(
                        /* webpackChunkName: "admin-file-review" */ "../pages/admin/file/index"
                        )
            },
            {
                path: "upload/article",
                component: () =>
                    import(
                        /* webpackChunkName: "admin-file-review" */ "../pages/admin/articleUp/index"
                        )
            },
            {
                path: "dataAnalysis",
                component: () =>
                    import(
                        "../pages/admin/dataAnalysis/index"
                    )
            }
        ]
    },
    {
        path: "*",
        name: "404",
        component: () =>
            import(/* webpackChunkName: "404" */ "../components/PageNotFound")
    }
];

let adminRouterGuard = (to, next) => {
    if (
        to.path.includes("/admin") &&
        (!getCookie("tokena") || !getCookie("token"))
    ) {
        next({path: "*"});
    }
};

export {adminRouter, adminRouterGuard};
