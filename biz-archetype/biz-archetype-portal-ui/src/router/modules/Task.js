import Layout from "@/layout";

const DevUtilsRoutes = [{
    path: '/',
    component: Layout,
    redirect: 'taskSearch',
    name: 'TaskSearch',
    meta: {
        title: '项目管理',
        icon: 'chart'
    },
    children: [
        {
            path: "/appID",
            component: () => import("@/views/projectFrame/index"),
            name: "AppID",
            meta: {title: "AppID管理", icon: "projectFrame", affix: true},
        }, {
            path: "/taskSearch",
            component: () => import("@/views/projectFrame/index"),
            name: "骨架模块",
            meta: {title: "骨架生成器", icon: "projectFrame", affix: true},
        }
    ]
}]
export default DevUtilsRoutes
