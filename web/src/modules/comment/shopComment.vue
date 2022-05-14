<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb
      separator-class="el-icon-arrow-right"
      style="margin-bottom: 10px"
    >
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>门店评价</el-breadcrumb-item>
    </el-breadcrumb>
    <el-container
      style="height: 100%"
      class="background-6F9 eig-mian-doc"
      v-loading="loading"
    >
      <el-main class="wnw-main">
        <div class="main">
          <ul>
            <li v-for="item in comments" :key="item.id">
              <div class="msg">
                <el-image :src="item.userImg" alt=""></el-image>
                <span class="msg-span">{{ item.username }}</span>
              </div>
              <div class="comment">
                <el-rate
                  v-model="item.score"
                  disabled
                  show-score
                  text-color="#ff9900"
                  style="margin-left: 20px"
                >
                </el-rate>
                <span style="font-size: 16px" class="comment-span">{{
                  item.content
                }}</span>

                <el-carousel
                  trigger="click"
                  height="400px"
                  width="300px"
                  class="pic"
                >
                  <el-carousel-item v-for="pic in item.images" :key="pic">
                    <el-image style="width: 100%; height: 100%" :src="pic" />
                  </el-carousel-item>
                </el-carousel>
                <el-divider></el-divider>
              </div>
            </li>
          </ul>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import { GetCommentList } from "@/api/orderComment.js";
export default {
  name: "Comments",
  data() {
    return {
      comments: [],
      loading: false,
    };
  },
  mounted() {
    this.loadData();
  },
  methods: {
    loadData() {
      this.loading = true;
      GetCommentList().then((res) => {
        if (res.success) {
          this.comments = res.result;
          for (var i = 0; i < this.comments.length; i++) {
            this.comments[i].images = JSON.parse(this.comments[i].images);
          }
          this.loading = false;
        }
      });
    },
  },
};
</script>
<style  scoped>
.el-carousel__item h3 {
  font-size: 14px;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
}
.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}

.pic {
  width: 500px;
  margin: 20px auto;
}
.comment {
  padding-bottom: 30px;
}

.comment-span {
  text-indent: 30px;
  display: inline-block;
  margin-left: 40px;
  width: 600px;
  margin-top: 20px;
}

.msg {
  display: flex;
  height: 80px;
  line-height: 80px;
  margin-left: 20px;
}

.msg-img {
  margin-top: 15px;
  border-radius: 50%;
  height: 50px;
  width: 50px;
}
.msg-span {
  margin-left: 20px;
  font-size: 14px;
  font-weight: bold;
}
.main {
  box-shadow: 2px 2px 5px rgb(164, 160, 160);
  margin: 20px auto;
  width: 1000px;

  border-top: none;
  border-bottom: none;
}
li {
  list-style: none;
}
</style>