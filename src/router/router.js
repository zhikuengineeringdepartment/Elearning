import Vue from "vue"
import Router from "vue-router"
import store from "../store/store"
import comRouter from "./com"
import adminRouter from "./admin"
import { getCookie } from "../tools.js"

Vue.use(Router);

let router = new Router({
  routes: adminRouter.concat(comRouter)
});

// 路由设定
router.beforeEach((to, from, next) => {
  // 这里之所以要再进行一次路由的判断，就是为了让"通过复制url进入指定页面"的用户看到正确的tab状态。
  store.commit("setPath", to.path);
  if (to.path === "/knowledge/detail" && store.state.courseId !== -1) {
    next();
  } else if (to.path === "/knowledge/detail" && store.state.courseId === -1) {
    next({
      path: "/knowledge"
    });
  } else if (to.path === "/user" || to.path === "/user/") {
    if (!getCookie("token")) {
      // 未登录却跳转到user的情况
      next({
        path: "/user/login"
      });
    } else {
      next({
        path: "/user/info"
      });
    }
  } else if (to.path === "/user/login" || to.path === "/user/register") {
    if (getCookie("token")) {
      // 已登录却跳转到login或register的情况
      next({
        path: "/user/info"
      });
    } else {
      next();
    }
  } else if (to.path.includes("/user/")) {
    if (!getCookie("token")) {
      // 未登录却跳转到user的情况
      next({
        path: "/user/login"
      });
    } else {
      next();
    }
  }
  next();
});

export default router;
