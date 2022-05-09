<template>
  <div class="container">
    <div class="order-body">
      <div class="order-right">
        <div class="title">
          <h3>我的订单</h3>
        </div>
        <div class="chosetype">
          <table>
            <thead>
              <tr>
                <th width="29%">商品</th>
                <th width="31%">订单详情</th>
                <th width="13%">收货人</th>
                <th>金额</th>
                <th>状态</th>
                
              </tr>
            </thead>
          </table>
        </div>
        <div class="orders">
          <table
            class="order-item"
            v-for="orders in Order"
            :key="orders.id"
          >
            <thead>
              <tr>
                <th colspan="5">
                  <span class="ordertitle"
                    >订单编号：{{ orders.orderId }}
                    
                      <!-- <img :src="order.orderDetailList[0].imgUrl" /> -->
                   <span style="margin-left:20px">{{orders.CreateTime}}</span>
                    
                  </span
                  >
                </th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(cart, index) in orders.order" :key="cart.id">
                <td width="50%">
                  <div class="typographic">
                    <img
                      :src="cart.images"
                      style="height: 100px; width: 100px"
                    />
                    <a  class="block-text" style="margin-top:40px;margin-left:80px">{{ cart.title }}</a>
                    <span style="margin-top:40px">x{{cart.num}}</span>
  
                  </div>
                </td>
                <td
                  :rowspan="cart.id"
                  v-if="index === 0"
                  width="17%"
                  class="center"
                >
                  {{ orders.name }}
                </td>
                <td
                  :rowspan="cart.id"
                  v-if="index === 0"
                  width="15%"
                  class="center"
                >
                  <ul class="unstyled">
                    <li>总金额：￥{{ orders.totalMoney }}</li>
                    <li style="font-size:17px;color:red;font-weight:bolder;margin-top:5px">实付：￥{{ orders.payMoney }}</li>
                  </ul>
                </td>
                <td
                  :rowspan="cart.id"
                  v-if="index === 0"
                  width="18%"
                  class="center"
                >
                  <el-button type="success" size="mini" v-if="orders.Status===1">立即评价</el-button>
                  <a href="#" class="btn" v-if="orders.Status===0">已完成 </a>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <el-pagination background layout="prev, pager, next" :total="1000" style="margin:20px 0">
    </el-pagination>
    </div>
  </div>
  </div>
</template>

<script>
export default {
  name: "MyPay",
  data() {
    return {
     Order:[
       {name:'小华',
         id:'1',
         shopId:'222',
         orderId:'444',
         userId:'333',
         totalMoney:'399',
         payMoney:'399',
         CreateTime:'2022-2-1',
        //  0表示已评价，1表示未评价
         Status:1,
         order:[
           {
            //  商品图片
             images:'https://img0.baidu.com/it/u=3883847948,421492166&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=501',
             title:'奶茶',
             id:'11',
          // 数量
             num:1,
            


           },{
            //  商品图片
             images:'https://img0.baidu.com/it/u=3883847948,421492166&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=501',
             title:'奶茶',
             id:'11',
          // 数量
             num:1,
           }
         ]
       },
         {name:'小华',
         id:'1',
         shopId:'222',
         orderId:'444',
         userId:'333',
         totalMoney:'399',
         payMoney:'399',
         CreateTime:'2022-2-1',
        //  0表示已评价，1表示未评价
         Status:0,
         order:[
           {
            //  商品图片
             images:'https://img0.baidu.com/it/u=3883847948,421492166&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=501',
             title:'奶茶',
             id:'11',
          // 数量
             num:1,
            


           },{
            //  商品图片
             images:'https://img0.baidu.com/it/u=3883847948,421492166&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=501',
             title:'奶茶',
             id:'11',
          // 数量
             num:1,
           }
         ]
       },
     ]
    };
  },
 
  methods: {
   
   
  },
};
</script>

<style lang="less" scoped>
.container {
  margin: 0 auto;
  width: 1200px;

  .order-body {
    padding: 10px;
    color: #333;

    &:after {
      content: "";
      display: block;
      clear: both;
    }

    //右边
    .order-right {
      float: right;
      width: 83.33%;

      margin: 20px 100px;
      color: #666;

      //标题
      .title {
        margin-bottom: 22px;
        border: 1px solid #ddd;

        h3 {
          padding: 12px 10px;
          font-size: 15px;
          background-color: #f1f1f1;
        }
      }

      //表头
      .chosetype {
        margin-bottom: 15px;
        color: #666;

        table {
          border: 1px solid #e6e6e6;
          border-collapse: separate;
          border-radius: 2px;
          width: 100%;
          max-width: 100%;
          border-spacing: 0;

          th {
            padding: 6px 8px;
            color: #666;
            font-weight: 700;
            vertical-align: bottom;
            background-color: #f4f4f4;
            line-height: 18px;
            border-bottom: 1px solid #e6e6e6;
            font-size: 12px;
            text-align: left;
          }
        }
      }

      // 表单内容
      .orders {
        font-size: 12px;

        a {
          &:hover {
            color: #4cb9fc;
          }
        }

        table {
          border: 1px solid #e6e6e6;
          border-collapse: collapse;
          border-radius: 2px;
          width: 100%;
          margin-bottom: 18px;
          max-width: 100%;

          th {
            padding: 6px 8px;
            line-height: 18px;
            text-align: left;
            vertical-align: bottom;
            background-color: #f4f4f4;
            font-size: 12px;
            color: #666;

            .pull-right {
              float: right;
              cursor: pointer;

              img {
                margin-right: 10px;
                vertical-align: middle;
              }
            }
          }

          td {
            font-size: 12px;
            color: #666;
            padding: 6px 8px;
            line-height: 18px;
            text-align: left;
            vertical-align: middle;
            border: 1px solid #e6e6e6;

            &.center {
              text-align: center;
            }

            .typographic {
              img {
                float: left;
                margin-right: 10px;
              }

              a {
                float: left;
                min-width: 80px;
                max-width: 250px;
                color: #e1251b;

                &.service {
                  color: #666;
                }
              }

              span {
                float: left;
                min-width: 80px;
                max-width: 250px;
                text-align: center;
              }
            }
          }
        }
      }

      // 分页
      .choose-order {
        .pagination {
          margin: 38px 0;

          ul {
            font-size: 0;
            display: inline-block;

            li {
              display: inline-block;
              padding: 4px 9px;
              font-size: 14px;
              border: 1px solid #e0e9ee;

              &.prev,
              &.next {
                background-color: #fafafa;
              }

              &.prev {
                border-right-color: #28a3ef;
              }

              &.page {
                border-color: #28a3ef;
                border-left: 0;

                &.actived {
                  background-color: #28a3ef;

                  a {
                    color: #fff;
                  }
                }
              }
            }
          }

          div {
            display: inline-block;
            font-size: 14px;
            color: #333;
          }
        }
      }

  
    }
  }
}
</style>