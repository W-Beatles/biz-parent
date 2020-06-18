import router from './router'
import store from './store'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style

NProgress.configure({ showSpinner: false }) // NProgress Configuration

router.beforeEach(async (to, from, next) => {
  // document.title = getPageTitle(to.meta.title)

  NProgress.start()
  console.log('router', router)
  await store.dispatch('permission/generateRoutes', ['admin'])
// hack method to ensure that addRoutes is complete
// set the replace: true, so the navigation will not leave a history record
//   next({ ...to, replace: true })
  next()
  NProgress.done()
})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
