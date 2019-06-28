import Vue from 'vue'
import Router from 'vue-router'
import store from '../store/store'

Vue.use(Router);

let router = new Router({
  routes: [
    {
      path: '/',
      name: 'Knowledge',
      component: () => import(/* webpackChunkName: "knowledge" */'../pages/knowledge/Knowledge.vue')
    },
    {
      path: '/resources',
      name: 'Resources',
      component: () => import(/* webpackChunkName: "resources" */'../pages/resources/Resources'),
    },
    {
      path: '/resources/upload',
      name: 'ResourcesFileUpload',
      component: () => import(/* webpackChunkName: "resources-fil-upload" */ '../pages/resources/ResourcesFileUpload')
    },
    {
      path: '/article',
      name: 'Article',
      component: () => import(/* webpackChunkName: "article" */'../pages/article/Article')
    },
    {
      path: '/about',
      name: 'About',
      component: () => import(/* webpackChunkName: "about" */'../pages/about/About')
    },
    {
      path: '/user',
      name: 'User',
      component: () => import(/* webpackChunkName: "user" */'../pages/user/User'),
      children: [
        {
          path: 'info',
          component: () => import(/* webpackChunkName: "user-info" */ '../pages/user/user-sub-pages/UserInfo')
        },
        {
          path: 'message',
          component: () => import(/* webpackChunkName: "user-message" */ '../pages/user/user-sub-pages/UserMessage')
        },
        {
          path: 'upload',
          component: () => import(/* webpackChunkName: "user-upload" */ '../pages/user/user-sub-pages/UserUpload')
        },
        {
          path: 'download',
          component: () => import(/* webpackChunkName: "user-download" */ '../pages/user/user-sub-pages/UserDownload')
        },
        {
          path: 'collection',
          component: () => import(/* webpackChunkName: "user-collection" */ '../pages/user/user-sub-pages/UserCollection')
        }
      ]
    },
    {
      path: '/user/login',
      name: 'UserLogin',
      component: () => import(/* webpackChunkName: "user-login" */ '../pages/user/UserLogin')
    },
    {
      path: '/user/register',
      name: 'UserRegister',
      component: () => import(/* webpackChunkName: "user-register" */ '../pages/user/UserRegister')
    },
    {
      path: '*',
      name: '404',
      component: () => import(/* webpackChunkName: "404" */'../components/PageNotFound')
    }
  
  ]
});

// 路由设定
router.beforeEach((to, from, next) => {
  // 这里之所以要再进行一次路由的判断，就是为了让"通过复制url进入指定页面"的用户看到正确的tab状态。
  if (to.path === '/') {
    store.commit('setTabIndex', 0);
    next();
    
  } else if (to.path === '/resources' || to.path === '/resources/upload') {
    store.commit('setTabIndex', 1);
    next();
    
  } else if (to.path === '/article') {
    store.commit('setTabIndex', 2);
    next();
    
  } else if (to.path === '/about') {
    store.commit('setTabIndex', 3);
    next();
    
  } else if (to.path === '/user') {
    store.commit('setTabIndex', 4);
    if (!store.state.isLogin) {
      // 未登录却跳转到user的情况
      next({path: '/user/login'});
    } else {
      next({path: '/user/info'});
    }
    
  } else if (to.path === '/user/info' || to.path === '/user/message' ||
    to.path === '/user/download' || to.path === '/user/upload' || to.path === '/user/collection') {
    store.commit('setTabIndex', 4);
    if (!store.state.isLogin) {
      // 未登录却跳转到user的情况
      next({path: '/user/login'});
    } else {
      next();
    }
    
  } else if (to.path === '/user/login' || to.path === '/user/register') {
    store.commit('setTabIndex', 4);
    if (store.state.isLogin) {
      // 已登录却跳转到login或register的情况
      next({path: '/user/info'});
    } else {
      next();
    }
    
  }
});

export default router;
