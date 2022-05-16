<template>
  <!-- <div style="width: 100%; height: 100%">
    <div id="container"></div>
    <div id="panel"></div>
  </div> -->
  <div style="height: 100%; weight: 100%">
    <!-- 面包屑导航 -->
    <el-breadcrumb
      separator-class="el-icon-arrow-right"
      style="margin-bottom: 10px"
    >
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>配送状态</el-breadcrumb-item>
    </el-breadcrumb>
    <el-container
      style="height: 100%; weight: 100%"
      class="background-6F9 eig-mian-doc"
      v-loading="loading"
    >
      <el-aside style="width: 60%; height: 100%">
        <iframe
          src="/mapIfram.html"
          ref="frame"
          @load="onLoad"
          width="100%"
          height="100%"
          scrolling="no"
        ></iframe>
      </el-aside>
      <el-main class="wnw-main">
        <span style="font-size: 16px; font-weight: bold">配送列表</span>
        <el-table :data="tableData" style="width: 100%" :show-header="false">
          <el-table-column type="expand" align="center" width="20px">
            <template #default="scope">
              <div m="4">
                <p m="t-0 b-2">订单编号: {{ FormatUUID(scope.row.id) }}</p>
                <p m="t-0 b-2">用户名: {{ scope.row.username }}</p>
                <p m="t-0 b-2">手机号: {{ scope.row.userMobile }}</p>
                <p m="t-0 b-2">配送地址: {{ scope.row.userAddress }}</p>
                <p m="t-0 b-2">商品: {{ FormatFoodList(scope.row.food) }}</p>
                <p m="t-0 b-2">
                  总数量: {{ FormatFoodNumber(scope.row.food) }}
                </p>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            type="index"
            label="序号"
            header-align="center"
            align="center"
            class-name="wn-tableindex"
            width="40px"
          >
          </el-table-column>
          <el-table-column
            label="配送地址"
            prop="userAddress"
            align="left"
            width="auto"
            show-overflow-tooltip
          ></el-table-column>
          <el-table-column
            label="用户名"
            prop="username"
            align="left"
            width="100px"
            show-overflow-tooltip
          ></el-table-column>
          <el-table-column
            v-if="param.status !== 5"
            label="创建时间"
            prop="createTime"
            width="180px"
            align="left"
          >
            <template #default="scope">
              {{ FormatUTC2Local(scope.row.createTime) }}
            </template>
          </el-table-column>
        </el-table>
      </el-main>
    </el-container>
  </div>
</template>

<script>
// import { GetDis } from "@/api/distribution.js";
// import VueAMap from "vue-amap";

import { GetOrderList, SelectCount } from "@/api/order.js";
import moment from "moment";
export default {
  data() {
    return {
      loading: false,
      tableData: [],
      iframeWin: {},
      params: [
        { lng: 122.988404, lat: 41.111892 },
        { lng: 122.995366, lat: 41.076753 },
      ],
      param: {
        status: 4,
        keywords: "",
        pagination: {
          rows: 999,
          page: 1,
          sidx: "CreateTime",
          sord: "desc",
        },
      },
      result: {},
    };
  },

  mounted() {
    //this.sendIframeWinpMessage();
    //this.$refs.iframe.addEventListener("load", this.sendIframeWinpMessage());
    this.loaddata();
  },
  methods: {
    onLoad() {
      this.iframeWin = this.$refs.frame.contentWindow;
      this.sendIframeWinpMessage();
    },
    sendIframeWinpMessage() {
      this.iframeWin.postMessage(
        {
          params: [
            { lng: 121.507738, lat: 38.88029 },
            { lng: 121.516493, lat: 38.860332 },
          ],
        },
        "*"
      );
    },
    loaddata() {
      this.loading = true;
      GetOrderList(this.param)
        .then((res) => {
          if (res.success && res.result) {
            this.tableData = res.result.items;
            this.total = res.result.totalcount;
          }
          this.loading = false;
        })
        .catch((err) => {
          this.loading = false;
        });
      SelectCount({ id: this.shopId }).then((res) => {
        if (res.success) {
          this.statusCount = res.result;
        }
      });
    },
    FormatUTC2Local(date) {
      if (date === "" || date === null) {
        return "";
      }
      var f = "YYYY-MM-DD HH:MM:SS";
      return moment(date).format(f);
    },
    FormatUUID(id) {
      const str = id.toUpperCase();
      return str;
    },
    FormatFoodList(food) {
      var foodName = JSON.parse(food);
      var result = "";
      for (var i = 0; i < foodName.length; i++) {
        result += foodName[i].title + " x" + foodName[i].count + ",";
      }
      return result;
    },
    FormatFoodNumber(food) {
      var foodName = JSON.parse(food);
      var result = 0;
      for (var i = 0; i < foodName.length; i++) {
        result += foodName[i].count;
      }
      return result;
    },
  },
};
</script>

<style type="text/css">
#panel {
  position: fixed;
  background-color: white;
  max-height: 90%;
  overflow-y: auto;
  top: 10px;
  right: 10px;
  width: 280px;
}
#panel .amap-call {
  background-color: #009cf9;
  border-top-left-radius: 4px;
  border-top-right-radius: 4px;
}
#panel .amap-lib-driving {
  border-bottom-left-radius: 4px;
  border-bottom-right-radius: 4px;
  overflow: hidden;
}
</style>
