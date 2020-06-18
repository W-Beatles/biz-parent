import Vue from 'vue'
import App from './App.vue'
import store from './store'
import router from './router'

import 'normalize.css/normalize.css' // a modern alternative to CSS resets

import './icons' // icon
import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

import './styles/element-variables.styl'

import '@/styles/index.styl' // global css

import './permission' // permission control
import './utils/error-log' // error log

import MButton from '@/components/BaseCmp/button'

Vue.use(MButton)

import * as filters from './filters' // global filters
Vue.use(Element, {size: 'small', zIndex: 1001})

import Enum from '@/enum/index.js'

Vue.prototype.Enum = Enum

// register global utility filters
Object.keys(filters).forEach(key => {
    Vue.filter(key, filters[key])
})

Vue.config.productionTip = false

new Vue({
    el: '#app',
    router,
    store,
    render: h => h(App)
})
