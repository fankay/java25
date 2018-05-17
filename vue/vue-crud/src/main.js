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

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
