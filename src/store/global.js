// 本文件定义了一些全局js变量和函数
import defaultUserIcon from "../assets/defaultUserIcon";

// 用户数据，如果未登录则显示默认头像
let user = {
  username: "",
  userIcon: defaultUserIcon
};

//课程信息，courseId为课程号，versionId为版本号，sectionId为章节号
let course = {
  courseId: -1,
  versionId: "V1.0",
  sectionId: -1
};

let isMobile = false; // 是否为手机端
let isAdministrator = false;
let isLogin = false;
let fileId = -1; // 预览文件的id
let path = "/"; // 当前路径

export { user, course, isMobile, isAdministrator, isLogin, fileId, path };
