<template>
  <div style="margin: 0 auto; width: 1500px; height: 800px">
    <el-container
      style="height: 100%"
      class="background-6F9 eig-mian-doc"
      v-loading="loading"
    >
      <el-header>
        <el-form :inline="true" :model="param" class="user-search">
          <el-row class="wn-row wn-row-fir" style="margin-bottom: 5px">
            <el-col :span="24" align="right">
              <el-select
                v-model="param.status"
                placeholder="请选择订单状态"
                class="wn-select-option wnw-left wnw-ml-20"
                @change="Search()"
              >
                <el-option
                  v-for="item in statusList"
                  :key="item.value"
                  :label="item.name"
                  :value="item.value"
                >
                </el-option>
              </el-select>
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
            width="50px"
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
            <template slot-scope="scope">
              {{ FormatUUID(scope.row.id) }}
            </template>
          </el-table-column>
          <el-table-column
            label="用户名"
            prop="username"
            align="center"
            width="100px"
            show-overflow-tooltip
          ></el-table-column>
          <el-table-column
            label="商品"
            prop="food"
            总价
            align="center"
            show-overflow-tooltip
          >
            <template slot-scope="scope">
              {{ FormatFoodList(scope.row.food) }}
            </template>
          </el-table-column>
          <el-table-column
            label="下单数量(件)"
            prop="number"
            align="center"
            width="120px"
          >
            <template slot-scope="scope">
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
            width="220px"
            label="创建时间"
            prop="createTime"
            align="center"
          >
            <template slot-scope="scope">
              {{ FormatUTC2Local(scope.row.createTime) }}
            </template>
          </el-table-column>
          <el-table-column align="left">
            <template slot-scope="scope">
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
                v-if="scope.row.status === 3"
                round
                @click="confirmOrder(scope.row)"
                >确认收货</el-button
              >
              <el-button
                size="mini"
                type="primary"
                v-if="scope.row.status === 1"
                round
                @click="confirmPay(scope.row)"
                >去支付</el-button
              >
              <el-button
                size="mini"
                type="primary"
                v-if="scope.row.status === 4"
                round
                @click="handleComment(scope.row)"
                >评论</el-button
              >
              <el-button
                size="mini"
                type="danger"
                v-if="scope.row.status !== 1 && scope.row.status !== -1"
                round
                :disabled="scope.row.status === 5"
                @click="handleRefund(scope.row)"
                >申请退款</el-button
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

    <el-dialog
      append-to-body
      :visible="orderDetailsVisible"
      title="订单详情"
      width="50%"
    >
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
                <template slot-scope="scope">
                  <el-image
                    style="width: 100px; height: 100px"
                    :src="scope.row.cover"
                  ></el-image>
                </template>
              </el-table-column>
              <el-table-column label="单价/元" prop="sellPrice" align="center">
                <template slot-scope="scope"
                  >单价：¥{{ scope.row.sellPrice }}</template
                ></el-table-column
              >
              <el-table-column label="数量" prop="count" align="center">
                <template slot-scope="scope"
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
                <template slot-scope="scope">
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

    <el-dialog
      append-to-body
      :visible="refundVisible"
      title="退款申请"
      width="35%"
    >
      <el-main v-loading="refundLoading">
        <el-tabs v-model="activeName">
          <el-form
            :model="refundData"
            :rules="rules"
            ref="refund"
            label-width="100px"
          >
            <el-form-item label="订单号:" prop="id">
              <el-input
                type="text"
                :readonly="true"
                v-model="refundData.id"
              ></el-input>
            </el-form-item>
            <el-form-item label="用户名:" prop="username">
              <el-input
                type="text"
                :readonly="true"
                v-model="refundData.username"
              ></el-input
            ></el-form-item>
            <el-form-item label="退回款项(元):" prop="totalMoney">
              <el-input
                type="text"
                :readonly="true"
                v-model="refundData.totalMoney"
              ></el-input>
            </el-form-item>
            <el-form-item label="退款原因:" prop="refundReason">
              <el-input
                type="text"
                v-model="refundData.refundReason"
              ></el-input>
            </el-form-item>
          </el-form>
        </el-tabs>
      </el-main>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="danger" @click="refundSubmit()">退款</el-button>
          <el-button @click="refundVisible = false">关闭</el-button>
        </div>
      </template>
    </el-dialog>

    <el-dialog
      append-to-body
      title="支付"
      :lock-scroll="true"
      :visible.sync="payVisible"
      :show-close="false"
      v-loading="payLoading"
      :close-on-press-escape="false"
      :close-on-click-modal="false"
      width="30%"
    >
      <div v-if="showPay">
        <el-main style="height: 50%">
          <el-form ref="form" label-width="80px">
            <el-form-item label="用户名">
              <span>{{ payForm.username }}</span>
            </el-form-item>
            <el-form-item label="配送地址">
              <span>{{ payForm.userAddress }}</span>
            </el-form-item>
            <el-form-item label="联系方式">
              <span>{{ payForm.userMobile }}</span>
            </el-form-item>
            <el-form-item label="总价">
              <span>{{ payForm.totalMoney }}元</span>
            </el-form-item>
            <el-form-item label="支付方式">
              <span>{{ paymentType | payMethod }}</span>
            </el-form-item>

            <el-form-item>
              <el-radio-group v-model="paymentType" @change="paymentTypeChange">
                <el-radio border :label="'' + 0">微信</el-radio>
                <el-radio border :label="'' + 1">支付宝</el-radio>
                <el-radio border :label="'' + 2">余额</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-form>
          <div class="align-center" v-if="paymentType === '0'">
            <el-image
              style="width: 200px; height: 200px"
              :src="require('@/assets/img/pay_wechat.jpg')"
              fit="fill"
            ></el-image>
            <h1>微信支付</h1>
          </div>
          <div class="align-center" v-else-if="paymentType === '1'">
            <el-image
              style="width: 200px; height: 200px"
              :src="require('@/assets/img/alipay.jpg')"
              fit="fill"
            ></el-image>
            <h1>支付宝支付</h1>
          </div>
        </el-main>
      </div>
      <div v-else-if="!showPay">
        <el-result
          v-if="paySuccess"
          icon="success"
          title="支付成功"
          :subTitle="backSubTitle"
        >
          <template slot="extra">
            <el-button type="primary" size="medium" @click="back()"
              >立即返回</el-button
            >
          </template>
        </el-result>
        <el-result v-else icon="error" title="支付失败" subTitle="余额不足">
          <template slot="extra">
            <el-button type="primary" size="medium" @click="back()"
              >立即返回</el-button
            >
          </template>
        </el-result>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button v-if="showPay" type="danger" @click="back()">退出</el-button>
        <el-button v-if="showPay" type="primary" @click="pay()">支付</el-button>
      </span>
    </el-dialog>

    <el-dialog
      append-to-body
      :visible="commentVisible"
      title="评论"
      width="35%"
    >
      <el-main v-loading="commentLoading">
        <el-form :model="commentText" ref="refund" label-width="100px">
          <el-form-item label="订单号:" prop="id">
            <el-input
              type="text"
              :readonly="true"
              v-model="commentText.id"
            ></el-input>
          </el-form-item>
          <el-form-item label="用户名:" prop="username">
            <el-input
              type="text"
              :readonly="true"
              v-model="commentText.username"
            ></el-input
          ></el-form-item>

          <el-form-item label="图片:" prop="images">
            <el-upload
              ref="upload"
              style="display: inline-block"
              action=""
              multiple
              :limit="5"
              :http-request="httpRequest"
              :on-remove="handleRemove"
              :before-upload="beforeAvatarUpload"
              :file-list="fileList"
            >
              <el-button type="info" size="mini">点击上传</el-button>
            </el-upload>
          </el-form-item>
          <el-form-item label="评分:" prop="score">
            <el-rate
              style="margin-top: 8px"
              v-model="commentText.score"
            ></el-rate>
          </el-form-item>
          <el-form-item label="评论内容:" prop="content">
            <el-input type="text" v-model="commentText.content"></el-input
          ></el-form-item>
        </el-form>
      </el-main>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="danger" @click="handleCommentSave()">保存</el-button>
          <el-button @click="commentVisible = false">关闭</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { GetListByUser, Refund, SetOrderStatus } from "@/api/order.js";
import { messageShow } from "@/assets/js/Common.js";
import { AddComment } from "@/api/orderComment.js";

import moment from "moment";
export default {
  name: "MyPay",
  data() {
    return {
      loading: false,
      refundLoading: false,
      orderDetailsVisible: false,
      refundVisible: false,
      dialogLoding: false,
      payLoading: false,
      commentLoading: false,
      payVisible: false,
      commentVisible: false,
      showPay: true,
      tableData: [],
      foodList: [],
      fileList: [],
      upFileList: [],
      activeName: "1",
      paymentType: "0",
      // 当前页
      pageIndex: this.defaultPageIndex,
      // 页面大小
      pageSize: this.defaultPageSize,
      total: 0,
      form: {}, //详情
      payForm: {},
      refundData: {
        id: "",
        username: "",
        totalMoney: "",
        refundReason: "",
      },
      commentText: {
        orderId: "",
        username: "",
        userid: "",
        content: "",
        score: "",
        images: [],
      },
      statusList: [
        {
          name: "全部订单",
          value: "",
        },
        {
          name: "待支付",
          value: 1,
        },
        {
          name: "待配送",
          value: 2,
        },
        {
          name: "待收货",
          value: 3,
        },
        {
          name: "已完成",
          value: 4,
        },
        {
          name: "退款中",
          value: 5,
        },
        {
          name: "已关闭",
          value: -1,
        },
      ],
      param: {
        userId: "",
        status: "",
        keywords: "",
        pagination: {
          rows: 10,
          page: 1,
          sidx: "CreateTime",
          sord: "desc",
        },
      },

      rules: {
        refundReason: [
          { required: true, message: "请输入退款原因", trigger: "blur" },
        ],
      },
    };
  },
  filters: {
    payMethod(val) {
      if (val === "0") {
        return "微信支付";
      } else if (val === "1") {
        return "支付宝支付";
      } else if (val === "2") {
        return "余额支付";
      }
    },
  },
  created() {
    this.loaddata();
  },
  methods: {
    loaddata() {
      this.loading = true;
      var temp = JSON.parse(localStorage.getItem("TOKEN"));
      this.param.userId = temp.id;
      GetListByUser(this.param)
        .then((res) => {
          if (res.success && res.result) {
            this.tableData = res.result.items;
            this.total = res.result.totalcount;
          }
          this.loading = false;
        })
        .catch(() => {
          this.loading = false;
        });
    },
    Search() {
      this.loaddata();
    },
    //查看订单详情
    ShowDetails(row) {
      this.form = row;
      this.foodList = JSON.parse(row.food);
      this.orderDetailsVisible = true;
    },
    //申请退款
    handleRefund(row) {
      this.refundData = row;
      this.refundVisible = true;
    },
    refundSubmit() {
      this.refundLoading = true;
      this.$refs["refund"].validate((valid) => {
        if (valid) {
          Refund(this.refundData)
            .then((res) => {
              if (res.success) {
                messageShow("success", "申请退款成功");
                this.refundVisible = false;
                this.Search();
                this.refundLoading = false;
              }
            })
            .then((err) => {
              messageShow("error", err);
              this.refundVisible = false;
              this.refundLoading = false;
            });
        } else {
          messageShow("warning", "表单有必填信息未填！");
          this.refundLoading = false;
        }
      });
    },
    //确认收货
    confirmOrder(row) {
      this.$confirm("是否确认收货？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.loading = true;
        SetOrderStatus({ id: row.id, status: 4 })
          .then((res) => {
            if (res.success) {
              messageShow("success", "确认收货成功");
              this.Search();
              this.loading = false;
            }
          })
          .catch(() => {
            this.loading = false;
          });
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
          return "已配送";
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
    paymentTypeChange(val) {
      this.paymentType = val;
    },
    //支付
    confirmPay(row) {
      console.log(row);
      this.loading = true;
      this.payForm = row;
      this.payVisible = true;
    },
    back() {
      this.Search;
      this.payVisible = false;
      this.loading = false;
      this.showPay = true;
      this.payForm = {};
    },
    messageTime() {
      setTimeout(() => {
        this.showPay = false;
        this.payLoading = false;
        this.backSubTitle = "3秒后返回";
        setTimeout(() => {
          this.backSubTitle = "2秒后返回";
          setTimeout(() => {
            this.backSubTitle = "1秒后返回";
            setTimeout(() => {
              this.back();
            }, 1000);
          }, 1000);
        }, 1000);
      }, 2000);
    },
    pay() {
      this.payLoading = true;
      SetOrderStatus({
        id: this.payForm.id,
        status: 2,
        price: this.payForm.totalMoney,
        payType: this.paymentType,
      })
        .then((res) => {
          if (res.success) {
            this.paySuccess = true;
            this.messageTime();
          }
        })
        .catch(() => {
          this.paySuccess = false;
          this.messageTime();
        });
    },
    //评论
    handleComment(row) {
      this.commentText = row;
      this.commentText.orderId = row.id;
      this.commentVisible = true;
    },
    handleCommentSave() {
      this.commentLoading == true;
      this.commentText.images = JSON.stringify(this.upFileList);
      AddComment(this.commentText)
        .then((res) => {
          if (res.success) {
            messageShow("success", "评论成功！");
            this.Search();
            this.upFileList = [];
            this.commentLoading == false;
            this.commentVisible = false;
          }
        })
        .catch(() => {});
    },
    httpRequest({ file }) {
      //阿里云OSS上传
      const fileName = `${Date.parse(new Date())}/${file.name}`; //定义唯一的文件名
      // console.log(fileName)
      let OSS = require("ali-oss");
      var client = new OSS({
        region: "oss-cn-hangzhou", //节点
        accessKeyId: "accessKeyId",
        accessKeySecret: "accessKeySecret",
        bucket: "yangdonglin",
      });

      client
        .put(fileName, file)
        // eslint-disable-next-line no-unused-vars
        .then(({ res, url, name }) => {
          if (res && res.status == 200) {
            // console.log(`阿里云OSS上传文件成功回调`, res, url, name);
            this.fileList.push({
              //回显文件name用
              url: url,
              id: fileName,
              name: file.name,
            });
            this.upFileList.push(url);
          }
        })
        .catch((err) => {
          console.log(`阿里云OSS上传失败回调`, err);
        });
    },
    handleRemove(e, fileList) {
      this.fileList = fileList;
    },
    beforeAvatarUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isLt2M) {
        this.$message.error("文件大小不能超过 2M !");
      }
      return isLt2M;
    },
  },
};
</script>