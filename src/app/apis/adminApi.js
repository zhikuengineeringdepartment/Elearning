import Http from "../modules/Http";

let http = new Http();

// 查询预览的知识见解
const queryPreviewKnowledge = params => {
  return http.uploadFileInstance().post("/backstage/course/preview", params);
};

// 更新文件状态
const updateFileStatus = params => {
  return http.post("/admin/modifyFileStatus", params);
};

// 获取文件列表
const queryFileList = params => {
  return http.get("/admin/getUncheckFiles", params);
};

export { queryPreviewKnowledge, updateFileStatus, queryFileList };
