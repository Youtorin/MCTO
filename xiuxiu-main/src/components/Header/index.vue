<template>
  <div class="top">
    <h2>修修便利店</h2>
    <div class="topRight">
      <span style="font-size: 18px">欢迎您：{{ username }}</span>
      <el-menu
        :default-active="this.$route.path"
        class="el-menu-demo"
        mode="horizontal"
        active-text-color="rgb(103,194,58)"
        @select="handleSelect"
        router
      >
        <el-menu-item index="/home">全部商品</el-menu-item>
        <el-menu-item index="/comments">店内评价</el-menu-item>
        <el-menu-item index="/shopcart">购物车</el-menu-item>
        <el-menu-item index="/mypay">我的订单</el-menu-item>

        <el-menu-item index="/detail">我的资料</el-menu-item>
        <el-menu-item @click="loginout">退出</el-menu-item>
      </el-menu>
    </div>
  </div>
</template>

<script>
export default {
  name: "Header",
  data() {
    return {
      username: JSON.parse(localStorage.getItem("TOKEN")).username,
    };
  },
  computed: {},
  created() {},
  methods: {
    handleSelect(key) {
      this.activeIndex = key;
    },
    loginout() {
      localStorage.removeItem("TOKEN");
      this.$router.push("/login");
    },
  },
};
</script>
<style lang='less' scoped>
.top {
  width: 1200px;
  height: 100px;
  margin: 0 auto;
  position: relative;

  // background: rebeccapurple;
  h2 {
    position: absolute;
    font-size: 20px;
    color: rgb(103, 194, 58);
    line-height: 100px;
  }

  .topRight {
    position: absolute;
    right: 600px;
    // margin-left: 400px;

    line-height: 100px;

    .el-menu-demo {
      position: absolute;
      margin-left: 150px;
      margin-top: 20px;
      line-height: 400px;
      border-bottom: none;
      display: flex;
      top: 0;
      //  right: 300px;
    }
  }
}
</style>