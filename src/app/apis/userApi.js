import Http from "../modules/Http";

let http = new Http();

/**登录*/
const login = (params, fn) => http.post("user/login", params, fn)

/**注册*/
const register = (params, fn) => http.post("/user/registe", params, fn)

/**查询已上传文件*/
const queryUploadRecords = (params, fn) => http.get("user/getUploadRecords", params, fn)

/**查询以下载文件*/
const queryDownloadRecords = (params, fn) => http.get("user/getDownloadRecords", params, fn)

/**发送验证码*/
const sendCode = (params, fn) => http.post("user/mail/sendCode", params, fn)

/**修改密码*/
const setPassword = (params, fn) => http.post("user/setPassword", params, fn)

export {login, register, queryUploadRecords, queryDownloadRecords, sendCode, setPassword}