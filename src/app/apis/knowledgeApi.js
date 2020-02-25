import Http from "../modules/Http";

let http = new Http();

/**查询知识见解列表*/
const queryCourseList = (params, fn) => http.get("course/getAllCourse", params, fn);

/**查询课程目录*/
const queryCourseView = (params, fn) => http.get("course/getCourseDetails", params, fn);

/**查询小节内容*/
const querySectionView = (params, fn) => http.get("section/getSection", params, fn);

/**查询csdn推荐*/
const queryCsdn = (params, fn) => http.get("section/getCSDN2", params, fn);

/**查询用户笔记*/
const queryNoteView = (params, fn) => http.post("paragraph/getNoteBySid", params, fn);

/**查询用户在某一节下面的所有收藏段落*/
const queryColParas = (params, fn) => http.post("paragraph/getColParagraphBySid", params, fn);

export {
    queryCourseList,
    queryCourseView,
    querySectionView,
    queryCsdn,
    queryNoteView,
    queryColParas
};
