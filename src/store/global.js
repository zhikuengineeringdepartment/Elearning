// 本文件定义了一些全局js变量和函数
import defaultUserIcon from '../assets/defaultUserIcon'

// 用户数据，如果未登录则显示默认头像
let user = {
  username: "",
  userIcon: defaultUserIcon
};

let isLogin = false; // 是否登录
let tabIndex = 0;
let isMobile = false; // 是否为手机端
let courseId = -1;
let sectionId = -1;
let fileId = -1; // 预览文件的id

export {
  user,
  isLogin,
  tabIndex,
  isMobile,
  courseId,
  sectionId,
  fileId
}
