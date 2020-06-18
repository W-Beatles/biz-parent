import Vue from 'vue'
import App from './App.vue'
import store from './store'
import router from './router'

import Cookies from 'js-cookie'
import 'normalize.css/normalize.css' // a modern alternative to CSS resets

import './icons' // icon
import Element from 'element-ui'
import './styles/element-variables.styl'

import '@/styles/index.styl' // global css

import './permission' // permission control
import './utils/error-log' // error log

import * as filters from './filters' // global filters
Vue.use(Element, {
  size: Cookies.get('size') || 'medium' // set element-ui default size
})

// register global utility filters
Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key])
})

Vue.config.productionTip = false

// 引入插件
import vpay from './lib/vpay'
// 使用插件
Vue.use(vpay)

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
