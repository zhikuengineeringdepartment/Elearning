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

export {
  routerChange,
  throttle
}
