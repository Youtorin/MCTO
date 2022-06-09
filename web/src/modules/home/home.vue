<template>
  <el-container v-loading="loading">
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
                    ><span>{{ statistics.totalSales }}</span></el-col
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
                    ><span>销售总额</span></el-col
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
                  <el-col :span="24" class="elRowText"
                    ><span>{{ totalOrder }}</span></el-col
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
                    ><span>订单总量</span></el-col
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
                  <el-col :span="24" class="elRowText"
                    ><span>{{ statistics.totalComment }}</span></el-col
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
                    ><span>评论总数</span></el-col
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
                  <el-col :span="24" class="elRowText"
                    ><span>{{ statistics.totalView }}</span></el-col
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
                    ><span>访问量</span></el-col
                  >
                </el-row>
              </el-col>
            </el-row>
          </el-card>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-card shadow="hover" style="height: 140px">
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
                <span>{{ statistics.totalUser - 1 }}</span>
              </el-col>
              <el-col :span="7">
                <span>1</span>
              </el-col>
              <el-col :span="7">
                <span>{{ statistics.totalUser }}</span>
              </el-col>
              <el-col :span="3">
                <span style="float: right">{{ statistics.totalUser }}</span>
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
          <el-card shadow="hover" style="height: 140px">
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
                <span>{{ foodAndCount.sum }}</span>
              </el-col>
              <el-col :span="11">
                <span>{{ foodAndCount.count }}</span>
              </el-col>
              <el-col :span="2">
                <span style="float: right">{{ statistics.totalCate }}</span>
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
          <el-card shadow="hover" style="auto">
            <template #header>
              <div class="card-header">
                <span style="font-weight: bold; font-size: 18px">订单情况</span>
              </div>
            </template>
            <div class="text item">
              <span>全部订单：</span>
              <span style="float: right; color: green">{{ totalOrder }}</span>
            </div>
            <div class="text item">
              <span>未支付订单：</span>
              <span style="float: right; color: green">{{
                status1.count
              }}</span>
            </div>
            <div class="text item">
              <span>待发货订单：</span>
              <span style="float: right; color: green">{{
                status2.count
              }}</span>
            </div>
            <div class="text item">
              <span>待收货订单：</span>
              <span style="float: right; color: green">{{
                status3.count
              }}</span>
            </div>
            <div class="text item">
              <span>已成交订单数：</span>
              <span style="float: right; color: green">{{
                status4.count
              }}</span>
            </div>
            <div class="text item">
              <span>退款中：</span>
              <span style="float: right; color: green">{{
                status5.count
              }}</span>
            </div>
            <div class="text item">
              <span>交易失败：</span>
              <span style="float: right; color: green">{{
                status6.count
              }}</span>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card shadow="hover" style="height: auto">
            <template #header>
              <div class="card-header">
                <span style="font-weight: bold; font-size: 18px">商品情况</span>
              </div>
            </template>
            <div class="text item">
              <span>库存商品数：</span>
              <span style="float: right; color: green">{{
                foodAndCount.sum
              }}</span>
            </div>
            <div class="text item">
              <span>上架商品：</span>
              <span style="float: right; color: green">{{
                foodAndCount.count
              }}</span>
            </div>
            <div class="text item">
              <span>商品评论：</span>
              <span style="float: right; color: green">{{
                statistics.totalComment
              }}</span>
            </div>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card shadow="hover" style="height: 403px">
            <template #header>
              <div class="clearfix">
                <span>待办事项</span>
                <el-button
                  @click="showDialog"
                  style="float: right; padding: 3px 0"
                  type="text"
                  >添加</el-button
                >
              </div>
            </template>

            <el-table :show-header="false" :data="todoList" style="width: 100%">
              <el-table-column width="40">
                <template #default="scope">
                  <el-checkbox v-model="scope.row.status"></el-checkbox>
                </template>
              </el-table-column>
              <el-table-column>
                <template #default="scope">
                  <div
                    class="todo-item"
                    :class="{
                      'todo-item-del': scope.row.status,
                    }"
                  >
                    {{ scope.row.title }}
                  </div>
                </template>
              </el-table-column>
              <el-table-column width="60">
                <template>
                  <i class="el-icon-edit"></i>
                  <i class="el-icon-delete"></i>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </el-col>
      </el-row>
    </el-main>
    <el-dialog
      append-to-body
      v-model="todoVisible"
      title="待办事项"
      width="50%"
    >
      <el-main style="height: 30%">
        <el-form :model="context" label-width="100px">
          <el-form-item label="事项" prop="cateId">
            <el-input
              type="text"
              v-model="context.title"
            ></el-input> </el-form-item></el-form
      ></el-main>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="addTodo" type="primary" size="mini"
            >保存</el-button
          >
          <el-button @click="todoVisible = false" size="mini">关闭</el-button>
        </div>
      </template>
    </el-dialog>
  </el-container>
</template>

<script>
import { messageShow } from "@/assets/js/Common.js";
import { GetStatistics } from "@/api/statistics.js";
import { AddTodo, GetInfo } from "@/api/shop.js";
import Schart from "vue-schart";
export default {
  name: "home",
  components: { Schart },
  mounted: function () {},
  data() {
    return {
      table: "",
      loading: false,
      todoVisible: false,
      statistics: {
        totalSales: 0,
        orderByCate: "",
        totalComment: 0,
        totalView: 0,
        totalUser: 0,
        foodAndCount: "",
        totalCate: 0,
      },
      order: [],
      foodAndCount: {
        sum: 0,
        count: 0,
      },
      todoList: [{ title: "又是肝代码的一天", status: false }],
      context: {
        title: "",
        status: false,
      },
      totalOrder: 0,
      status1: { count: 0, status: 1 }, //未支付
      status2: { count: 0, status: 2 }, //待发货
      status3: { count: 0, status: 3 }, //待收货
      status4: { count: 0, status: 4 }, //已完成
      status5: { count: 0, status: 5 }, //退款中
      status6: { count: 0, status: -1 }, //已取消
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
  mounted() {
    this.loadData();
  },
  methods: {
    async loadData() {
      this.loading = true;
      await GetStatistics().then((res) => {
        if (res.success) {
          setTimeout(() => {
            this.statistics = res.result;
            this.order = JSON.parse(this.statistics.orderByCate);
            this.foodAndCount = JSON.parse(this.statistics.foodAndCount);
            for (var i = 0; i < this.order.length; i++) {
              this.totalOrder += this.order[i].count;
              switch (this.order[i].status) {
                case 1:
                  this.status1 = this.order[i];
                case 2:
                  this.status2 = this.order[i];
                case 3:
                  this.status3 = this.order[i];
                case 4:
                  this.status4 = this.order[i];
                case 5:
                  this.status5 = this.order[i];
                case -1:
                  this.status6 = this.order[i];
              }
            }
            this.loading = false;
          }, 1000);
        }
      });
      var id = localStorage.getItem("currentUserId");
      await GetInfo({ id: id }).then((res) => {
        if (res.success) {
          this.todoList = JSON.parse(res.result.todoItem);
        }
      });
    },

    showDialog() {
      this.todoVisible = true;
    },
    addTodo() {
      this.todoList.push(this.context);
      var id = localStorage.getItem("currentUserId");
      if (this.todoList === null) {
        this.todoList = [];
      }
      AddTodo({ id: id, todoItem: JSON.stringify(this.todoList) }).then(
        (res) => {
          if (res.success) {
            this.todoVisible = false;
            this.context = { title: "", status: false };
          }
        }
      );
    },
    handleChange(row) {
      var temp = [];
      for (var i = 0; i < this.todoList.length; i++) {
        if (row.title !== this.todaList[i]) {
          temp.push(this.todoList[i]);
        }
      }
      var id = localStorage.getItem("currentUserId");
      setTimeout(() => {
        AddTodo({ id: id, todoItem: JSON.stringify(temp) }).then((res) => {
          if (res.success) {
            this.context = { title: "", status: false };
          }
        });
      });
    },
  },
};
</script>

<style scoped>
.todo-item {
  font-size: 14px;
}

.todo-item-del {
  text-decoration: line-through;
  color: #999;
}
</style>
