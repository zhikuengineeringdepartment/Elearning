import Vue from 'vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue';
import store from './store/store';
import router from './router/router';
import axios from 'axios';
import Vueaxios from 'vue-axios';
import Qs from 'qs';

window.axios = axios;
axios.defaults.withCredentials = true;

// 公共方法
import './app/funcitons'

// import {throttle} from './tools'

// 创建一个axios实例
const axiosInstance = axios.create({
  // config里面有这个transformRquest，这个选项会在发送参数前进行处理，这时候我们通过Qs.stringify转换为表单查询参数
  transformRequest: [function (data) {
    data = Qs.stringify(data);
    return data;
  }],
  
  // 设置Content-Type
  headers: {'Content-Type': 'application/x-www-form-urlencoded'},
  
  // 可携带cookies
  withCredentials: true,
  
  baseURL: 'http://sharingideas.cn:18888'
});

Vue.use(ElementUI);
Vue.use(Vueaxios, axiosInstance);

Vue.config.productionTip = false;

// 页面初始化时先判断是手机端还是电脑端
store.commit('changeMode', window.innerHeight > window.innerWidth);


// 修改窗口大小时的操作，用于判断手机端或电脑端，使用节流函数
window.onresize = () => {
  store.commit('changeMode', window.innerHeight > window.innerWidth);
};

// 这个版本是使用节流函数的，不过貌似在这里使用节流函数效果更差
// window.onresize = throttle(() => {
//   store.commit('changeMode', window.innerHeight > window.innerWidth);
// }, 1000);

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app');
