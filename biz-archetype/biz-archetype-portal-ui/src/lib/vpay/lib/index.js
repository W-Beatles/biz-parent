// pay.vue写我们的组件
import vpay from './pay'

// 定义我们的插件
const myPlugin = {
  // 该插件有一个install方法
  // 方法的第一个参数是传入的Vue，第二个参数可以插件的自定义参数
  install (Vue) {
    // 将其注册为vue的组件，'vpay'是组件名，keyboard是我们开发的组件
    Vue.component('vpay', vpay)
  }
}

// 最后将插件导出，并在main.js中通过Vue.use()即可使用插件
export default myPlugin
