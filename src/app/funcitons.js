// 放置全局自定义的js行数

/**
 * Nprogress.js
 * http://ricostacruz.com/nprogress/
 */
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

/**
 * 发送post请求
 * @param url 请求地址（不包括服务器地址）
 * @param data 要携带的数据
 * @param fn 回调函数
 */
window.fPost = function (url, data, fn) {
  NProgress.start();

  axios.post(url, data, {
      method: 'post',
      baseURL: process.env.API_ROOT,
      withCredentials: true,
    })
    .then(function (res) {
      if (res.data.code == 2) {
        console.log(res.data.msg);
        return false;

      }
      NProgress.done();
      // if (typeof res.data.code === 'undefined' || typeof res.data.msg === 'undefined' || typeof res.data.data === 'undefined') {
      //   console.log("api（" + url + "）返回的数据格式有误");
      //   return false;
      // }


      fn(res.data);
    })
    .catch(function (error) {
      NProgress.done();
      fn({
        code: 1,
        data: [],
        msg: "api 请求失败"
      });
      console.log(error);
    });
};

/**
 * 发送get请求
 * @param url 请求地址（不包括服务器地址）
 * @param data 要携带的数据
 * @param fn 回调函数
 */
window.fGet = function (url, data, fn) {
  NProgress.start();

  axios.get(url, {
      params: data
    }, {
      method: 'get',
      baseURL: process.env.API_ROOT,
      withCredentials: true,
    })
    .then(function (res) {
      if (res.data.code == 2) {
        console.log(res.data.msg);
        return false;
      }
      NProgress.done();
      // if (typeof res.data.code === 'undefined' || typeof res.data.msg === 'undefined' || typeof res.data.data === 'undefined') {
      //   console.log("api（" + url + "）返回的数据格式有误");
      //   return false;
      // }


      fn(res.data);
    })
    .catch(function (error) {
      NProgress.done();
      fn({
        code: 1,
        data: [],
        msg: "api 请求失败"
      });
      console.log(error);
    });
};

/**
 * 控制台打印
 * @param data
 */
window.dump = function (data) {
  console.log(data);
};

/**
 * 设置cookie
 * @param cname 名称
 * @param cvalue 值
 * @param expire 过期时间
 */
window.setCookie = function (cname, cvalue, expire) {
  var d = new Date();
  d.setTime(d.getTime() + (expire * 1000));
  var expires = "expires=" + d.toUTCString();
  document.cookie = cname + "=" + cvalue + "; " + expires + ";path=/";
};

/**
 * 获取cookie
 * @param cname
 * @returns {string}
 */
window.getCookie = function (cname) {
  var name = cname + "=";
  var ca = document.cookie.split(';');
  for (var i = 0; i < ca.length; i++) {
    var c = ca[i];
    while (c.charAt(0) == ' ') c = c.substring(1);
    if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
  }
  return "";
};

/**
 * 清除cookie
 * @param name
 */
window.clearCookie = function (name) {
  setCookie(name, "", -1);
};

/**
 * 处理对象参数值，排除对象参数值为”“、null、undefined，并返回一个新对象
 **/
window.dealObjectValue = function (obj) {
  var param = {};
  if (obj === null || obj === undefined || obj === "") return param;
  for (let key in obj) {
    if (typeof obj[key] === "object") {
      param[key] = dealObjectValue(obj[key]);
    } else if (obj[key] !== null && obj[key] !== undefined && obj[key] !== "") {
      param[key] = obj[key];
    }
  }
  return param;
};

/**
 * 计算两个时间相差天数
 * @param sDate1 开始时间
 * @param sDate2 结束时间
 */
window.datedifference = function (sDate1, sDate2) {
  var dateSpan,
    tempDate,
    iDays;
  sDate1 = Date.parse(sDate1);
  sDate2 = Date.parse(sDate2);
  dateSpan = sDate2 - sDate1;
  dateSpan = Math.abs(dateSpan);
  iDays = Math.floor(dateSpan / (24 * 3600 * 1000));
  return iDays
};

/**
 * 获取当前时间，格式YYYY-MM-DD
 * @param date 初始化时间对象
 */
window.getToday = function (date) {
  let seperator1 = "-";
  let year = date.getFullYear();
  let month = date.getMonth() + 1;
  let strDate = date.getDate();
  if (month >= 1 && month <= 9) {
    month = "0" + month;
  }
  if (strDate >= 0 && strDate <= 9) {
    strDate = "0" + strDate;
  }
  let currentdate = year + seperator1 + month + seperator1 + strDate;
  return currentdate;
};

/**
 * 时间戳转为日期，格式YYYY-MM-DD
 * @param timeStamp 时间戳
 */
window.getDate = function formatDateTime(timeStamp) {
  var date = new Date();
  date.setTime(timeStamp * 1000);
  var y = date.getFullYear();
  var m = date.getMonth() + 1;
  m = m < 10 ? ('0' + m) : m;
  var d = date.getDate();
  d = d < 10 ? ('0' + d) : d;
  return y + '-' + m + '-' + d;
};
/**
 * 时间戳转为日期，格式YYYY-MM-DD HH:ii:ss
 * @param timestamp
 * @returns {string}
 */
window.timestampToTime = function (timestamp) {
  var date = new Date(timestamp * 1000); //时间戳为10位需*1000，时间戳为13位的话不需乘1000
  let Y = date.getFullYear() + '-';
  let M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
  let D = date.getDate();
  let h = date.getHours();
  let m = date.getMinutes();
  let s = date.getSeconds();

  D = D < 10 ? ('0' + D + ' ') : D + ' ';
  h = h < 10 ? ('0' + h + ':') : h + ':';
  m = m < 10 ? ('0' + m + ':') : m + ':';
  s = s < 10 ? ('0' + s) : s;

  return Y + M + D + h + m + s;
};

/**
 * 转换html
 * @param str
 * @returns {*}
 */
window.htmlspecialchars_decode = function (str) {
  str = str.replace(/&amp;/g, '&');
  str = str.replace(/&lt;/g, '<');
  str = str.replace(/&gt;/g, '>');
  str = str.replace(/&quot;/g, "'");
  str = str.replace(/&#039;/g, "'");
  return str;
};

/**
 * 根据长度截取先使用字符串，超长部分追加…
 * str 对象字符串
 * len 目标字节长度
 *  返回值： 处理结果字符串
 */
window.cutString = function (str, len) {

  //length属性读出来的汉字长度为1

  if (str.length * 2 <= len) {
    return str;
  }
  var strlen = 0;
  var s = "";
  for (var i = 0; i < str.length; i++) {
    s = s + str.charAt(i);
    if (str.charCodeAt(i) > 128) {
      strlen = strlen + 2;
      if (strlen >= len) {
        return s.substring(0, s.length - 1) + "...";
      }
    } else {
      strlen = strlen + 1;
      if (strlen >= len) {
        return s.substring(0, s.length - 2) + "...";
      }
    }
  }

  return s;

};

/**
 * 去重复
 * @param arr
 * @returns {Array}
 */
window.hovercUnique = function (arr) {
  let hash = {};
  let result = [];
  for (let i = 0, len = arr.length; i < len; i++) {
    if (!hash[arr[i]]) {
      result.push(arr[i]);
      hash[arr[i]] = true;
    }
  }
  return result;
};

/**
 * 图片压缩
 * @param file 文件(类型是图片格式)，
 * @param maxLen 图片的最大边长
 * @param callBack 容器或者回调函数
 */
window.imgCompress = function (file, maxLen, callBack) {
  if (typeof (FileReader) === 'undefined' || typeof (Image) === 'undefined') {
    console.log("当前浏览器内核不支持base64图标压缩");
    //调用上传方式不压缩
    callBack(file);
  } else {
    try {

      let reader = new FileReader(); //读取客户端上的文件
      reader.readAsDataURL(file);

      reader.onload = function () {
        let img = new Image();

        img.src = reader.result; //reader读取的文件内容是base64,利用这个url就能实现上传前预览图片
        img.onload = function () {

          //生成比例
          let width = img.width,
            height = img.height;

          //计算缩放比例
          let rate = 1;
          if (width >= height) {
            if (width > maxLen) {
              rate = maxLen / width;
            }
          } else {
            if (height > maxLen) {
              rate = maxLen / height;
            }
          }
          img.width = width * rate;
          img.height = height * rate;

          //生成canvas
          let canvas = document.createElement("canvas");
          let ctx = canvas.getContext("2d");
          canvas.width = img.width;
          canvas.height = img.height;
          ctx.drawImage(img, 0, 0, img.width, img.height);
          let base64 = canvas.toDataURL('image/jpeg', 0.9);

          //base64转File
          var arr = base64.split(','),
            mime = arr[0].match(/:(.*?);/)[1],
            bstr = atob(arr[1]),
            n = bstr.length,
            u8arr = new Uint8Array(n);
          while (n--) {
            u8arr[n] = bstr.charCodeAt(n);
          }

          //判断压缩效果
          let newFile = new File([u8arr], file.name, {
            type: mime
          });
          if (newFile.size > file.size) {
            newFile = file;
          }

          callBack(newFile);
        };
      };
    } catch (e) {
      console.log("压缩失败!");
      //调用直接上传方式  不压缩
      callBack(file);
    }
  }
};

/**
 * 消息提示
 * 5秒后消失
 * @param msg 提示内容
 * @param type 0，成功，默认；1失败；2，警告
 */
window.$msg = function (msg = "操作成功", type = 0, context) {

  let map = {
    0: 'success',
    1: 'error',
    2: 'warning',
  };

  context.$message({
    showClose: true,
    message: msg,
    type: map[type],
    duration: 1000 * 5,
  });
};

/**
 * 确认框
 * @param msg 提示内容
 * @param fn 确认后的操作，默认不作操作
 */
window.$alert = function (msg = "操作成功", fn = () => {}) {
  window.$alert = function (msg = "操作成功", fn = () => {}) {
    window.vm.$alert(msg, '请注意', {
      confirmButtonText: '确定',
      callback: fn,
    });
  };

  /**
   * 选择框
   * @param msg 提示内容
   * @param okFn 确认后的操作，可为空
   * @param cancelFn 取消后的操作,可为空
   */
  window.$confirm = function (msg = "请选择", okFn = () => {}, cancelFn = () => {}) {
    vm.$confirm(msg, '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(okFn).catch(cancelFn);
  };

  /**
   * 输入框
   * @param msg 提示内容
   * @param okFn 确认后的操作，会注入输入的值
   * @param cancelFn 取消后的操作,可为空
   */
  window.$prompt = function (msg = "请输入", okFn, cancelFn = () => {}) {
    vm.$prompt(msg, '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
    }).then(({
      value
    }) => {
      okFn(value);
    }).catch(cancelFn);
  }
}
