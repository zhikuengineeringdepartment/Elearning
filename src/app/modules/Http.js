import Vue from "vue";
import axios from "axios";
import {getLocation} from "./functions";
// import {Message} from "element-ui";
import NProgress from "nprogress";
import "nprogress/nprogress.css";
import Qs from "qs";

const context = new Vue();
const DEFALUT_HEADER = "application/x-www-form-urlencoded";
const FILE_HEADER = "multipart/form-data";
const LOCATION = getLocation(window.location.href)
const PRODUCTION_URL = LOCATION.protocol + '//' + LOCATION.host
const DEVELOPMENT_URL = "/api";

export default class Http {
    // #axiosInstance;
    constructor(header = DEFALUT_HEADER) {
        this._axiosInstance = this.createInstance(header);
    }

    createInstance(header) {
        const axiosInstance = axios.create({
            // config里面有这个transformRquest，这个选项会在发送参数前进行处理，这时候我们通过Qs.stringify转换为表单查询参数
            transformRequest: [
                function (data) {
                    data = Qs.stringify(data);
                    return data;
                }
            ],

            // 设置Content-Type
            headers: {"Content-Type": header},

            timeout: 1000 * 15,
            // 可携带cookies
            withCredentials: true,
            baseURL:
                process.env.NODE_ENV === "production" ? PRODUCTION_URL : DEVELOPMENT_URL // 正式环境与开发环境的url
        });

        //设置拦截器
        axiosInstance.interceptors.response.use(
            response => response.data.code === 200 ? Promise.resolve(response.data) : Promise.reject(response.data),
            error => {
                const {response} = error
                if (response) {
                    return Promise.reject(response.data)
                } else context.$message.error("网络似乎出了一些状况")
            }
        )
        return axiosInstance
    }

    uploadFileInstance() {
        return axios.create({
            // 设置Content-Type
            headers: {"Content-Type": FILE_HEADER},

            // 可携带cookies
            withCredentials: true,
            baseURL:
                process.env.NODE_ENV === "production" ? PRODUCTION_URL : DEVELOPMENT_URL // 正式环境与开发环境的url
        });
    }

    /**
     * 发送get请求
     * @param url
     * @param data
     * @param fn
     */
    get(url, data, fn) {
        NProgress.start();

        return this.axiosInstance
            .get(url, {params: data})
            .then(response => {
                NProgress.done();
                return fn(response)
                // return response;
            }, error => {
                NProgress.done();
                context.$message({
                    showClose: true,
                    message: error.message,
                    type: "error"
                })
                console.log(error)
                throw new Error("出错啦")
            })
            .catch(err => {
                NProgress.done();
                console.log(err);
            });
    }

    /**
     * 发送post请求
     * @param url
     * @param data
     * @param fn
     */
    post(url, data, fn) {
        NProgress.start();

        return this.axiosInstance
            .post(url, data, {
                transformRequest: [
                    data => {
                        let ret = "";
                        for (let it in data) {
                            ret +=
                                encodeURIComponent(it) +
                                "=" +
                                encodeURIComponent(data[it]) +
                                "&";
                        }
                        return ret;
                    }
                ],
                withCredentials: true
            })
            .then(response => {
                NProgress.done();
                return fn(response)
            }, error => {
                NProgress.done();
                context.$message({
                    showClose: true,
                    message: error.message,
                    type: "error"
                })
                console.log(error)
                throw new Error("出错啦")
            })
            .catch(err => {
                NProgress.done();
                console.log(err);
            });
    }

    get axiosInstance() {
        return this._axiosInstance;
    }
}
