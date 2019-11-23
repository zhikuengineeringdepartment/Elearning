/**普通用户路由 */

let comRouter = [
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
  {
    path: "forum",
    name: "Forum",
    component: () =>
      import(
        /* webpackChunkName: "user-login" */ "../pages/forum/overview/Forum"
      )
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
  }
];

export default [
  {
    path: "/",
    name: "home",
    redirect: "/knowledge",
    component: () => import(/* webpackChunkName: "index" */ "../pages/index"),
    children: comRouter
  },

  {
    path: "*",
    name: "404",
    component: () =>
      import(/* webpackChunkName: "404" */ "../components/PageNotFound")
  }
];
