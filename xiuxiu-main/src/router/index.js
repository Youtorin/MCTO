import Vue from 'vue'
import VueRouter from "vue-router";

Vue.use(VueRouter)
import Home from '@/modules/Home'
import Login from '@/modules/Login'
import Register from '@/modules/Register'

import Detail from '@/modules/Detail'
import MyPay from '@/modules/MyPay'
import ShopCart from '@/modules/ShopCart'
import EditOrder from '@/modules/ShopCart/editOrder'
import Comments from "@/modules/Comments"

const router = new VueRouter({
    routes: [
        {
            path: '/',
            redirect: '/home'
        },
        {
            path: '/home',
            component: Home,
            meta: { show: true, footshow: true }
        },
        {
            path: '/comments',
            component: Comments,
            meta: { show: true, footshow: true }
        },
        {
            path: '/detail',
            component: Detail,
            meta: { show: true, footshow: true }
        },
        {
            path: '/mypay',
            name: 'MyPay',
            component: MyPay,
            meta: { show: true, footshow: true }
        },
        {
            path: '/shopcart',
            name: 'shopcart',
            component: ShopCart,
            meta: { show: true, footshow: true }
        },
        {
            path: '/editOrder',
            name: 'editOrder',
            component: EditOrder,
            meta: { show: false, footshow: false }
        },
        {
            path: '/Login',
            component: Login,
            meta: { show: false, footshow: true }
        },
        {
            path: '/Register',
            component: Register,
            meta: { show: false, footshow: true }
        },

    ],
    scrollBehavior() {
        return { x: 0, y: 0 }
    }

})

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}

router.beforeEach(async (to, from, next) => {
    // 用户登录了之后不能调到登录界面
    let token = localStorage.getItem('TOKEN')
    if (token) {
        // 是否已经登录
        next()

    } else {

        if (to.path == '/login' || to.path == '/register') {
            next();
        } else {
            next({ path: '/login' })
        }
    }
})




export default router