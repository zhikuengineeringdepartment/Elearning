// 使用vuex进行全局的数据状态管理
import Vue from "vue";
import Vuex from "vuex";
import {
  user,
  course,
  isMobile,
  isAdministrator,
  isLogin,
  fileId,
  path
} from "./global";

Vue.use(Vuex);

const store = new Vuex.Store({
  state: {
    user, // 用户对象
    course, //课程对象
    isMobile, // 是否为手机端
    isLogin, // 是否登陆
    isAdministrator, // 是否为管理员
    fileId, // 预览文件的id
    path
  },
  mutations: {
    // 设置user
    setUser(state, newUser) {
      state.user.username = newUser.username;
      state.user.userIcon = newUser.userIcon;
    },
    //设置课程
    setCourse(state, course) {
      state.course.courseId = course.cid;
      state.course.versionId = course.vid;
    },
    setSectionId(state, sid) {
      state.course.sectionId = sid;
    },
    // 更改浏览模式
    changeMode(state, isMobile) {
      state.isMobile = isMobile;
    },
    setAuthority(state, isAdministrator) {
      state.isAdministrator = isAdministrator;
    },
    setLoginStatus(state, isLogin) {
      state.isLogin = isLogin;
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
