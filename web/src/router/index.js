import { createRouter, createWebHashHistory } from "vue-router";
import Home from "@/modules/Home.vue"

const routes = [
    {
        path: '/',
        redirect: '/dashboard/Home'
    }, {
        path: "/dashboard",
        name: "dashboard",
        component: Home,
        children: [
            {
                path: "home",
                name: "home",
                meta: {
                    title: '商家首页'
                },
                component: () => import("@/modules/dashboard.vue")
            }, {
                path: "order",
                name: "order",
                meta: {
                    title: '订单管理'
                },
                component: () => import("@/modules/order.vue")
            }, {
                path: "commodity",
                name: "commodity",
                meta: {
                    title: '商品管理'
                },
                component: () => import("@/modules/commodity.vue")
            }, {
                path: "customer",
                name: "customer",
                meta: {
                    title: '顾客管理'
                },
                component: () => import("@/modules/customer.vue")
            }, {
                path: "distribution",
                name: "distribution",
                meta: {
                    title: '配送状态'
                },
                component: () => import("@/modules/distribution.vue")
            }, {
                path: "finance",
                name: "finance",
                meta: {
                    title: '财务状态'
                },
                component: () => import("@/modules/finance.vue")
            }, {
                path: "analysis",
                name: "analysis",
                meta: {
                    title: '经营分析'
                },
                component: () => import("@/modules/analysis.vue")
            }, {
                path: 'user',
                name: 'user',
                meta: {
                    title: '个人中心'
                },
                component: () => import('@/modules/user.vue')
            }, {
                path: 'setting',
                name: 'setting',
                meta: {
                    title: '设置'
                },
                component: () => import('@/modules/setting.vue')
            }
        ]
    }, {
        path: "/login",
        name: "Login",
        meta: {
            title: '登录'
        },
        component: () => import("@/shared/Login.vue")
    }
];

const router = createRouter({
    history: createWebHashHistory(),
    routes
});

router.beforeEach((to, from, next) => {
    document.title = `${to.meta.title} | vue-manage-system`;
    const role = localStorage.getItem('ms_username');
    if (!role && to.path !== '/login') {
        next('/login');
    } else if (to.meta.permission) {
        // 如果是管理员权限则可进入，这里只是简单的模拟管理员权限而已
        role === 'admin'
            ? next()
            : next('/403');
    } else {
        next();
    }
});

export default router;