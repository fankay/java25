// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
//导入elementUI
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
//导入axios
import axios from 'axios'


Vue.use(ElementUI);


Vue.prototype.$http=axios
//配置axios的默认属性
axios.defaults.baseURL="http://localhost:9090" //默认URL的前缀

Vue.config.productionTip = false

//路由的前置过滤，查看当前请求的路由是否需要认证，
//如果需要认证就从localStorage中获取Token，如果可以取到，就跳转
//如果token不存在，则跳转到登录路由
router.beforeEach((to,from,next)=>{
  if(to.meta.reqiredAuth) {
    //需要认证的路由
    var token = localStorage.getItem("jwtToken");
    if(!token) {
      router.push("/");
      return;
    }
  }
  next();
});

//axios 请求拦截器：将Token放入到HttpHeader中发送给服务端
axios.interceptors.request.use(config=>{
  var token = localStorage.getItem("jwtToken");
  if(token) {
    //将token放入header
    config.headers.Authorization = token;
  }
  return config;
},error=>{
  return Promise.reject(error);
});
//axios 响应拦截器：判断服务端返回的是200？401？...
axios.interceptors.response.use(response=>{
  return response;
},error=>{
  if(error.response) {
    if(error.response.status == 401) {
      //删除token
      localStorage.removeItem("jwtToken");
      router.push("/");
    }
  }
  return Promise.reject(error.response.data);
});






/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
