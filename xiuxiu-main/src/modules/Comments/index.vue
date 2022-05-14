<template>
  <div class="main" v-loading="loading">
    <ul>
      <li v-for="item in comments" :key="item.id">
        <div class="msg">
          <el-image :src="item.userImg" alt=""></el-image>
          <span>{{ item.username }}</span>
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
          <span style="font-size: 16px">{{ item.content }}</span>

          <el-carousel trigger="click" height="400px" width="300px" class="pic">
            <el-carousel-item v-for="(pic, index) in item.images" :key="index">
              <img :src="pic" alt="" height="100%" width="100%" />
            </el-carousel-item>
          </el-carousel>
          <el-divider></el-divider>
        </div>
      </li>
    </ul>
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
      GetCommentList()
        .then((res) => {
          if (res.success) {
            this.comments = res.result;
            for (var i = 0; i < this.comments.length; i++) {
              this.comments[i].images = JSON.parse(this.comments[i].images);
            }
            this.loading = false;
          }
        })
        .catch(() => {
          this.loading = false;
        });
    },
  },
};
</script>
<style lang='less' scoped>
.el-carousel__item h3 {
  font-size: 14px;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
}
.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
.main {
  box-shadow: 2px 2px 5px rgb(164, 160, 160);
  margin: 20px auto;
  height: 100%;
  width: 800px;

  //   background-color: aqua;
  // border: 1px solid rgb(187, 179, 179);
  border-top: none;
  border-bottom: none;

  ul {
    li {
      // border-bottom: 1px dashed rgb(187, 179, 179);
      .msg {
        display: flex;
        height: 80px;
        line-height: 80px;
        margin-left: 20px;
        img {
          margin-top: 15px;
          border-radius: 50%;
          height: 50px;
          width: 50px;
        }
        span {
          margin-left: 20px;
          font-size: 14px;
          font-weight: bold;
        }
      }
      .comment {
        padding-bottom: 30px;
        span {
          text-indent: 30px;
          display: inline-block;
          margin-left: 40px;
          width: 600px;
          margin-top: 20px;
        }
        .pic {
          width: 500px;
          // height: 20px;
          // background-color: aqua;
          margin: 20px auto;
        }
      }
    }
  }
}
</style>