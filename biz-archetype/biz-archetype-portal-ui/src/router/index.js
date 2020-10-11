import Vue from "vue";
import Router from "vue-router";
import {getToken, refreshToken} from "@/api/authUtil"

Vue.use(Router);

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

export const constantRoutes = routerModule

const createRouter = () =>
    new Router({
        scrollBehavior: () => ({y: 0}),
        routes: constantRoutes,
    });

const router = createRouter();

export function resetRouter() {
    const newRouter = createRouter();
    router.matcher = newRouter.matcher;
}

router.beforeEach(async (to, from, next) => {
    await getToken()
    next()
    await refreshToken()
})
export default router;
