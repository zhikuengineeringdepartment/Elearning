// 使用vuex进行全局的数据状态管理

import Vue from 'vue'
import Vuex from 'vuex'
import {
  user,
  isMobile,
  courseId,
  sectionId,
  fileId,
  path
} from './global'

Vue.use(Vuex);

const store = new Vuex.Store({
  state: {
    user, // 用户对象
    isMobile, // 是否为手机端
    courseId, // 课程Id
    sectionId, // 章节id
    fileId, // 预览文件的id
    path
  },
  mutations: {
    // 设置user
    setUser(state, newUser) {
      state.user.username = newUser.username;
      state.user.userIcon = newUser.userIcon;
    },
    // 更改浏览模式
    changeMode(state, isMobile) {
      state.isMobile = isMobile;
    },
    setCourseId(state, cid) {
      state.courseId = cid;
    },
    setSectionId(state, sid) {
      state.sectionId = sid;
    },
    setFid(state, fid) {
      state.fileId = fid;
    },
    setPath(state, path) {
      state.path = path;
    }
  }
});

export default store;