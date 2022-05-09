<template>
  <div class="main">
    <ul>
      <li v-for="item in comments" :key="item.id">
        <div class="msg">
          <img :src="item.userImg" alt="" />
          <span>{{ item.useName }}</span>
        </div>
        <div class="comment">
          <el-rate
            v-model="item.score"
            disabled
            show-score
            text-color="#ff9900"
           style="margin-left:20px"
          >
          </el-rate>
          <span style="font-size:16px">{{ item.content }}</span>

          <el-carousel
            trigger="click"
            height="200px"
            width="300px"
            class="pic"
            
          >
            <el-carousel-item v-for="(pic, index) in item.images" :key="index" >
              <img :src="pic" alt="" height="100%" width="100%">
            </el-carousel-item>
          </el-carousel>
          <el-divider></el-divider>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "Comments",
  data() {
    return {
      comments: [
        {
          // 评分
          score:4.5,
          id: "1",
          // 订单id
          orderId: "123",
          userId: "222",
          // 用户名

          useName: "jioajfoia",
          // 用户头像
          userImg:
            "https://img1.baidu.com/it/u=671076267,3648872689&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
          // 评论内容
          content: "真不错啊",
          // 评论图片
          images: [
            "https://img1.baidu.com/it/u=2244213269,3659097181&fm=253&fmt=auto&app=138&f=JPEG?w=750&h=500",
            "https://img1.baidu.com/it/u=671076267,3648872689&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
            "https://img1.baidu.com/it/u=2244213269,3659097181&fm=253&fmt=auto&app=138&f=JPEG?w=750&h=500",
            "https://img1.baidu.com/it/u=2244213269,3659097181&fm=253&fmt=auto&app=138&f=JPEG?w=750&h=500",
          ],
          // 点赞数量
          numPraise: 100,
          // 创建时间
          CreateTime: "",
          // 显示序号
          ViewOrder: 55,
          // 状态
          Status: 1,
          // 更新时间
          TimeStamp: "12",
        },
        {
          // 评分
          score:4.5,
          id: "2",
          // 订单id
          orderId: "123",
          userId: "222",
          // 用户名

          useName: "jioajfoia",
          // 用户头像
          userImg:
            "https://img1.baidu.com/it/u=671076267,3648872689&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
          // 评论内容
          content: "真不错啊",
          // 评论图片
          images: [
            "https://img1.baidu.com/it/u=671076267,3648872689&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
            "https://img1.baidu.com/it/u=671076267,3648872689&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
          ],
          // 点赞数量
          numPraise: 100,
          // 创建时间
          CreateTime: "",
          // 显示序号
          ViewOrder: 55,
          // 状态
          Status: 1,
          // 更新时间
          TimeStamp: "12",
        },
        {
          // 评分
          score:4.5,
          id: "3",
          // 订单id
          orderId: "123",
          userId: "222",
          // 用户名

          useName: "jioajfoia",
          // 用户头像
          userImg:
            "https://img1.baidu.com/it/u=671076267,3648872689&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
          // 评论内容
          content: "真不错啊",
          // 评论图片
          images: [
            "https://img1.baidu.com/it/u=671076267,3648872689&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
            "https://img1.baidu.com/it/u=671076267,3648872689&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
          ],
          // 点赞数量
          numPraise: 100,
          // 创建时间
          CreateTime: "",
          // 显示序号
          ViewOrder: 55,
          // 状态
          Status: 1,
          // 更新时间
          TimeStamp: "12",
        },
        {
          // 评分
          score:4.5,
          id: "4",
          // 订单id
          orderId: "123",
          userId: "222",
          // 用户名

          useName: "jioajfoia",
          // 用户头像
          userImg:
            "https://img1.baidu.com/it/u=671076267,3648872689&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
          // 评论内容
          content: "真不错啊",
          // 评论图片
          images: [
            "https://img1.baidu.com/it/u=671076267,3648872689&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
            "https://img1.baidu.com/it/u=671076267,3648872689&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
          ],
          // 点赞数量
          numPraise: 100,
          // 创建时间
          CreateTime: "",
          // 显示序号
          ViewOrder: 55,
          // 状态
          Status: 1,
          // 更新时间
          TimeStamp: "12",
        },
        {
          // 评分
          score:4.5,
          id: "5",
          // 订单id
          orderId: "123",
          userId: "222",
          // 用户名

          useName: "jioajfoia",
          // 用户头像
          userImg:
            "https://img1.baidu.com/it/u=671076267,3648872689&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
          // 评论内容
          content: "真不错啊",
          // 评论图片
          images: [
            "https://img1.baidu.com/it/u=671076267,3648872689&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
            "https://img1.baidu.com/it/u=671076267,3648872689&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
          ],
          // 点赞数量
          numPraise: 100,
          // 创建时间
          CreateTime: "",
          // 显示序号
          ViewOrder: 55,
          // 状态
          Status: 1,
          // 更新时间
          TimeStamp: "12",
        },
        {
          // 评分
          score:4.5,
          id: "6",
          // 订单id
          orderId: "123",
          userId: "222",
          // 用户名

          useName: "jioajfoia",
          // 用户头像
          userImg:
            "https://img1.baidu.com/it/u=671076267,3648872689&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
          // 评论内容
          content: "真不错啊",
          // 评论图片
          images: [
            "https://img1.baidu.com/it/u=671076267,3648872689&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
            "https://img1.baidu.com/it/u=671076267,3648872689&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
          ],
          // 点赞数量
          numPraise: 100,
          // 创建时间
          CreateTime: "",
          // 显示序号
          ViewOrder: 55,
          // 状态
          Status: 1,
          // 更新时间
          TimeStamp: "12",
        },
      ],
    };
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

// .el-carousel__item:nth-child(2n) {
//    background-color: #99a9bf;
// }

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