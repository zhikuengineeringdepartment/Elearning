//本文件定义了一些工具方法。

// 路由跳转的定制方法
const routerChange = (pageUrl, _this) => {
  _this.$router.push(pageUrl);
  _this.$emit('change', pageUrl);
  console.log('跳转到' + pageUrl);
};

// 生成节流函数
const throttle = (fn, duration) => {
  let preTime = Date.now();
  
  return () => {
    let doTime = Date.now();
    if (doTime - preTime > duration) {
      console.log('节流函数执行');
      fn();
      preTime = doTime;
    }
  }
};

// 匹配根域名
const getLocation = (href) => {
  const match = href.match(/^(https?\:)\/\/(([^:\/?#]*)(?:\:([0-9]+))?)([\/]{0,1}[^?#]*)(\?[^#]*|)(#.*|)$/);
  return match && {
    protocol: match[1],
    host: match[2],
    hostname: match[3],
    port: match[4],
    pathname: match[5],
    search: match[6],
    hash: match[7]
  }
};

export {
  routerChange,
  throttle,
  getLocation
}
