import Http from "../modules/Http";

let http = new Http();

// 查询知识见解列表
const queryCourseList = () => {
  return http.get("course/getAllCourse", { uid: 0 });
};

// 查询课程目录
const queryCourseView = params => {
  return http.get("course/getCourseDetails", params);
};

// 查询小节内容
const querySectionView = params => {
  return http.get("section/getSection", params);
};

// 查询csdn推荐
const queryCsdn = params => {
  return http.get("section/getCSDN2", params);
};

// 查询用户笔记
const queryNoteView = params => {
  return http.post("paragraph/getNoteBySid", params);
};

// 查询用户在某一节下面的所有收藏段落
const queryColParas = params => {
  return http.post("paragraph/getColParagraphBySid", params);
};

export {
  queryCourseList,
  queryCourseView,
  querySectionView,
  queryCsdn,
  queryNoteView,
  queryColParas
};
