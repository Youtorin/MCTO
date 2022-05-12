
<template>
  <div v-loading="loading">
    <el-collapse v-model="activeNames">
      <el-collapse-item title="地址列表" name="1" class="wn-form-collapse">
        <el-col>
          <span class="addAddressCons">收货人信息</span>
          <el-button
            type="primary"
            class="addAddress"
            @click="AddressShowClick"
          >
            新增收货地址
          </el-button>
          <div class="scolleShow">
            <el-row
              class="consignee"
              v-for="(items, index) in filterAddress"
              :key="index"
            >
              <el-col :span="4">
                <div
                  class="consigneeList consigneeListWidth"
                  :class="{ consigneeListClick: items.isDefault }"
                  @click="currentIndexClick(items, index)"
                >
                  <span v-text="items.username"></span>
                  <span v-text="items.address"></span>
                  <div v-show="items.isDefault">
                    <div class="arrow-right"></div>
                    <i class="fa fa-check" aria-hidden="true"></i>
                  </div>
                </div>
              </el-col>
              <el-col class="rightAddress" :span="20">
                <span v-text="items.username"></span>
                <span v-text="items.address"></span>
                <span v-text="items.mobile"></span>
                <span v-if="items.isDefault === 1" class="addressDefultClass"
                  >默认地址</span
                >
                <span class="edmitAddre">
                  <span class="edmitSpan" @click="defultAddress(items)"
                    >设为默认地址</span
                  >
                  <span class="edmitSpan" @click="emitAddress(items)"
                    >编辑</span
                  >
                  <span class="edmitSpan" @click="deleAddressAlert(items.id)"
                    >删除</span
                  >
                </span>
              </el-col>
            </el-row>
          </div>
          <el-dialog
            append-to-body
            title="新增收货地址"
            v-loading="dialogLoding"
            :visible.sync="newAddressShow"
            width="30%"
          >
            <el-main style="height: 50%">
              <el-form ref="form" :model="form" label-width="80px">
                <el-form-item label="收货人">
                  <el-input
                    v-model="form.username"
                    placeholder="请输入内容"
                  ></el-input>
                </el-form-item>
                <el-form-item label="所在地区">
                  <el-cascader
                    size="large"
                    :options="options"
                    v-model="address"
                    :props="{ value: 'label' }"
                    @change="handleChange"
                    :show-all-levels="false"
                    clearable
                  >
                  </el-cascader>
                </el-form-item>
                <el-form-item label="详细地址">
                  <el-input
                    v-model="form.address"
                    placeholder="请输入内容"
                  ></el-input>
                </el-form-item>
                <el-form-item label="手机号码">
                  <el-input
                    v-model="form.mobile"
                    placeholder="请输入内容"
                  ></el-input>
                </el-form-item>
                <el-form-item>
                  <el-checkbox-group v-model="isDefault">
                    <el-checkbox label="设为默认地址" name="type"
                      >设为默认地址</el-checkbox
                    >
                  </el-checkbox-group>
                </el-form-item>
              </el-form>
            </el-main>
            <span slot="footer" class="dialog-footer">
              <el-button @click="newAddressShow = false" type="danger"
                >取 消</el-button
              >
              <el-button type="primary" @click="SaveNewAdress(edmitType)"
                >保存</el-button
              >
            </span>
          </el-dialog>
          <div v-if="!AddressData.length == 0" class="muchAddress">
            <span @click="moreAddress"
              >更多地址
              <transition name="fade" mode="out-in">
                <i
                  key="up"
                  v-if="isTranShow"
                  class="fa fa-angle-double-down"
                ></i>
                <i key="down" v-else class="fa fa-angle-double-up"></i>
              </transition>
            </span>
          </div>
          <div v-if="AddressData.length == 0" class="noAddress">
            <span>您还没有收货地址，请增加新的收货地址！</span>
          </div>
        </el-col>
      </el-collapse-item>
      <el-collapse-item title="送货清单" name="2" class="wn-form-collapse">
        <el-table :data="tableData" style="width: 100%" :show-header="false">
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
            <template slot-scope="{ row }"
              >单价：¥{{ row.sellPrice }}</template
            ></el-table-column
          >
          <el-table-column label="数量" prop="count" align="center">
            <template slot-scope="{ row }">数量：x {{ row.count }}</template>
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
      </el-collapse-item>
      <el-collapse-item title="规格选项" name="3" class="wn-form-collapse">
        <span class="addAddressCons">支付方式</span>
        <el-form style="margin: 10px 20px">
          <el-form-item>
            <el-radio-group v-model="paymentType" @change="paymentTypeChange">
              <el-radio border :label="'' + 0">微信</el-radio>
              <el-radio border :label="'' + 1">支付宝</el-radio>
              <el-radio border :label="'' + 2">余额</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item>
            <el-row>
              <el-radio-group
                v-model="distribution"
                @change="distmentTypeChange"
              >
                <el-radio border :label="'' + 0">送货上门</el-radio>
                <el-radio border :label="'' + 1">自取</el-radio>
              </el-radio-group>
            </el-row>
          </el-form-item>
          <el-form-item label="备注" prop="remark">
            <el-row>
              <el-col :span="8">
                <el-input type="text" v-model="remark"></el-input
              ></el-col>
            </el-row>
          </el-form-item>
        </el-form>
      </el-collapse-item>
    </el-collapse>
    <div style="padding: 5px 20px; font-size: 16px">
      <div>总价：¥{{ totalPrice }}</div>
      <div>支付方式：{{ paymentType | payMethod }}</div>
      <div>配送：{{ distribution | disMethod }}</div>
      <el-button type="success" style="margin-top: 10px" @click="creatOrder()"
        >创建订单</el-button
      >
      <el-button type="danger" style="margin-top: 10px" @click="back()"
        >返回</el-button
      >
    </div>

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
      <div v-if="showPay && AddressData.length !== 0">
        <el-main style="height: 50%">
          <el-form ref="form" :model="form" label-width="80px">
            <el-form-item label="用户名">
              <span>{{ AddressData[0].username }}</span>
            </el-form-item>
            <el-form-item label="配送地址">
              <span>{{ AddressData[0].address }}</span>
            </el-form-item>
            <el-form-item label="联系方式">
              <span>{{ AddressData[0].mobile }}</span>
            </el-form-item>
            <el-form-item label="总价">
              <span>{{ totalPrice }}元</span>
            </el-form-item>
            <el-form-item label="支付方式">
              <span>{{ paymentType | payMethod }}</span>
            </el-form-item>
            <el-form-item label="配送方式">
              <span>{{ distribution | disMethod }}</span>
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
            <el-button type="primary" size="medium" @click="backHome()"
              >立即返回</el-button
            >
          </template>
        </el-result>
        <el-result v-else icon="error" title="支付失败" subTitle="余额不足">
          <template slot="extra">
            <el-button type="primary" size="medium" @click="backHome()"
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
  </div>
</template>

<script>
import { regionData } from "element-china-area-data";
import { currentUser, messageShow } from "@/assets/js/Common.js";
import {
  EditAddress,
  GetAddressList,
  SetDefault,
  DeleteAddress,
} from "@/api/userAddress.js";
import { AddOrder, SetOrderStatus } from "@/api/order.js";
export default {
  data() {
    return {
      loading: false,
      dialogLoding: false,
      payLoading: false,
      userId: JSON.parse(localStorage.getItem("TOKEN")).id,
      username: JSON.parse(localStorage.getItem("TOKEN")).username,
      activeNames: ["1", "2", "3"],
      AddressData: [],
      tableData: [],
      address: [],
      backSubTitle: "",
      orderId: "",
      isTranShow: true,
      isDefault: false,
      newAddressShow: false,
      payVisible: false,
      showPay: true,
      paySuccess: false,
      limitNum: 1, //默认显示几个地址
      paymentType: "0",
      distribution: "0",
      remark: "",
      edmitType: "",
      options: regionData,
      form: {
        id: "",
        username: "",
        address: "",
        mobile: "",
        isDefault: 0,
        province: "",
        city: "",
        district: "",
        addressIsShow: "",
        isShowDefult: "",
      },
    };
  },
  created() {
    this.GetUserAdress();
    this.GetShopCartDate();
  },
  methods: {
    //加载收货人信息
    GetUserAdress() {
      this.loading = true;
      GetAddressList({ id: this.userId })
        .then((res) => {
          if (res.success && res.result) {
            this.loading = false;
            this.AddressData = res.result;
          }
        })
        // eslint-disable-next-line no-unused-vars
        .catch((err) => {
          this.loading = false;
        });
    },
    //加载购物车数据
    GetShopCartDate() {
      if (localStorage.getItem(currentUser.username + "shopCartOrder")) {
        this.tableData = JSON.parse(
          localStorage.getItem(currentUser.username + "shopCartOrder")
        );
      }
      for (var i = 0; i < this.tableData.length; i++) {
        this.tableData[i] = Object.assign(this.tableData[i], {
          price: this.tableData[i].sellPrice * this.tableData[i].count,
        });
      }
    },
    //打开新增收货地址
    AddressShowClick() {
      (this.edmitType = "add"), (this.newAddressShow = true);
    },
    //新增收货地址
    SaveNewAdress(operator, param) {
      if (operator === "add") {
        this.dialogLoding = true;
        if (this.isDefault) {
          this.form.isDefault = 1;
        }
      }
      param = Object.assign(this.form, { userId: this.userId });
      EditAddress(param)
        .then((res) => {
          if (res.success) {
            messageShow("success", "保存成功！");
            this.GetUserAdress();
            this.dialogLoding = false;
            this.newAddressShow = false;
          }
        })
        // eslint-disable-next-line no-unused-vars
        .catch((err) => {
          this.dialogLoding = false;
        });
    },
    //点击更多地址函数
    moreAddress() {
      //根据状态选择单条数据还是多条数据
      if (this.limitNum == 1) {
        this.limitNum = this.AddressData.length;
        this.isTranShow = !this.isTranShow;
      } else {
        this.limitNum = 1;
      }
    },
    //设置为默认收货地址
    defultAddress(row) {
      if (row.isDefault === 1) {
        messageShow("warning", "已是默认地址！");
        return;
      }
      this.loading = true;
      SetDefault({ id: row.id })
        .then((res) => {
          if (res.success) {
            messageShow("success", "已设为默认地址！");
            this.GetUserAdress();
            this.loading = false;
          }
        })
        .catch((err) => {
          messageShow("erroe", err);
          this.loading = false;
        });
    },
    //编辑收货地址函数
    emitAddress(row) {
      console.log(row);
      this.newAddressShow = true;
      this.edmitType = "edit";
      //把此行数据到对话框
      this.form.username = row.username;
      this.form.address = row.address;
      this.form.mobile = row.mobile;
      this.form.id = row.id;
      this.form.isDefault = row.isDefault;
    },
    handleChange(value) {
      this.form.province = value[0];
      this.form.city = value[1];
      this.form.district = value[2];
      console.log(this.form);
    },
    deleAddressAlert(id) {
      this.$confirm("是否确认删除？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.loading = true;
          DeleteAddress({ id: id })
            .then((res) => {
              if (res.success) {
                messageShow("success", "删除成功！");
                this.loading = false;
                this.GetUserAdress();
              }
            })
            .catch((err) => {
              this.loading = false;
              messageShow("error", err);
            });
        })
        .catch(() => {
          this.loading = false;
        });
    },
    paymentTypeChange(val) {
      this.paymentType = val;
    },
    distmentTypeChange(val) {
      this.distribution = val;
    },
    creatOrder() {
      if (this.AddressData.length === 0) {
        messageShow("warning", "请选择一个收货地址！");
        return;
      }
      var param = {
        userId: this.userId,
        userAddressId: this.AddressData[0].id,
        username: this.AddressData[0].username,
        userMobile: this.AddressData[0].mobile,
        remark: this.remark,
        food: JSON.stringify(this.tableData),
        totalMoney: this.totalPrice,
        distributionType: this.distribution,
      };
      this.loading = true;
      console.log(param);
      AddOrder(param)
        .then((res) => {
          if (res.success) {
            messageShow("success", "创建订单成功！");
            this.orderId = res.result;
            var temp = JSON.parse(
              localStorage.getItem(this.username + "shopCart")
            );
            var result = [];
            var flag = true;
            for (var i = 0; i < temp.length; i++) {
              flag = true;
              for (var j = 0; j < this.tableData.length; j++) {
                if (temp[i].id === this.tableData[j].id) {
                  flag = false;
                }
              }
              if (flag) {
                result.push(temp[i]);
              }
            }
            localStorage.setItem(
              this.username + "shopCart",
              JSON.stringify(result)
            );
            this.payVisible = true;
          }
        })
        .catch((err) => {
          messageShow("error", "创建订单失败，" + err);
          this.loading = false;
        });
    },
    back() {
      this.$router.push("/shopcart");
    },
    backHome() {
      this.$router.push("/home");
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
              this.backHome();
            }, 1000);
          }, 1000);
        }, 1000);
      }, 2000);
    },
    pay() {
      this.payLoading = true;
      SetOrderStatus({
        id: this.orderId,
        status: 2,
        price: this.totalPrice,
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
    disMethod(val) {
      if (val === "0") {
        return "送货上门";
      } else if (val === "1") {
        return "自取";
      }
    },
  },
  /*用于过滤缓存(用于过滤地址显示几个)*/
  computed: {
    filterAddress() {
      return this.AddressData.slice(0, this.limitNum);
    },
    totalPrice() {
      var p = 0;
      for (var i = 0; i < this.tableData.length; i++) {
        p += this.tableData[i].sellPrice * this.tableData[i].count;
      }
      return p;
    },
  },
};
</script>