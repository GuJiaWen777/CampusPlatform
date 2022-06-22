import Vue from 'vue'
import App from './App.vue'
import store from "./store";
import ElementUI from 'element-ui';
import locale from 'element-ui/lib/locale/lang/en'
import 'element-ui/lib/theme-chalk/index.css';
import router from './router'
import './assets/gloabel.css'
import request from "./utils/request";
import moment from "moment";

Vue.prototype.$moment = moment;

Vue.config.productionTip = false

Vue.use(ElementUI,{size:"small"},{ locale });

Vue.prototype.request = request


new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

