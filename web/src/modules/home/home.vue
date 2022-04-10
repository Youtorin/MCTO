<template>
  <el-main style="height: 100%; background-color: rgb(244, 246, 249)">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card
          shadow="hover"
          style="height: 100px; background-color: rgba(54, 207, 202)"
        >
          <el-row :gutter="10">
            <el-col :span="6"
              ><i
                class="el-icon-suitcase"
                style="font-size: 65px; margin-left: 20px"
              ></i
            ></el-col>
            <el-col :span="18">
              <el-row>
                <el-col :span="24" class="elRowText"
                  ><span>¥ 34522</span></el-col
                >
              </el-row>
              <el-row>
                <el-col
                  :span="24"
                  style="
                    text-align: center;
                    color: white;
                    margin-top: -5px;
                    font-size: 14px;
                  "
                  ><span>今日销售总额</span></el-col
                >
              </el-row>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card
          shadow="hover"
          style="height: 100px; background-color: rgba(255, 196, 57)"
        >
          <el-row :gutter="10">
            <el-col :span="6"
              ><i
                class="el-icon-shopping-cart-2"
                style="font-size: 65px; margin-left: 20px"
              ></i
            ></el-col>
            <el-col :span="18">
              <el-row>
                <el-col :span="24" class="elRowText"><span>45211</span></el-col>
              </el-row>
              <el-row>
                <el-col
                  :span="24"
                  style="
                    text-align: center;
                    color: white;
                    margin-top: -5px;
                    font-size: 14px;
                  "
                  ><span>今日订单量</span></el-col
                >
              </el-row>
            </el-col>
          </el-row>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card
          shadow="hover"
          style="height: 100px; background-color: rgba(253, 77, 79)"
        >
          <el-row :gutter="10">
            <el-col :span="6"
              ><i
                class="el-icon-chat-line-square"
                style="font-size: 65px; margin-left: 20px"
              ></i
            ></el-col>
            <el-col :span="18">
              <el-row>
                <el-col :span="24" class="elRowText"><span>4552</span></el-col>
              </el-row>
              <el-row>
                <el-col
                  :span="24"
                  style="
                    text-align: center;
                    color: white;
                    margin-top: -5px;
                    font-size: 14px;
                  "
                  ><span>今日评论数</span></el-col
                >
              </el-row>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card
          shadow="hover"
          style="height: 100px; background-color: rgba(66, 167, 255)"
        >
          <el-row :gutter="10">
            <el-col :span="6"
              ><i
                class="el-icon-view"
                style="font-size: 65px; margin-left: 20px"
              ></i
            ></el-col>
            <el-col :span="18">
              <el-row>
                <el-col :span="24" class="elRowText"><span>4522</span></el-col>
              </el-row>
              <el-row>
                <el-col
                  :span="24"
                  style="
                    text-align: center;
                    color: white;
                    margin-top: -5px;
                    font-size: 14px;
                  "
                  ><span>今日访问量</span></el-col
                >
              </el-row>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="12">
        <el-card shadow="hover" style="height: 120px">
          <el-row>
            <el-col :span="20">
              <span style="font-weight: bold">会员统计</span>
            </el-col>
            <el-col :span="4">
              <span style="float: right; color: #898989; font-size: 14px"
                >单位（个）</span
              >
            </el-col>
          </el-row>
          <el-row
            :gutter="5"
            style="font-size: 34px; font-weight: bold; margin-top: -10px"
          >
            <el-col :span="7">
              <span>{{ userStatistics.newToday }}</span>
            </el-col>
            <el-col :span="7">
              <span>{{ userStatistics.newYesterday }}</span>
            </el-col>
            <el-col :span="7">
              <span>{{ userStatistics.newMonth }}</span>
            </el-col>
            <el-col :span="3">
              <span style="float: right">{{ userStatistics.total }}</span>
            </el-col>
          </el-row>
          <el-row
            :gutter="10"
            style="font-size: 12px; margin-top: -15px; color: #898989"
          >
            <el-col :span="7">
              <span>今日新增</span>
            </el-col>
            <el-col :span="7">
              <span>昨日新增</span>
            </el-col>
            <el-col :span="7">
              <span>本月新增</span>
            </el-col>
            <el-col :span="3">
              <span style="float: right">会员总数</span>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card shadow="hover" style="height: 120px">
          <el-row>
            <el-col :span="20">
              <span style="font-weight: bold">商品统计</span>
            </el-col>
            <el-col :span="4">
              <span style="float: right; color: #898989; font-size: 14px"
                >单位（件）</span
              >
            </el-col>
          </el-row>
          <el-row
            :gutter="5"
            style="font-size: 34px; font-weight: bold; margin-top: -10px"
          >
            <el-col :span="11">
              <span>{{ foodStatistics.total }}</span>
            </el-col>
            <el-col :span="11">
              <span>{{ foodStatistics.foodNum }}</span>
            </el-col>
            <el-col :span="2">
              <span style="float: right">{{ foodStatistics.foodCate }}</span>
            </el-col>
          </el-row>
          <el-row
            :gutter="10"
            style="font-size: 12px; margin-top: -15px; color: #898989"
          >
            <el-col :span="11">
              <span>商品总数</span>
            </el-col>
            <el-col :span="10">
              <span>商家商品</span>
            </el-col>
            <el-col :span="3">
              <span style="float: right">商品类目</span>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card shadow="hover" style="height: 280px">
          <template #header>
            <div class="card-header">
              <span style="font-weight: bold; font-size: 18px">订单情况</span>
            </div>
          </template>
          <div class="text item">
            <span>全部订单：</span>
            <span style="float: right; color: green">0</span>
          </div>
          <div class="text item">
            <span>待发货订单：</span>
            <span style="float: right; color: green">10</span>
          </div>
          <div class="text item">
            <span>待收货订单：</span>
            <span style="float: right; color: green">13</span>
          </div>
          <div class="text item">
            <span>已成交订单数：</span>
            <span style="float: right; color: green">26</span>
          </div>
          <div class="text item">
            <span>交易失败：</span>
            <span style="float: right; color: green">30</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" style="height: 280px">
          <template #header>
            <div class="card-header">
              <span style="font-weight: bold; font-size: 18px">商品情况</span>
            </div>
          </template>
          <div class="text item">
            <span>库存商品数：</span>
            <span style="float: right; color: green">312</span>
          </div>
          <div class="text item">
            <span>库存警告商品：</span>
            <span style="float: right; color: green">10</span>
          </div>
          <div class="text item">
            <span>上架商品：</span>
            <span style="float: right; color: green">13</span>
          </div>
          <div class="text item">
            <span>下架商品：</span>
            <span style="float: right; color: green">26</span>
          </div>
          <div class="text item">
            <span>商品评论：</span>
            <span style="float: right; color: green">215</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card shadow="hover" style="height: 280px">
          <schart
            ref="bar"
            class="schart"
            canvasId="bar"
            :options="options"
          ></schart>
        </el-card>
      </el-col>
    </el-row>
  </el-main>
</template>

<script>
import { messageShow } from "@/assets/js/Common.js";
import Schart from "vue-schart";
export default {
  name: "home",
  components: { Schart },
  mounted: function () {},
  data() {
    return {
      table: "",
      userStatistics: {
        newToday: 0, //今日新增
        newYesterday: 22, //昨日新增
        newMonth: 131, //本月新增
        total: 1352, //会员总数
      },
      foodStatistics: {
        total: 4312, //商品总数
        foodNum: 312, //商家商品数
        foodCate: 8,
      },
      options: {
        type: "bar",
        title: {
          text: "最近一周各品类销售图",
        },
        xRorate: 25,
        labels: ["周一", "周二", "周三", "周四", "周五"],
        datasets: [
          {
            label: "休闲零食",
            data: [234, 278, 270, 190, 230],
          },
          {
            label: "酒水饮料",
            data: [164, 178, 190, 135, 160],
          },
          {
            label: "日常用品",
            data: [144, 198, 150, 235, 120],
          },
        ],
      },
    };
  },
  methods: {},
};
</script>

<style scoped>
</style>
