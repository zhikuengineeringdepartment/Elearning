import Http from "../modules/Http";

let http = new Http();

/**大页面路由跳转时发送请求，用于后端统计页面停留时间 */
// 补上接口名,然后在组件MainHeader 里面调用
const leavePage = (params, fn) => http.post("/dataStatistics/access", params, fn);

// 请求数据
const getData = (params, fn) => http.get("/admin/dataStatistics/getFlow", params, fn)

export {
    leavePage,
    getData
}