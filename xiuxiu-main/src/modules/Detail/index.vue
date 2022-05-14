<template>
  <div class="detail" v-loading="bodyLoading">
    <el-row>
      <el-col>
        <div class="logo">
          <el-image
            v-if="user.img"
            style="height: 130px; weight: 130px"
            :src="user.img"
            alt=""
          ></el-image>

          <el-image
            v-else
            :src="require('./images/logo.png')"
            style="height: 130px; weight: 130px"
            alt=""
          ></el-image>

          <span class="name">{{ user.username }}</span>
          <div class="sex" v-if="user.gender == '男'">
            <img src="./images/性别男.png" alt="" />
          </div>
          <div class="sex" v-if="user.gender == '女'">
            <img src="./images/性别女.png" alt="" />
          </div>
          <div class="wallet">
            <img
              src="./images/钱包1.png"
              alt=""
              style="width: 50px; height: 50px; margin-left: 15px"
            /><br />
            余额:
            <span style="font-size: 16px; color: red; font-weight: bolder"
              >￥{{ user.wallet }}</span
            >
          </div>
        </div>
      </el-col>
      <el-col>
        <el-card class="box-card">
          <div class="text item">
            <span
              ><img src="./images/nickname.png" alt="" /> 用户名 :
              {{ user.username }}</span
            >
          </div>

          <div class="text item">
            <span
              ><img src="./images/nickname.png" alt="" /> 真实姓名 :
              {{ user.truename }}</span
            >
          </div>

          <div class="text item">
            <span
              ><img src="./images/性别.png" alt="" /> 性别 :
              {{ user.gender }}</span
            >
          </div>
          <div class="text item">
            <span
              ><img src="./images/电话.png" alt="" /> 电话 :
              {{ user.mobile }}</span
            >
          </div>

          <div class="text item">
            <span
              ><img src="./images/email.png" alt="" /> 邮箱 :
              {{ user.email }}</span
            >
          </div>
        </el-card>
      </el-col>
      <el-col style="text-align: center">
        <el-button round @click="edit(user, 'info')">编辑资料</el-button>
        <el-button round @click="edit(user, 'pass')">修改密码</el-button>
        <el-button round @click="edit(user, 'wallet')">余额充值</el-button>
      </el-col>
    </el-row>

    <el-dialog
      append-to-body
      :visible="userInfoVisible"
      v-loading="loading"
      title="个人信息"
      width="50%"
    >
      <el-main style="height: 50%">
        <el-form :model="form" ref="modelInfo" label-width="100px">
          <el-form-item label="头像:" prop="images">
            <el-upload
              ref="upload"
              style="display: inline-block"
              action=""
              multiple
              :limit="1"
              :http-request="httpRequest"
              :on-remove="handleRemove"
              :before-upload="beforeAvatarUpload"
            >
              <img
                v-if="form.img"
                :src="form.img"
                style="width: 170px; height: 170px"
                class="avatar"
              />
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
          <el-form-item label="用户名" prop="username">
            <el-input
              type="text"
              :readonly="true"
              v-model="form.username"
            ></el-input
          ></el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input type="text" v-model="form.email"></el-input
          ></el-form-item>
          <el-form-item label="联系方式" prop="mobile">
            <el-input type="text" v-model="form.mobile"></el-input
          ></el-form-item>
          <el-form-item label="真实姓名" prop="truename">
            <el-input type="text" v-model="form.truename"></el-input
          ></el-form-item>
          <el-form-item label="性别" prop="gender">
            <el-radio-group v-model="form.gender">
              <el-radio label="男">男</el-radio>
              <el-radio label="女">女</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
      </el-main>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="save('modelInfo')" type="primary" size="mini"
            >保存</el-button
          >
          <el-button @click="userInfoVisible = false" size="mini"
            >关闭</el-button
          >
        </div>
      </template>
    </el-dialog>

    <el-dialog
      append-to-body
      :visible="userPassVisible"
      v-loading="loading"
      title="密码修改"
      width="25%"
    >
      <el-main style="height: 50%">
        <el-form :model="password" ref="pass" label-width="100px" :rules="pass">
          <el-form-item label="原密码" prop="oldPass">
            <el-input type="password" v-model="password.oldPass"></el-input
          ></el-form-item>
          <el-form-item label="新密码" prop="newPass">
            <el-input type="password" v-model="password.newPass"></el-input
          ></el-form-item>
          <el-form-item label="确认密码" prop="checkPass">
            <el-input type="password" v-model="password.checkPass"></el-input
          ></el-form-item>
        </el-form>
      </el-main>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="save('pass')" type="primary" size="mini"
            >保存</el-button
          >
          <el-button @click="userPassVisible = false" size="mini"
            >关闭</el-button
          >
        </div>
      </template>
    </el-dialog>

    <el-dialog
      append-to-body
      :visible="userWalletVisible"
      v-loading="loading"
      width="40%"
      class="walletBox"
      title="余额充值"
    >
      <el-card
        class="box-card"
        style="width: 99%; height: 100%; margin: 0px auto"
      >
        <ul class="msg-box">
          <li>
            <h4>我要充值</h4>
          </li>
          <li>
            <h4 style="margin-bottom: 15px">充值金额</h4>
            <el-radio-group v-model="amountVal" @change="amountChange">
              <el-radio border :label="'' + 100">充值100</el-radio>
              <el-radio border :label="'' + 500">充值500</el-radio>
              <el-radio border :label="'' + 1000">充值1000</el-radio>
              <el-radio border :label="'' + 2000">充值2000</el-radio>
              <el-radio border :label="'' + 5000">充值5000</el-radio>
              <el-radio border :label="''">自定义</el-radio>
            </el-radio-group>
          </li>

          <li>
            <h4 style="margin-bottom: 15px">充值方式</h4>
            <el-radio-group
              v-model="rechargeParams.paymentType"
              @change="paymentTypeChange"
            >
              <el-radio border :label="'' + 0">微信</el-radio>
              <el-radio border :label="'' + 1">支付宝</el-radio>
            </el-radio-group>
          </li>

          <li>
            <h4 style="margin-bottom: 15px">充值金额</h4>
            <!-- <img src="{:url('qrcode',['text'=>$text])}" /> -->
            <el-input
              :disabled="disabled"
              clearable
              v-model="rechargeParams.totalAmt"
              placeholder="请输入金额"
              style="width: 150px"
            ></el-input>
          </li>
        </ul>
        <div style="text-align: center; margin-top: 30px">
          <el-button type="primary" @click="surePay">确认支付</el-button>
          <el-button @click="userWalletVisible = false">取消支付</el-button>
        </div>
      </el-card>
    </el-dialog>
  </div>
</template>

<script>
import { Edit, EditWallet, GetModel } from "@/api/user";
import { messageShow } from "@/assets/js/Common.js";
import { setToken } from "@/utils/token";
export default {
  name: "Detail",
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.password.newPass) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      userInfoVisible: false,
      userPassVisible: false,
      userWalletVisible: false,
      loading: false,
      bodyLoading: false,
      img: "",
      user: {
        username: "",
        password: "",
        age: 0,
        img: "",
        // 钱包
        wallet: "",
        email: "",
        mobile: "",
        truename: "",
        gender: "",
      },
      form: {
        id: "",
        username: "",
        password: "",
        age: 0,
        img: "",
        // 钱包
        wallet: 0,
        email: "",
        mobile: "",
        truename: "",
        gender: "",
      },
      password: {
        oldPass: "",
        newPass: "",
        checkPass: "",
      },
      pass: {
        oldPass: [
          {
            required: true,
            message: "请输入原密码",
            trigger: "blur",
          },
        ],
        newPass: [
          {
            required: true,
            message: "请输入密码",
            trigger: "blur",
          },
        ],
        checkPass: [{ validator: validatePass, trigger: "blur" }],
      },
      amountVal: "",
      disabled: false,
      //充值参数
      rechargeParams: {
        totalAmt: "", //金额
        paymentType: "0", //支付方式[0:微信,1:支付宝,2:余额,3:活动]
        // transType: "0", //交易类型[0:充值,1:消费]
      },
      code: "",
    };
  },
  mounted() {
    this.loadData();
  },
  methods: {
    loadData() {
      this.bodyLoading = true;
      var id = JSON.parse(localStorage.getItem("TOKEN")).id;
      GetModel({ id: id }).then((res) => {
        if (res.success) {
          this.user = res.result;
          setToken(JSON.stringify(this.user));
          this.bodyLoading = false;
        }
      });
    },
    edit(row, operator) {
      this.form = row;
      console.log(this.form);
      if (operator === "info") {
        this.userInfoVisible = true;
      } else if (operator === "pass") {
        this.userPassVisible = true;
      } else if (operator === "wallet") {
        this.userWalletVisible = true;
      }
    },
    save(operator) {
      this.loading = true;
      if (this.password.oldPass !== this.user.password && operator === "pass") {
        messageShow("error", "原密码错误");
        this.loading = false;
        return false;
      } else if (operator === "pass") {
        this.form.password = this.password.newPass;
      }
      Edit(this.form)
        .then((res) => {
          if (res.success) {
            this.user = this.form;
            setToken(JSON.stringify(this.user));
            this.userInfoVisible = false;
            this.userPassVisible = false;

            messageShow("success", "保存成功！");
            this.loading = false;
          }
        })
        .catch((err) => {
          messageShow("error", "保存失败," + err.message);
          this.loading = false;
        });
    },
    amountChange(val) {
      this.rechargeParams.totalAmt = val;
      if (val == "") {
        this.disabled = false;
      } else {
        this.disabled = true;
      }
    },

    //支付方式

    paymentTypeChange(val) {
      this.rechargeParams.paymentType = val;
    },

    //确认支付

    surePay() {
      if (this.rechargeParams.totalAmt == "") {
        this.$message.warning("请输入金额");
        return;
      }

      EditWallet({
        id: this.form.id,
        wallet: Number.parseInt(this.rechargeParams.totalAmt),
      })
        .then((res) => {
          if (res.success) {
            this.form.wallet = res.result;
            this.user = this.form;
            setToken(JSON.stringify(this.user));
            this.userWalletVisible = false;
            messageShow("success", "余额充值成功！");
          }
        })
        .catch((err) => {
          messageShow("error", "余额充值失败，" + err.message);
        });
      // post("weixin/createOrderInfo", this.rechargeParams).then((res) => {
      //   var result = res.result;
      //   if (res.code === 20000) {
      //     // 支付方式跳转

      //     if (this.rechargeParams.paymentType == "0") {
      //       this.$message.success("微信支付");
      //       this.wechatPay(result);
      //     } else if (this.rechargeParams.paymentType == "1") {
      //       this.$message.success("支付宝支付");
      //       const payDiv = document.getElementById("payDiv");
      //       if (payDiv) {
      //         document.body.removeChild(payDiv);
      //       }
      //       const div = document.createElement("div");
      //       div.id = "payDiv";
      //       div.innerHTML = result;
      //       document.body.appendChild(div);
      //       document.getElementById("payDiv").getElementsByTagName("form")[0].submit();
      //     }
      //   }
      // });
    },

    //微信支付

    wechatPay(result) {
      console.log("result " + result);

      // sessionStorage.qrurl = result.qrurl;
      // console.log("qrurl " + result.qrur);
      // sessionStorage.amt = this.rechargeParams.totalAmt;
      // this.$router.push({ path: "/code" });
    },

    httpRequest({ file }) {
      //阿里云OSS上传
      const fileName = `${Date.parse(new Date())}/${file.name}`; //定义唯一的文件名
      // console.log(fileName)
      let OSS = require("ali-oss");
      var client = new OSS({
        region: "oss-cn-hangzhou", //节点
        accessKeyId: "LTAI5t8AEpDswvW4QVp6iyjq",
        accessKeySecret: "0vtOgQcKwzVibXPhITvwRaJGuCTgxp",
        bucket: "yangdonglin",
      });

      client
        .put(fileName, file)
        // eslint-disable-next-line no-unused-vars
        .then(({ res, url, name }) => {
          if (res && res.status == 200) {
            // console.log(`阿里云OSS上传文件成功回调`, res, url, name);
            this.form.img = url;
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

<style lang="less" scoped>
.text {
  vertical-align: middle;
  font-size: 14px;

  span {
    font-weight: bolder;
    img {
      height: 25px;
      width: 25px;
      margin: -4px 10px;
    }
  }
}

.msg-box > li {
  list-style: none;
  border-bottom: 1px solid #c5c5c5;
  padding: 20px 10px;
}

.item {
  padding: 25px 0;
}

.box-card {
  // height: 700px;
  width: 600px;
  margin: 50px auto;
}
.detail {
  width: 1000px;
  //  background-color: antiquewhite;
  height: 100%;
  margin: 0 auto;
  .logo {
    height: 100px;
    margin-top: 20px;
    position: relative;
    img {
      height: 100px;
      width: 100px;
    }
    .name {
      display: inline-block;
      left: 180px;
      font-size: 26px;
      top: 20px;
      position: absolute;
    }
    .sex {
      position: absolute;
      top: 60px;
      font-size: 12px;
      left: 190px;
      img {
        height: 30px;
        width: 30px;
      }
    }
    .wallet {
      position: absolute;
      right: 60px;
      top: 30px;
    }
  }
}
</style>
