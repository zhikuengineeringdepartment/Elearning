import Vue from 'vue'
import Router from 'vue-router'
import store from '../store/store'
import {
  getCookie
} from '../tools.js'

Vue.use(Router);

let router = new Router({
  routes: [{
      path: '/',
      name: 'home',
      redirect:'/knowledge',
      component: () => import( /* webpackChunkName: "knowledge" */ '../pages/knowledge/overview/Knowledge')
    },
    {
      path: '/knowledge',
      name: 'Knowledge',
      component: () => import( /* webpackChunkName: "knowledge" */ '../pages/knowledge/overview/Knowledge')
    },
    {
      path: '/knowledge/detail',
      name: 'KnowledgeDetail',
      component: () => import( /* webpackChunkName: "knowledge-detail" */ '../pages/knowledge/detail/KnowledgeDetail')
    },
    {
      path: '/resources',
      name: 'Resources',
      component: () => import( /* webpackChunkName: "resources" */ '../pages/resources/Resources')
    },
    {
      path: '/resources/upload',
      name: 'ResourcesFileUpload',
      component: () => import( /* webpackChunkName: "resources-file-upload" */ '../pages/resources/ResourcesFileUpload')
    },
    {
      path: '/article',
      name: 'Article',
      component: () => import( /* webpackChunkName: "article" */ '../pages/article/Article')
    },
    {
      path: '/about',
      name: 'About',
      component: () => import( /* webpackChunkName: "about" */ '../pages/about/About')
    },
    {
      path: '/user',
      name: 'User',
      component: () => import( /* webpackChunkName: "user" */ '../pages/user/User'),
      children: [{
          path: 'info',
          component: () => import( /* webpackChunkName: "user-info" */ '../pages/user/information/UserInfo')
        },
        {
          path: 'message',
          component: () => import( /* webpackChunkName: "user-message" */ '../pages/user/message/UserMessage')
        },
        {
          path: 'upload',
          component: () => import( /* webpackChunkName: "user-upload" */ '../pages/user/upload/UserUpload')
        },
        {
          path: 'download',
          component: () => import( /* webpackChunkName: "user-download" */ '../pages/user/download/UserDownload')
        },
        {
          path: 'collection',
          component: () => import( /* webpackChunkName: "user-collection" */ '../pages/user/collection/UserCollection')
        }
      ]
    },
    {
      path: '/forum',
      name: 'Forum',
      component: () => import( /* webpackChunkName: "user-login" */ '../pages/forum/overview/Forum')
    },
    {
      path: '/user/login',
      name: 'UserLogin',
      component: () => import( /* webpackChunkName: "user-login" */ '../pages/user/UserLogin')
    },
    {
      path: '/user/register',
      name: 'UserRegister',
      component: () => import( /* webpackChunkName: "user-register" */ '../pages/user/UserRegister')
    },
    {
      path: '*',
      name: '404',
      component: () => import( /* webpackChunkName: "404" */ '../components/PageNotFound')
    }

  ]
});

// 路由设定
router.beforeEach((to, from, next) => {
  // 这里之所以要再进行一次路由的判断，就是为了让"通过复制url进入指定页面"的用户看到正确的tab状态。
  store.commit('setPath', to.path);
  if (to.path === '/knowledge/detail' && store.state.courseId !== -1) {
    next();
  } else if (to.path === '/knowledge/detail' && store.state.courseId === -1) {
    next({
      path: '/knowledge'
    });
    } else if (to.path === '/user' || to.path === '/user/') {
    if (!getCookie("token")) {
      // 未登录却跳转到user的情况
      next({
        path: '/user/login'
      });
    } else {
      next({
        path: '/user/info'
      });
    }
  } else if (to.path === '/user/login' || to.path === '/user/register') {
    if (getCookie("token")) {
      // 已登录却跳转到login或register的情况
      next({
        path: '/user/info'
      });
    } else {
      next();
    }
  } else if (to.path.includes('/user/')) {
    if (!getCookie("token")) {
      // 未登录却跳转到user的情况
      next({
        path: '/user/login'
      });
    } else {
      next();
    }
  }
  next();
});

export default router;