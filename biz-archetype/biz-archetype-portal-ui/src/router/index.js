import Vue from "vue";
import Router from "vue-router";
import Request from "@/api/Model";

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
    const code = new URLSearchParams(window.location.search).get('code')
    let {origin, hash} = window.location
    if (code && !window.location.hash) {
        const urlStr = `grant_type=authorization_code&code=${code}&client_id=h5&client_secret=123456&redirect_uri=http://admin.gezimm.com:9527/index`
        const {access_token} = await Request.requestAccess(`/oauth/token?${urlStr}`, 'post')
        localStorage.setItem('Token', `Bearer ${access_token}`)
        window.location.href = `${origin}/${hash}`
    }
    next()
})
export default router;
