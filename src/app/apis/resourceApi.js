import Http from "../modules/Http";

let http = new Http();

/**上传文件*/
const uploadFile = params => http.uploadFileInstance().post("file/upload", params);

/**获取文件列表*/
const queryFileList = (params, fn) => http.get('file/getFileList', params, fn)

export {uploadFile, queryFileList};
