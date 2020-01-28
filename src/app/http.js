import axios from "axios";
import NProgress from "nprogress";
import "nprogress/nprogress.css";
import Qs from "qs";

const DEFALUT_HEADER = "application/x-www-form-urlencoded";
const FILE_HEADER = "multipart/form-data";
const PRODUCTION_URL = "http://sharingideas.cn:10000/";
const DEVELOPMENT_URL = "/api";

export default class HTTP {
  constructor(context) {
    this.context = context;
    this.axiosInstance = axios.create({
      // config里面有这个transformRquest，这个选项会在发送参数前进行处理，这时候我们通过Qs.stringify转换为表单查询参数
      transformRequest: [
        function(data) {
          data = Qs.stringify(data);
          return data;
        }
      ],

      // 设置Content-Type
      headers: { "Content-Type": DEFALUT_HEADER },

      // 可携带cookies
      withCredentials: true,
      baseURL:
        process.env.NODE_ENV === "production" ? PRODUCTION_URL : DEVELOPMENT_URL // 正式环境与开发环境的url
    });
  }

  changeInstance() {
    this.axiosInstance = axios.create({
      headers: { "Content-Type": FILE_HEADER },
      withCredentials: true,
      baseURL:
        process.env.NODE_ENV === "production" ? PRODUCTION_URL : DEVELOPMENT_URL
    });
  }

  /**
   * 发送get请求
   * @param url 请求地址（不包括服务器地址）
   * @param data 要携带的数据
   * @param fn 回调函数
   */
  get(url, data, fn) {
    NProgress.start();

    let _this = this;
    _this.axiosInstance
      .get(url, { params: data })
      .then(response => {
        NProgress.done();

        if (response.data.code === 200) {
          fn(res.data);
        } else {
          _this.context.$message({
            showClose: true,
            message: response.data.message,
            type: "error"
          });
        }
      })
      .catch(err => {
        NProgress.done();
        console.log(err);
      });
  }

  /**
   * 发送post请求
   * @param url 请求地址（不包括服务器地址）
   * @param data 要携带的数据
   * @param fn 回调函数
   */
  post(url, data, fn) {
    NProgress.start();

    let _this = this;
    _this.axiosInstance
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
          fn(res.data);
        } else {
          _this.context.$message({
            showClose: true,
            message: response.data.message,
            type: "error"
          });
        }
      })
      .catch(err => {
        NProgress.done();
        console.log(err);
      });
  }
}
