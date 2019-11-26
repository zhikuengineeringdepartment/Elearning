/**管理路由*/

export default [
  {
    path: "/admin",
    name: "Admin",
    component: () =>
      import(/* webpackChunkName: "admin" */ "../pages/admin/Admin"),
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
      }
    ]
  }
];
