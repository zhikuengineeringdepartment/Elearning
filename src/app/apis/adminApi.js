import Http from "../modules/Http";

let http = new Http("multipart/form-data");

// 查询预览的知识见解
const queryPreviewKnowledge = params => {
  return http.post("/backstage/course/preview", params);
};

export { queryPreviewKnowledge };
