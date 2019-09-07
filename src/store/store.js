// 使用vuex进行全局的数据状态管理

import Vue from 'vue'
import Vuex from 'vuex'
import {user, isLogin, tabIndex, isMobile, courseId, sectionId, fileId} from './global'

Vue.use(Vuex);

const store = new Vuex.Store({
  state: {
    user, // 用户对象
    isLogin, // 登录状态
    tabIndex, // 选中了哪个tab
    isMobile, // 是否为手机端
    courseId, // 课程Id
    sectionId, // 章节id
    fileId // 预览文件的id
  },
  mutations: {
    // 设置user
    setUser (state, newUser) {
      state.user.username = newUser.username;
      state.user.userIcon = newUser.userIcon;
    },
    // 设置tabBarStatus
    setTabIndex (state, newTabIndex) {
      state.tabIndex = newTabIndex;
    },
    // 更改浏览模式
    changeMode (state, isMobile) {
      state.isMobile = isMobile;
    },
    // 更改登录状态
    changeLoginStatus (state, loginStatus) {
      state.isLogin = loginStatus;
    },
    setCourseId(state, cid) {
      state.courseId = cid;
    },
    setSectionId(state, sid) {
      state.sectionId = sid;
    },
    setFid(state, fid) {
      state.fileId = fid;
    }
  }
});

export default store;
