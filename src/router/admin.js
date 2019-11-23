/**管理路由*/

export default [
  {
    path: "/admin",
    name: "Admin",
    component: () =>
      import(/* webpackChunkName: "user-register" */ "../pages/admin/Admin"),
    children: [
      {
        path: "upload/knowledge",
        component: () =>
          import(
            /* webpackChunkName: "user-info" */ "../pages/admin/upload/UploadKnowledge"
          )
      }
    ]
  }
];
