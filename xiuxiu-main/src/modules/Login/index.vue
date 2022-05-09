<template>
  <div class="login-wrap" v-loading="loading">
    <div class="ms-login">
      <div class="ms-title">修修便利店</div>
      <el-form
        :model="user"
        :rules="rules"
        ref="login"
        label-width="0px"
        class="ms-content"
      >
        <el-form-item prop="username">
          <el-input v-model="user.username" placeholder="username">
            <template #prepend>
              <el-button icon="el-icon-user"></el-button>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            type="password"
            placeholder="password"
            v-model="user.password"
            @keyup.enter="submitForm()"
          >
            <template #prepend>
              <el-button icon="el-icon-lock"></el-button>
            </template>
          </el-input>
        </el-form-item>
        <div class="login-btn">
          <el-button type="primary" @click="submitForm('login')"
            >登录</el-button
          >
        </div>
        <div class="call clearFix">
          <router-link class="register" to="/register">立即注册</router-link>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import { setToken } from "@/utils/token";
import { Login } from "@/api/user";
import { messageShow } from "@/assets/js/Common.js";
export default {
  name: "Login",
  data() {
    return {
      loading: false,
      user: {
        username: "",
        password: "",
      },
      rules: {
        username: [
          {
            required: true,
            message: "请输入用户名",
            trigger: "blur",
          },
        ],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
      },
    };
  },
  methods: {
    submitForm(formName) {
      this.loading = true;
      this.$refs[formName].validate((valid) => {
        if (valid) {
          Login(this.user)
            .then((res) => {
              if (res.success) {
                setToken(JSON.stringify(res.result));
                setTimeout(() => {
                  messageShow("success", "登录成功！");
                  this.loading = false;
                  this.$router.push("/home");
                }, 1000);
              } else {
                messageShow("error", "登录失败，账号或密码错误！");
                this.loading = false;
              }
            })
            .catch(() => {
              messageShow("error", "登录失败，账号或密码错误！");
              this.loading = false;
            });
        } else {
          messageShow("warning", "还有未填写的信息");
          this.loading = false;
          return false;
        }
      });
    },
  },
};
</script>

<style scoped>
.login-wrap {
  position: absolute;
  width: 100%;
  height: 100%;
  background-image: url(../../assets/img/login-bg.jpg);
  background-size: 100%;
}
.ms-title {
  width: 100%;
  line-height: 50px;
  text-align: center;
  font-size: 20px;
  color: #fff;
  border-bottom: 1px solid #ddd;
}
.ms-login {
  position: fixed;
  left: 50%;
  top: 50%;
  width: 350px;
  margin: -190px 0 0 -175px;
  border-radius: 5px;
  background: rgba(255, 255, 255, 0.3);
  overflow: hidden;
}
.ms-content {
  padding: 30px 30px;
}
.login-btn {
  text-align: center;
}
.login-btn button {
  width: 100%;
  height: 36px;
  margin-bottom: 10px;
}
.login-tips {
  font-size: 12px;
  line-height: 30px;
  color: #fff;
}
</style>
