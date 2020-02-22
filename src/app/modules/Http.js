import Vue from "vue";
import axios from "axios";
// import {Message} from "element-ui";
import NProgress from "nprogress";
import "nprogress/nprogress.css";
import Qs from "qs";

const DEFALUT_HEADER = "application/x-www-form-urlencoded";
const FILE_HEADER = "multipart/form-data";
const PRODUCTION_URL = "http://sharingideas.cn:10000/";
const DEVELOPMENT_URL = "/api";
const context = new Vue();

export default class Http {
    // #axiosInstance;
    constructor(header = DEFALUT_HEADER) {
        this._axiosInstance = this.createInstance(header);
    }

    createInstance(header) {
        return axios.create({
            // config里面有这个transformRquest，这个选项会在发送参数前进行处理，这时候我们通过Qs.stringify转换为表单查询参数
            transformRequest: [
                function (data) {
                    data = Qs.stringify(data);
                    return data;
                }
            ],

            // 设置Content-Type
            headers: {"Content-Type": header},

            // 可携带cookies
            withCredentials: true,
            baseURL:
                process.env.NODE_ENV === "production" ? PRODUCTION_URL : DEVELOPMENT_URL // 正式环境与开发环境的url
        });
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
     * @returns {Promise<AxiosResponse<T>>}
     */
    get(url, data) {
        NProgress.start();

        return this.axiosInstance
            .get(url, {params: data})
            .then(response => {
                NProgress.done();

                if (response.data.code === 200) {
                    return response.data;
                } else {
                    context.$message({
                        showClose: true,
                        message: response.data.message,
                        type: "error"
                    });
                    return response.data;
                }
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
     * @returns {Promise<AxiosResponse<T>>}
     */
    post(url, data) {
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

                if (response.data.code === 200) {
                    return response.data;
                } else {
                    context.$message({
                        showClose: true,
                        message: response.data.message,
                        type: "error"
                    });
                    return response.data;
                }
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
