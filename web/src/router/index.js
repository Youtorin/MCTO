import { createRouter, createWebHashHistory } from "vue-router";
import Home from "@/modules/dashboard.vue";

const routes = [
  {
    path: "/",
    redirect: "/api/dashboard/Home",
  },
  {
    path: "/api/dashboard",
    name: "dashboard",
    component: Home,
    children: [
      {
        path: "home",
        name: "home",
        meta: {
          title: "商家首页",
        },
        component: () => import("@/modules/home/home.vue"),
      },
      {
        path: "orderlist",
        name: "orderlist",
        meta: {
          title: "订单列表",
        },
        component: () => import("@/modules/order/orderlist.vue"),
      },
      {
        path: "order",
        name: "order",
        meta: {
          title: "待办订单管理",
        },
        component: () => import("@/modules/order/order.vue"),
      },
      {
        path: "retrievalorder",
        name: "retrievalorder",
        meta: {
          title: "出库订单管理",
        },
        component: () => import("@/modules/order/retrievalorder.vue"),
      },
      {
        path: "refundorder",
        name: "refundorder",
        meta: {
          title: "退款订单管理",
        },
        component: () => import("@/modules/order/refundorder.vue"),
      },
      {
        path: "comment",
        name: "comment",
        meta: {
          title: "项目评价管理",
        },
        component: () => import("@/modules/comment/comment.vue"),
      },
      {
        path: "shopComment",
        name: "shopComment",
        meta: {
          title: "门店评价管理",
        },
        component: () => import("@/modules/comment/shopComment.vue"),
      },
      {
        path: "commodity",
        name: "commodity",
        meta: {
          title: "商品管理",
        },
        component: () => import("@/modules/commodity/commodity.vue"),
      },
      {
        path: "customer",
        name: "customer",
        meta: {
          title: "顾客管理",
        },
        component: () => import("@/modules/customer/customer.vue"),
      },
      {
        path: "distribution",
        name: "distribution",
        meta: {
          title: "配送状态",
        },
        component: () => import("@/modules/distribution/distribution.vue"),
      },
      {
        path: "finance",
        name: "finance",
        meta: {
          title: "财务状态",
        },
        component: () => import("@/modules/finance/finance.vue"),
      },
      {
        path: "analysis",
        name: "analysis",
        meta: {
          title: "经营分析",
        },
        component: () => import("@/modules/analysis/analysis.vue"),
      },
      {
        path: "shop",
        name: "shop",
        meta: {
          title: "门店管理",
        },
        component: () => import("@/modules/shop/shop.vue"),
      },
      {
        path: "user",
        name: "user",
        meta: {
          title: "个人中心",
        },
        component: () => import("@/modules/user/user.vue"),
      },
    ],
  },
  {
    path: "/login",
    name: "Login",
    meta: {
      title: "登录",
    },
    component: () => import("@/shared/Login.vue"),
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  document.title = `${to.meta.title} | vue-manage-system`;
  const role = localStorage.getItem("currentUser");
  if (!role && to.path !== "/login") {
    next("/login");
  } else if (to.meta.permission) {
    role === "admin" ? next() : next("/403");
  } else {
    next();
  }
});

export default router;
