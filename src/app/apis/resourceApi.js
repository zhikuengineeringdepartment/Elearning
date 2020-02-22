import Http from "../modules/Http";

let http = new Http();

/**
 * 上传文件
 * @param params
 * @returns {Promise<AxiosResponse<T>>}
 */
const uploadFile = params => {
    return http.uploadFileInstance().post("file/upload", params);
};

/**
 * 获取文件列表
 * @param params
 * @returns {Promise<* | void>}
 */
const queryFileList = (params) => {
    return http.get('file/getFileList', params)
}

export {uploadFile, queryFileList};
