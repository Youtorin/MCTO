<template>
  <div style="width: 70%; left: 15%; position: absolute">
    <el-container
      style="height: 100%; align: center"
      class="background-6F9 eig-mian-doc"
      v-loading="loading"
    >
      <el-aside width="200px" style="background-color: rgb(255, 255, 255)">
        <el-menu :default-openeds="['1', '2', '3']">
          <el-submenu index="1">
            <template #title><i class="el-icon-goods"></i>商品分类</template>
            <el-menu-item-group>
              <el-menu-item
                v-for="item in cateList"
                :key="item.id"
                v-model="item.id"
                @click="handleChangeCate(item)"
                ><span style="color: black">{{ item.name }}</span></el-menu-item
              >
            </el-menu-item-group>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-container
        style="height: 100%"
        class="background-6F9 eig-mian-doc wnw-ml-10"
      >
        <el-header>
          <el-row class="wn-row wn-row-fir" style="margin-bottom: 5px">
            <el-col :span="24" align="right">
              <el-input
                v-model.trim="param.keywords"
                maxlength="50"
                placeholder="请输入关键字"
                class="wn-input-group wnw-left wnw-ml-20"
              >
              </el-input>
              <el-button
                icon="el-icon-search"
                style="float: left; margin: 5px 0px"
                type="success"
                size="mini"
                @click="Search"
                circle
              />
            </el-col>
          </el-row>
        </el-header>
        <el-main class="wnw-main">
          <div class="rank">
            <div class="content">
              <ul>
                <li v-for="food in foodList" :key="food.id">
                  <img
                    :src="food.cover"
                    alt=""
                    @click="details(food)"
                    class="mouse-hand"
                  />
                  <p>{{ food.title }}</p>
                  <div class="content-list">
                    <span>￥{{ food.sellPrice }}.0</span>
                    <el-button
                      type="success"
                      @click="details(food)"
                      style="padding-top: 8px"
                      >立即购买</el-button
                    >
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </el-main>

        <el-footer class="wn-footer">
          <el-row style="padding-top: 1px">
            <el-col :span="24" class="align-left" style="margin-left: 20px">
              <el-pagination
                :background="true"
                :page-sizes="[12]"
                :pager-count="5"
                layout="total, sizes, prev, pager, next, jumper"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :total="total"
              ></el-pagination>
            </el-col>
          </el-row>
        </el-footer>
      </el-container>
    </el-container>

    <el-dialog
      append-to-body
      :visible="detailsVisible"
      title="商品信息"
      width="20%"
    >
      <el-card :body-style="{ padding: '0px' }">
        <img :src="form.cover" class="image" />
        <div style="padding: 14px">
          <el-tag>{{ form.cateName }}</el-tag>
          <span
            class="margin-left-15"
            style="font-size: 16px; font-weight: bold"
            >{{ form.title }}</span
          >
        </div>
        <div style="padding: 5px 14px; font-size: 18px; font-weight: bold">
          {{ form.description }}
        </div>
        <div
          style="
            padding: 5px 14px;
            font-size: 30px;
            color: red;
            font-weight: bolder;
          "
        >
          ¥ {{ form.sellPrice }}
        </div>
        <div style="padding: 5px 14px; font-size: 16px">
          库存：{{ form.number }}&nbsp;&nbsp;&nbsp;&nbsp; 月销量：{{
            form.monthSales
          }}&nbsp;&nbsp;&nbsp;&nbsp; 总销量：{{ form.totalSales }}
        </div>
      </el-card>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="addShoping" type="danger" round
            >加入购物车</el-button
          >
          <el-button @click="addShoping" type="warning" round
            >立即购买</el-button
          >
          <el-button @click="detailsVisible = false" type="info" round
            >关闭</el-button
          >
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { GetCateList } from "@/api/foodCategory.js";
import { GetList } from "@/api/food.js";
export default {
  name: "Rank",
  data() {
    return {
      loading: false,
      detailsVisible: false,
      cateList: [], //分类列表
      foodList: [], //商品列表
      total: 0,
      rowKey: "id",
      checkModel: [],
      checkValue: [],
      form: {
        cateId: "",
        cover: "",
        title: "",
        discount: 0,
        createTime: "",
        description: "",
        count: 0,
      },
      param: {
        keywords: "",
        pagination: {
          cateId: "",
          rows: 12,
          page: 1,
          sidx: "CreateTime",
          sord: "desc",
        },
      },
      shopingList: [],
    };
  },
  mounted() {
    this.loaddata();
  },
  methods: {
    Search() {
      this.loaddata();
    },
    details(row) {
      this.detailsVisible = true;
      this.form = row;
    },
    addShoping() {
      var flag = true;
      // 判断购物车中是否已经存在该商品
      for (var i = 0; i < this.shopingList.length; i++) {
        if (this.shopingList[i].id == this.form.id) {
          // 存在则数量加1
          this.shopingList[i].count++;
          flag = false;
          break;
        }
      }
      // 购物车中不存在该商品-首次添加
      if (flag) {
        this.form.count = 1;
        this.shopingList.push(this.form);
      }
      // localStorage.setItem("shopCart", JSON.stringify(this.shopingList));
      // console.log(JSON.parse(localStorage.getItem("shopCart")));
    },
    async loaddata() {
      this.loading = true;
      await GetCateList()
        .then((res) => {
          if (res.success && res.result) {
            this.cateList = res.result;
            this.cateList.unshift({
              name: "全部分类",
              id: "",
            });
          }
        })
        .catch((err) => {
          console.log(err);
        });
      await GetList(this.param)
        .then((res) => {
          if (res.success && res.result) {
            this.foodList = res.result.items;
            this.total = res.result.totalcount;
          }
        })
        .catch((err) => {
          console.log(err);
        });
      console.log(this.foodList);
      this.loading = false;
    },
    handleCurrentChange(page) {
      this.param.pagination.page = page;
      this.loaddata();
    },
    handleSizeChange(rows) {
      this.param.pagination.page = 1;
      this.param.pagination.rows = rows;
      this.loaddata();
    },
    handleChangeCate(val) {
      this.param.pagination.cateId = val.id;
      this.loaddata();
    },
  },
};
</script>

<style lang="less">
::-webkit-scrollbar {
  display: none; /* Chrome Safari */
}
.image {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}

.fontSize {
  font-size: 20px;
  margin-bottom: 0px;
}
.rank {
  width: 1250px;
  margin: 0 auto;

  .tab {
    height: 100px;
    margin: 0 auto;
    overflow: hidden;
    width: 1000px;
    display: flex;
    justify-content: space-between;
    li {
      line-height: 100px;
      span {
        margin-right: 10px;
        font-weight: bold;
        font-size: 13px;
      }
    }
  }

  .content {
    height: 800px;
    width: 1250px;

    ul {
      display: flex;
      flex-wrap: wrap;
      justify-content: space-between;

      li {
        // border: 1px solid rgb(182, 178, 178);
        box-shadow: 2px 2px 5px rgb(164, 160, 160);
        width: 280px;
        height: 350px;
        // background-color: aqua;
        margin-top: 20px;

        img {
          height: 260px;
          width: 280px;

          // margin-left: 5px;
        }
        p {
          margin: 10px 15px 10px 15px;
          color: rgb(103, 194, 58);
        }
        .content-list {
          width: 250px;
          margin: 0 auto;
          height: 30px;
          display: flex;
          span {
            width: 200px;
            font-size: 16px;
          }
        }
      }
    }
  }
}
</style>
