<template>
  <div class="cart">
    <h4>购物车</h4>
    <div class="cart-main">
      <div class="cart-th">
        <div class="cart-th1">全部</div>
        <div class="cart-th2">商品</div>
        <div class="cart-th3">单价（元）</div>
        <div class="cart-th4">数量</div>
        <div class="cart-th5">金额（元）</div>
        <div class="cart-th6">删除</div>
      </div>
      <div class="cart-body">
        <ul class="cart-list" v-for="product in products" :key="product.id">
          <li class="cart-list-con1">
            <input type="checkbox" name="chk_list" :checked='product.isChecked===1' @click="changeChecked(product)"/>
          </li>
          <li class="cart-list-con2">
            <img src="./images/goods1.png" />
            <div class="item-msg">{{product.name}}</div>
          </li>
          <li class="cart-list-con4">
            <span class="price">{{ product.price }}</span>
          </li>
          <li class="cart-list-con5">
            <el-input-number
              v-model="product.num"
             
              :min="1"
              :max="10"
              label="描述文字"
              size="mini"
            ></el-input-number>
          </li>
          <li class="cart-list-con6">
            <span class="sum">{{product.price*product.num }}</span>
          </li>
          <li class="cart-list-con7">
            <a href="#none" class="sindelet" @click="deleteItem(product.id)">删除</a>
          </li>
        </ul>
      </div>
    </div>
    <div class="cart-tool">
      <div class="select-all">
        <input class="chooseAll" type="checkbox" :checked='isAllChecked'   @change="updateAllChecked($event)"/>
        <span>全选</span>
      </div>
      <div class="money-box">
        <div class="chosed">已选择 <span>{{totalList}}</span>件商品</div>
        <div class="sumprice">
          <em>合计 ：</em>
          <i class="summoney">{{totalPrice}}</i>
        </div>
        <div class="sumbtn">
          <a class="sum-btn"  @click="pay">结算</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "ShopCart",
  data() {
    return {
      products: [
        {
          isChecked:1,
          name:'乌龙茶',
          id:'1',
          price: 399,
          num: 1,
        },
        {isChecked:1,
           name:'乌龙茶',
          id:'5',
          price: 399,
          num: 1,
        },
      ],
    };

    // totalPrice:num*price
  },
  mounted() {
    //  挂载
    // this.getData();
  },
  methods: {
    changeChecked(product){
         product.isChecked = product.isChecked===1?0:1
    },
    
    updateAllChecked(e){
      let checked = e.target.checked?1:0
      this.products.forEach(item=>{
        item.isChecked = checked
      })
    },
    // 删除商品
    deleteItem(id){
     
     this.products = this.products.filter(item=>{
       
       return id !== item.id
      })
     
      
    },
    // 结算成功
    pay(){
      let count = 0
      this.products.forEach(item=>{
        if(item.isChecked===1) count++
      })
      if(count!==0) return console.log('支付成功');
      console.log('支付失败');
    }

  },
  computed: {
  //  是否全选
  isAllChecked() {
      return this.products.every((item) => item.isChecked === 1);
    },
    // 选中商品数量
    totalList(){
       let sum = 0;
      sum = this.products
        .filter((item) => item.isChecked === 1)
        .reduce((total, item) => {
          return total + item.num;
        }, 0);

      return sum;
     
    },
    // 选中商品的价格
    totalPrice(){
      let sum = 0;
      sum = this.products
        .filter((item) => item.isChecked === 1)
        .reduce((total, item) => {
          return total + item.price * item.num;
        }, 0);

      return sum;
    }
  },
};
</script>

<style lang="less" scoped>
.cart {
  width: 1200px;
  margin: 0 auto;

  h4 {
    margin: 9px 0;
    font-size: 14px;
    line-height: 21px;
  }

  .cart-main {
    .cart-th {
      background: #f5f5f5;
      border: 1px solid #ddd;
      padding: 10px;
      overflow: hidden;

      & > div {
        float: left;
      }

      .cart-th1 {
        width: 25%;

        input {
          vertical-align: middle;
        }

        span {
          vertical-align: middle;
        }
      }

      .cart-th2 {
        width: 25%;
      }

      .cart-th3,
      .cart-th4,
      .cart-th5,
      .cart-th6 {
        width: 12.5%;
      }
    }

    .cart-body {
      margin: 15px 0;
      border: 1px solid #ddd;

      .cart-list {
        height: 70px;
        line-height: 70px;
        padding: 10px;
        border-bottom: 1px solid #ddd;
        overflow: hidden;

        & > li {
          float: left;
        }

        .cart-list-con1 {
          width: 15%;
        }

        .cart-list-con2 {
          width: 35%;

          img {
            width: 82px;
            height: 82px;
            float: left;
          }

          .item-msg {
            float: left;
            width: 150px;
            margin: 20px 10px;
            line-height: 18px;
          }
        }

        .cart-list-con4 {
          width: 10%;
        }

        .cart-list-con5 {
          width: 17%;

          .mins {
            border: 1px solid #ddd;
            border-right: 0;
            float: left;
            color: #666;
            width: 6px;
            text-align: center;
            padding: 8px;
          }

          input {
            border: 1px solid #ddd;
            width: 40px;
            height: 33px;
            float: left;
            text-align: center;
            font-size: 14px;
          }

          .plus {
            border: 1px solid #ddd;
            border-left: 0;
            float: left;
            color: #666;
            width: 6px;
            text-align: center;
            padding: 8px;
          }
        }

        .cart-list-con6 {
          width: 10%;

          .sum {
            font-size: 16px;
          }
        }

        .cart-list-con7 {
          width: 13%;

          a {
            color: #666;
          }
        }
      }
    }
  }

  .cart-tool {
    overflow: hidden;
    border: 1px solid #ddd;

    .select-all {
      padding: 10px;
      overflow: hidden;
      float: left;

      span {
        vertical-align: middle;
      }

      input {
        vertical-align: middle;
      }
    }

    .option {
      padding: 10px;
      overflow: hidden;
      float: left;

      a {
        float: left;
        padding: 0 10px;
        color: #666;
      }
    }

    .money-box {
      float: right;

      .chosed {
        line-height: 26px;
        float: left;
        padding: 0 10px;
      }

      .sumprice {
        width: 200px;
        line-height: 22px;
        float: left;
        padding: 0 10px;

        .summoney {
          color: #c81623;
          font-size: 16px;
        }
      }

      .sumbtn {
        float: right;

        a {
          display: block;
          position: relative;
          width: 96px;
          height: 52px;
          line-height: 52px;
          color: #fff;
          text-align: center;
          font-size: 18px;
          font-family: "Microsoft YaHei";
          background: rgb(103,194,58);
          overflow: hidden;
        }
      }
    }
  }
}
</style>
