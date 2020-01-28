//本文件定义了一些工具方法。

import katex from "katex";
import axios from "axios";

// 路由跳转的定制方法
const routerChange = (pageUrl, _this) => {
  _this.$router.push(pageUrl);
  _this.$emit("change", pageUrl);
  console.log("跳转到" + pageUrl);
};

// 生成节流函数
const throttle = (fn, duration) => {
  let preTime = Date.now();
  return () => {
    let doTime = Date.now();
    if (doTime - preTime > duration) {
      console.log("节流函数执行");
      fn();
      preTime = doTime;
    }
  };
};

// 匹配根域名
const getLocation = href => {
  const match = href.match(
    /^(https?\:)\/\/(([^:\/?#]*)(?:\:([0-9]+))?)([\/]{0,1}[^?#]*)(\?[^#]*|)(#.*|)$/
  );
  return (
    match && {
      protocol: match[1],
      host: match[2],
      hostname: match[3],
      port: match[4],
      pathname: match[5],
      search: match[6],
      hash: match[7]
    }
  );
};

/**
 * @desc 设置cookie
 * @param cname 名称
 * @param cvalue 值
 * @param expire 过期时间
 */
const setCookie = function(cname, cvalue, expire) {
  let d = new Date();
  d.setTime(d.getTime() + expire * 1000);
  let expires = "expires=" + d.toUTCString();
  document.cookie = cname + "=" + cvalue + "; " + expires + ";path=/";
};

/**
 * @desc 获取cookie
 * @param cname
 * @returns {string}
 */ const getCookie = cname => {
  var name = cname + "=";
  var ca = document.cookie.split(";");
  for (var i = 0; i < ca.length; i++) {
    var c = ca[i];
    while (c.charAt(0) == " ") c = c.substring(1);
    if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
  }
  return "";
};

/**
 * @desc 清除cookie
 * @param name
 */
const clearCookie = function(name) {
  setCookie(name, "", -1);
};

/**
 * 翻译markdown字符串中的公式
 * @param str
 * @returns {void|string|*}
 */
const parse = str => {
  return parse$(parse$$(str));
};

/**
 * 编译markdown字符串中的多行公式
 * @param str
 * @returns {void | string | *}
 */
const parse$$ = str => {
  const strArray = str.split("");
  let dollarFlag = false; // 是否匹配到了$
  let mathStr = "";
  for (let a = 0; a < strArray.length - 1; a++) {
    let testStr = strArray[a] + strArray[a + 1];
    if (testStr === "$$" && !dollarFlag) {
      dollarFlag = true;
    } else if (testStr === "$$" && dollarFlag) {
      dollarFlag = false;
      mathStr = mathStr.substr(1, mathStr.length);
      let parseMathStr = parseMath(
        mathStr,
        mathStr.includes("\\tag{") ? true : false
      );
      str = str.replace("$$" + mathStr + "$$", "<p>" + parseMathStr + "</p>");
      mathStr = "";
    } else if (testStr !== "$$" && dollarFlag) {
      mathStr += strArray[a];
    }
  }

  return str;
};

/**
 * 编译markdown中的单行公式
 * @param str
 * @returns {void | string | *}
 */
const parse$ = str => {
  const strArray = str.split("");
  let dollarFlag = false; // 是否匹配到了$
  let mathStr = "";
  for (let a = 0; a < strArray.length; a++) {
    let testStr = strArray[a];
    if (testStr === "$" && !dollarFlag) {
      dollarFlag = true;
    } else if (testStr === "$" && dollarFlag) {
      dollarFlag = false;
      let parseMathStr = parseMath(
        mathStr,
        mathStr.includes("\\tag{") ? true : false
      );
      str = str.replace("$" + mathStr + "$", parseMathStr);
      mathStr = "";
    } else if (testStr !== "$" && dollarFlag) {
      mathStr += strArray[a];
    }
  }

  return str;
};

/**
 * 将公式字符串转换为HTML
 * @param mathStr
 * @returns {*}
 */
const parseMath = (mathStr, flag = false) => {
  return katex.renderToString(mathStr, {
    throwOnError: false,
    displayMode: flag,
    output: "html",
    strict: "ignore "
  });
};

/**
 *新建axios实例
 */
const getInstance = () => {
  const instance = axios.create({
    headers: {
      "Content-Type": "multipart/form-data"
    },
    withCredentials: true,
    baseURL:
      process.env.NODE_ENV === "production"
        ? "http://sharingideas.cn:10000/"
        : "/api"
  });
  return instance;
};

export {
  routerChange,
  throttle,
  getLocation,
  setCookie,
  getCookie,
  clearCookie,
  parse,
  getInstance
};
