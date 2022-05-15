/** 订单列表 */
<template>
  <div>
    <el-container
      style="height: 100%"
      class="background-6F9 eig-mian-doc"
      v-loading="loading"
    >
      <!-- 面包屑导航 -->
      <el-breadcrumb
        separator-class="el-icon-arrow-right"
        style="margin-bottom: 10px"
      >
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>订单列表</el-breadcrumb-item>
      </el-breadcrumb>
      <div height="100px" style="background-color: white">
        <el-row :gutter="20" style="margin: 5px 0">
          <el-col :span="4">
            <el-card
              class="transBig"
              @click="AllOrder()"
              shadow="hover"
              style="height: 100px; background-color: rgba(156, 205, 90)"
            >
              <el-row>
                <el-col :span="24" class="elRowText"
                  ><span>{{ allStatusCount }}</span></el-col
                >
              </el-row>
              <el-row>
                <el-col
                  :span="24"
                  style="
                    text-align: center;
                    color: white;
                    margin-top: -5px;
                    font-size: 16px;
                  "
                  ><span>全部订单</span></el-col
                >
              </el-row>
            </el-card>
          </el-col>
          <el-col :span="4">
            <el-card
              class="transBig"
              shadow="hover"
              @click="ComplateOrder()"
              style="height: 100px; background-color: rgba(255, 88, 88)"
            >
              <el-row>
                <el-col :span="24" class="elRowText"
                  ><span>{{ complateStatusCount }}</span></el-col
                >
              </el-row>
              <el-row>
                <el-col
                  :span="24"
                  style="
                    text-align: center;
                    color: white;
                    margin-top: -5px;
                    font-size: 16px;
                  "
                  ><span>已完成订单</span></el-col
                >
              </el-row>
            </el-card>
          </el-col>
          <el-col :span="4">
            <el-card
              class="transBig"
              shadow="hover"
              @click="NotPayOrder()"
              style="height: 100px; background-color: rgba(255, 165, 0)"
            >
              <el-row>
                <el-col :span="24" class="elRowText"
                  ><span v-if="statusCount !== null">{{
                    notPayStatusCount
                  }}</span></el-col
                >
              </el-row>
              <el-row>
                <el-col
                  :span="24"
                  style="
                    text-align: center;
                    color: white;
                    margin-top: -5px;
                    font-size: 16px;
                  "
                  ><span>待支付订单</span></el-col
                >
              </el-row>
            </el-card>
          </el-col>
          <el-col :span="4">
            <el-card
              class="transBig"
              shadow="hover"
              @click="NotDeliverOrder()"
              style="height: 100px; background-color: rgba(50, 155, 255)"
            >
              <el-row>
                <el-col :span="24" class="elRowText"
                  ><span>{{ notDeliverStatusCount }}</span></el-col
                >
              </el-row>
              <el-row>
                <el-col
                  :span="24"
                  style="
                    text-align: center;
                    color: white;
                    margin-top: -5px;
                    font-size: 16px;
                  "
                  ><span>待配送订单</span></el-col
                >
              </el-row>
            </el-card>
          </el-col>
          <el-col :span="4">
            <el-card
              class="transBig"
              shadow="hover"
              @click="NotReceivingOrder()"
              style="height: 100px; background-color: rgba(150, 150, 250)"
            >
              <el-row>
                <el-col :span="24" class="elRowText"
                  ><span>{{ notReceivingStatusCount }}</span></el-col
                >
              </el-row>
              <el-row>
                <el-col
                  :span="24"
                  style="
                    text-align: center;
                    color: white;
                    margin-top: -5px;
                    font-size: 16px;
                  "
                  ><span>待收货订单</span></el-col
                >
              </el-row>
            </el-card>
          </el-col>
          <el-col :span="4">
            <el-card
              class="transBig"
              shadow="hover"
              @click="RefundOrder()"
              style="height: 100px; background-color: rgba(180, 0, 0)"
            >
              <el-row>
                <el-col :span="24" class="elRowText"
                  ><span>{{ refundStatusCount }}</span></el-col
                >
              </el-row>
              <el-row>
                <el-col
                  :span="24"
                  style="
                    text-align: center;
                    color: white;
                    margin-top: -5px;
                    font-size: 16px;
                  "
                  ><span>退款订单</span></el-col
                >
              </el-row>
            </el-card>
          </el-col>
        </el-row>
      </div>
      <el-header>
        <el-form :inline="true" :model="param" class="user-search">
          <el-row class="wn-row wn-row-fir" style="margin-bottom: 5px">
            <el-col :span="24" align="right">
              <el-input
                v-model.trim="param.keywords"
                maxlength="50"
                placeholder="请输入关键字"
                class="wn-input-group wnw-left wnw-ml-20"
              >
              </el-input>
              <el-button
                icon="el-icon-search"
                style="float: left; margin: 5px 0px"
                type="success"
                size="mini"
                @click="Search"
                circle
              />
            </el-col>
          </el-row>
        </el-form>
      </el-header>
      <el-main class="wnw-main">
        <el-table :data="tableData" style="width: 100%">
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
            label="订单编号"
            prop="id"
            align="left"
            header-align="center"
            width="320px"
            show-overflow-tooltip
          >
            <template #default="scope">
              {{ FormatUUID(scope.row.id) }}
            </template>
          </el-table-column>
          <el-table-column
            label="用户名"
            prop="username"
            align="center"
            show-overflow-tooltip
          ></el-table-column>
          <el-table-column
            label="手机号"
            prop="userMobile"
            align="center"
            width="120px"
          ></el-table-column>
          <el-table-column
            label="地址"
            prop="userAddress"
            align="center"
            show-overflow-tooltip
          ></el-table-column>
          <el-table-column
            label="商品"
            prop="food"
            align="center"
            show-overflow-tooltip
          >
            <template #default="scope">
              {{ FormatFoodList(scope.row.food) }}
            </template>
          </el-table-column>
          <el-table-column
            label="下单数量(件)"
            prop="number"
            align="center"
            width="120px"
          >
            <template #default="scope">
              {{ FormatFoodNumber(scope.row.food) }}
            </template>
          </el-table-column>
          <el-table-column
            label="总价(元)"
            prop="totalMoney"
            align="center"
            width="120px"
          ></el-table-column>
          <el-table-column
            label="状态"
            prop="status"
            align="center"
            show-overflow-tooltip
            ><template #default="scope">
              {{ FormatStatusType(scope.row.status) }}
            </template></el-table-column
          >
          <el-table-column
            v-if="param.status !== 5"
            label="备注"
            prop="remark"
            align="center"
            show-overflow-tooltip
          ></el-table-column>
          <el-table-column
            v-else
            label="退款原因"
            prop="refundReason"
            align="center"
            show-overflow-tooltip
          ></el-table-column>
          <el-table-column
            v-if="param.status !== 5"
            label="创建时间"
            prop="createTime"
            align="center"
          >
            <template #default="scope">
              {{ FormatUTC2Local(scope.row.createTime) }}
            </template>
          </el-table-column>
          <el-table-column
            v-else
            label="申请时间"
            prop="applyTime"
            align="center"
          >
            <template #default="scope">
              {{ FormatUTC2Local(scope.row.applyTime) }}
            </template>
          </el-table-column>
          <el-table-column align="left" width="220px">
            <template #default="scope">
              <el-button
                size="mini"
                type="success"
                round
                @click="ShowDetails(scope.row)"
                >查看</el-button
              >
              <el-button
                size="mini"
                type="primary"
                v-if="scope.row.status === 2"
                round
                @click="handleDeliver(scope.row.id)"
                >发货</el-button
              >
              <el-button
                size="mini"
                v-if="scope.row.status === 5"
                type="primary"
                round
                @click="handleCancel(scope.row)"
                >拒绝</el-button
              >

              <el-button
                size="mini"
                type="danger"
                round
                v-if="scope.row.status === 5"
                @click="handleDelete(scope.row)"
                >退款</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </el-main>
      <el-footer class="wn-footer">
        <el-row style="padding-top: 1px">
          <el-col :span="24" class="align-right">
            <el-pagination
              :background="true"
              :page-sizes="[5, 10, 20]"
              :pager-count="5"
              layout="total, sizes, prev, pager, next, jumper"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :total="total"
            ></el-pagination>
          </el-col>
        </el-row>
      </el-footer>
    </el-container>
    <el-dialog v-model="orderDetailsVisible" title="订单详情" width="50%">
      <el-main>
        <el-tabs v-model="activeName">
          <el-tab-pane label="订单信息" name="1">
            <el-form :model="form" label-width="100px">
              <el-form-item label="订单号:" prop="id">
                <span style="margin-left: 10px">{{
                  form.id
                }}</span></el-form-item
              >
              <el-form-item label="用户名:" prop="username">
                <span style="margin-left: 10px">{{
                  form.username
                }}</span></el-form-item
              >
              <el-form-item label="联系方式:" prop="userMobile">
                <span style="margin-left: 10px">{{
                  form.userMobile
                }}</span></el-form-item
              >
              <el-form-item label="用户地址:" prop="userAddress">
                <span style="margin-left: 10px">{{
                  form.userAddress
                }}</span></el-form-item
              >
              <el-form-item label="总价:" prop="totalMoney">
                <span style="margin-left: 10px">{{ form.totalMoney }}元</span>
              </el-form-item>
              <el-form-item label="创建时间" prop="createTime">
                <span style="margin-left: 10px">{{
                  FormatUTC2Local(form.createTime)
                }}</span>
              </el-form-item>
              <el-form-item label="备注" prop="remark">
                <span style="margin-left: 10px">{{
                  form.remark
                }}</span></el-form-item
              >
            </el-form>
          </el-tab-pane>
          <el-tab-pane label="商品列表" name="2">
            <el-table :data="foodList" style="width: 100%" :show-header="false">
              <el-table-column
                type="index"
                label="序号"
                header-align="center"
                align="center"
                class-name="wn-tableindex"
                width="60px"
              >
              </el-table-column>
              <el-table-column
                label="商品"
                prop="title"
                align="center"
                width="150px"
                show-overflow-tooltip
              ></el-table-column>
              <el-table-column
                label="图片"
                prop="cover"
                align="center"
                width="200px"
              >
                <template #default="scope">
                  <el-image
                    style="width: 100px; height: 100px"
                    :src="scope.row.cover"
                  ></el-image>
                </template>
              </el-table-column>
              <el-table-column label="单价/元" prop="sellPrice" align="center">
                <template #default="scope"
                  >单价：¥{{ scope.row.sellPrice }}</template
                ></el-table-column
              >
              <el-table-column label="数量" prop="count" align="center">
                <template #default="scope"
                  >数量：x {{ scope.row.count }}</template
                >
              </el-table-column>

              <el-table-column
                label="金额/元"
                prop="price"
                align="center"
                width="100px"
                show-overflow-tooltip
              >
                <template #default="scope">
                  总价：¥{{ scope.row.sellPrice * scope.row.count }}
                </template></el-table-column
              >
            </el-table>
          </el-tab-pane>
        </el-tabs>
      </el-main>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="orderDetailsVisible = false">关闭</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import {
  GetOrderList,
  SelectCount,
  SetOrderStatus,
  HandlerRefund,
  HandlerCancel,
} from "@/api/order.js";
import { messageShow } from "@/assets/js/Common.js";
import moment from "moment";
export default {
  data() {
    return {
      loading: false,
      orderDetailsVisible: false,
      shopId: localStorage.getItem("currentUserId"),
      tableData: [],
      foodList: [],
      statusCount: [],
      activeName: "1",
      // 当前页
      pageIndex: this.defaultPageIndex,
      // 页面大小
      pageSize: this.defaultPageSize,
      total: 0,
      form: {},
      param: {
        status: "",
        keywords: "",
        pagination: {
          rows: 10,
          page: 1,
          sidx: "CreateTime",
          sord: "desc",
        },
      },
    };
  },
  computed: {
    allStatusCount() {
      var total = 0;
      for (var i = 0; i < this.statusCount.length; i++) {
        total += this.statusCount[i].count;
      }
      return total;
    },
    refundStatusCount() {
      if (this.statusCount.length === 0) {
        return 0;
      }
      for (var i = 0; i < this.statusCount.length; i++) {
        if (this.statusCount[i].Status === 5) {
          return this.statusCount[i].count;
        }
      }
      return 0;
    },
    complateStatusCount() {
      if (this.statusCount.length === 0) {
        return 0;
      }
      for (var i = 0; i < this.statusCount.length; i++) {
        if (this.statusCount[i].Status === 4) {
          return this.statusCount[i].count;
        }
      }
      return 0;
    },
    notReceivingStatusCount() {
      if (this.statusCount.length === 0) {
        return 0;
      }
      for (var i = 0; i < this.statusCount.length; i++) {
        if (this.statusCount[i].Status === 3) {
          return this.statusCount[i].count;
        }
      }
      return 0;
    },
    notDeliverStatusCount() {
      if (this.statusCount.length === 0) {
        return 0;
      }
      for (var i = 0; i < this.statusCount.length; i++) {
        if (this.statusCount[i].Status === 2) {
          return this.statusCount[i].count;
        }
      }
      return 0;
    },
    notPayStatusCount() {
      if (this.statusCount.length === 0) {
        return 0;
      }
      for (var i = 0; i < this.statusCount.length; i++) {
        if (this.statusCount[i].Status === 1) {
          return this.statusCount[i].count;
        }
      }
      return 0;
    },
  },
  created() {
    this.loaddata();
  },
  methods: {
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
    Search() {
      this.loaddata();
    },
    AllOrder() {
      this.param.status = "";
      this.Search();
    },
    RefundOrder() {
      this.param.status = 5;
      this.Search();
    },
    ComplateOrder() {
      this.param.status = 4;
      this.Search();
    },
    NotReceivingOrder() {
      this.param.status = 3;
      this.Search();
    },
    NotDeliverOrder() {
      this.param.status = 2;
      this.Search();
    },
    NotPayOrder() {
      this.param.status = 1;
      this.Search();
    },
    //查看订单详情
    ShowDetails(row) {
      this.form = row;
      this.foodList = JSON.parse(row.food);
      this.orderDetailsVisible = true;
    },
    //发货
    handleDeliver(id) {
      this.loading = true;
      SetOrderStatus({ id: id, status: 3 })
        .then((res) => {
          if (res.success) {
            messageShow("success", "发货成功!");
            this.Search();
            this.loading = false;
          }
        })
        .catch(() => {
          this.loading = false;
        });
    },
    //退款
    handleDelete(row) {
      this.$confirm("是否确认退款？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.loading = true;
        HandlerRefund(row)
          .then((res) => {
            if (res.success) {
              this.Search();
              this.loading = false;
              messageShow("success", "退款成功！");
            }
          })
          .catch(() => {
            this.loading = false;
          });
      });
    },
    //拒绝
    handleCancel(row) {
      this.loading = true;
      this.$confirm("是否拒绝？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        HandlerCancel(row)
          .then((res) => {
            if (res.success) {
              this.Search();
              this.loading = false;
              messageShow("success", "拒绝成功！");
            }
          })
          .then(() => {});
      });
    },
    handleCurrentChange(page) {
      this.param.pagination.page = page;
      this.loaddata();
    },
    handleSizeChange(rows) {
      this.param.pagination.page = 1;
      this.param.pagination.rows = rows;
      this.loaddata();
    },

    FormatUTC2Local(date) {
      if (date === "" || date === null) {
        return "";
      }
      var f = "YYYY-MM-DD";
      return moment(date).format(f);
    },
    FormatStatusType(status) {
      switch (status) {
        case 1:
          return "未支付";
        case 2:
          return "已支付";
        case 3:
          return "已发货";
        case 4:
          return "已完成";
        case 5:
          return "退款";
      }
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
    FormatUUID(id) {
      const str = id.toUpperCase();
      return str;
    },
  },
};
</script>
