import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

import Layout from "@/layout";

/**
 *  无需手动import所有的 router 模块，自动加载./modules下所有的模块
 */
const modulesRouterFiles = require.context("./modules", true, /\.js$/);
const routerModule = modulesRouterFiles
  .keys()
  .reduce((routerModule, modulePath) => {
    const value = modulesRouterFiles(modulePath);
    return routerModule.concat(value.default);
  }, []);

export const constantRoutes = [
  {
    path: "/",
    component: Layout,
    redirect: "/projectFrame",
    children: [
      {
        path: "projectFrame",
        component: () => import("@/views/projectFrame/index"),
        name: "ProjectFrame",
        meta: { title: "ProjectFrame", icon: "projectFrame", affix: true },
      },
    ],
  },
]
    // .concat(routerModule);

const createRouter = () =>
  new Router({
    // mode: 'history', // require service support
    scrollBehavior: () => ({ y: 0 }),
    routes: constantRoutes,
  });

const router = createRouter();

export function resetRouter() {
  const newRouter = createRouter();
  router.matcher = newRouter.matcher; // reset router
}

export default router;
