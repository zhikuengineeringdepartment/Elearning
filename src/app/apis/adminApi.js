import Http from "../modules/Http";

let http = new Http();

/**查询预览的知识见解*/
const queryPreviewKnowledge = params => http.uploadFileInstance().post("/backstage/course/preview", params);

/**更新文件状态*/
const updateFileStatus = (params, fn) => http.post("/admin/modifyFileStatus", params, fn);

/**获取文件列表*/
const queryFileList = (params, fn) => http.get("/admin/getUncheckFiles", params, fn);

export {queryPreviewKnowledge, updateFileStatus, queryFileList};
