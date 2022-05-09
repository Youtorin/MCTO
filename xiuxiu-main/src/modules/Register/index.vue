<template>
  <div class="register-container">
    <!-- 注册内容 -->
    <div class="register">
      <h3>
        注册新用户
        <span class="go"
          >我有账号，去 <router-link to="/login">登陆</router-link>
        </span>
      </h3>
      <el-form
        :model="user"
        ref="user"
        label-width="100px"
        class="demo-dynamic"
        :rules="rules"
      >
        <el-form-item
          class="item"
          prop="username"
          label="用户名"
          :rules="[
            { required: true, message: '请输入用户名', trigger: 'blur' },
          ]"
        >
          <el-input v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password" class="item">
          <el-input
            type="password"
            v-model="user.password"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass" class="item">
          <el-input
            type="password"
            v-model="user.checkPass"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
            type="success"
            @click="submitForm('user')"
            style="margin: 30px 80px; width: 100px"
            >提交</el-button
          >
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { Register } from "@/api/user";
import { messageShow } from "@/assets/js/Common.js";
export default {
  name: "Register",
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.user.checkPass !== "") {
          this.$refs.user.validateField("checkPass");
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.user.password) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      rules: {
        password: [
          { validator: validatePass, trigger: "blur" },
          {
            min: 8,
            max: 11,
            message: "密码长度为8-11个字符",
            trigger: ["blur", "change"],
          },
        ],
        checkPass: [{ validator: validatePass2, trigger: "blur" }],
      },
      user: {
        username: "",
        password: "",
        checkPass: "",
      },
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          Register(this.user)
            .then((res) => {
              if (res.success) {
                messageShow("success", "注册成功！");
                this.$router.push("/login");
              }
            })
            .catch((err) => {
              messageShow("error", "注册失败！" + err.message);
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    removeDomain(item) {
      var index = this.user.domains.indexOf(item);
      if (index !== -1) {
        this.user.domains.splice(index, 1);
      }
    },
    addDomain() {
      this.user.domains.push({
        value: "",
        key: Date.now(),
      });
    },
  },
};
</script>

<style lang="less" scoped>
.demo-dynamic {
  width: 500px;
  margin: 50px auto;
}
.controls {
  margin: 10px auto;
  // background-color: #333;
  padding: 30px 130px;
}
.register-container {
  position: absolute;
  width: 100%;
  height: 100%;
  background-image: url(../../assets/img/login-bg.jpg);
  background-size: 100%;
  .register {
    position: fixed;
    left: 30%;
    top: 25%;
    width: 850px;
    border: 1px solid rgb(223, 223, 223);
    background: rgba(255, 255, 255, 0.3);
    h3 {
      background: #3dc2ff;
      margin: 0;
      padding: 6px 15px;
      color: rgb(51, 51, 51);
      border-bottom: 1px solid #dfdfdf;
      font-size: 20.04px;
      line-height: 30.06px;

      span {
        font-size: 14px;
        float: right;

        a {
          color: #e1251b;
        }
      }
    }
  }
}
</style>