<template>
  <div class="sidebar">
    <el-menu
      class="sidebar-el-menu"
      :default-active="onRoutes"
      :collapse="collapse"
      background-color="#324157"
      text-color="#bfcbd9"
      active-text-color="#20a0ff"
      unique-opened
      router
    >
      <template v-for="item in items">
        <template v-if="item.subs">
          <el-submenu :index="item.index" :key="item.index">
            <template #title>
              <i :class="item.icon"></i>
              <span>{{ item.title }}</span>
            </template>
            <template v-for="subItem in item.subs">
              <el-submenu
                v-if="subItem.subs"
                :index="subItem.index"
                :key="subItem.index"
              >
                <template #title>{{ subItem.title }}</template>
                <el-menu-item
                  v-for="(threeItem, i) in subItem.subs"
                  :key="i"
                  :index="threeItem.index"
                >
                  {{ threeItem.title }}</el-menu-item
                >
              </el-submenu>
              <el-menu-item v-else :index="subItem.index" :key="subItem.index"
                >{{ subItem.title }}
              </el-menu-item>
            </template>
          </el-submenu>
        </template>
        <template v-else>
          <el-menu-item :index="item.index" :key="item.index">
            <i :class="item.icon"></i>
            <template #title>{{ item.title }}</template>
          </el-menu-item>
        </template>
      </template>
    </el-menu>
  </div>
</template>

<script>
import { computed, watch } from "vue";
import { useStore } from "vuex";
import { useRoute } from "vue-router";
export default {
  setup() {
    const items = [
      {
        icon: "el-icon-lx-home",
        index: "/api/dashboard/home",
        title: "商家首页",
      },
      {
        icon: "el-icon-lx-sort",
        index: "order",
        title: "订单管理",
        subs: [
          {
            index: "/api/dashboard/orderlist",
            title: "订单列表",
          },
          {
            index: "/api/dashboard/order",
            title: "待办订单管理",
          },
          {
            index: "/api/dashboard/retrievalorder",
            title: "出库订单管理",
          },
          {
            index: "/api/dashboard/refundorder",
            title: "退款订单管理",
          },
        ],
      },
      {
        icon: "el-icon-lx-sort",
        index: "comment",
        title: "评价管理",
        subs: [
          {
            index: "/api/dashboard/shopComment",
            title: "门店评价",
          },
          {
            index: "/api/dashboard/comment",
            title: "项目评价",
          },
        ],
      },
      {
        icon: "el-icon-lx-cart",
        index: "/api/dashboard/commodity",
        title: "商品管理",
      },
      {
        icon: "el-icon-lx-profile",
        index: "/api/dashboard/customer",
        title: "顾客管理",
      },
      {
        icon: "el-icon-lx-time",
        index: "/api/dashboard/distribution",
        title: "配送状态",
      },
      {
        icon: "el-icon-lx-recharge",
        index: "/api/dashboard/finance",
        title: "财务管理",
      },
      {
        icon: "el-icon-lx-rank",
        index: "/api/dashboard/analysis",
        title: "经营分析",
      },
      {
        icon: "el-icon-lx-shop",
        index: "/api/dashboard/shop",
        title: "门店管理",
      },
    ];

    const route = useRoute();

    const onRoutes = computed(() => {
      return route.path;
    });

    const store = useStore();
    const collapse = computed(() => store.state.collapse);

    return {
      items,
      onRoutes,
      collapse,
    };
  },
};
</script>

<style scoped>
.sidebar {
  display: block;
  position: absolute;
  left: 0;
  top: 70px;
  bottom: 0;
  overflow-y: scroll;
}
.sidebar::-webkit-scrollbar {
  width: 0;
}
.sidebar-el-menu:not(.el-menu--collapse) {
  width: 250px;
}
.sidebar > ul {
  height: 100%;
}
</style>
