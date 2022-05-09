<template>
  <div class="cart">
    <el-container
      style="height: 100%"
      class="background-6F9 eig-mian-doc"
      v-loading="loading"
    >
      <el-header>
        <el-form :inline="true" class="user-search">
          <el-row class="wn-row wn-row-fir" style="margin-bottom: 5px">
            <el-col :span="24" align="left">
              <el-button type="danger" size="small" @click="deleteAll"
                >全部删除</el-button
              >
            </el-col>
          </el-row>
        </el-form>
      </el-header>
      <el-main class="wnw-main">
        <el-table :data="tableData" style="width: 100%">
          <el-table-column width="40px" class="wn-table">
            <template slot-scope="{ row }">
              <el-checkbox
                v-model="row.checked"
                @change="(checked) => CheckBoxChange(checked, row[rowKey], row)"
              ></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column
            type="index"
            label="序号"
            header-align="center"
            align="center"
            class-name="wn-tableindex"
            width="60px"
          >
          </el-table-column>
          <el-table-column
            label="商品"
            prop="title"
            align="center"
            width="150px"
            show-overflow-tooltip
          ></el-table-column>
          <el-table-column
            label="图片"
            prop="cover"
            align="center"
            width="200px"
          >
            <template slot-scope="scope">
              <el-image
                style="width: 100px; height: 100px"
                :src="scope.row.cover"
              ></el-image>
            </template>
          </el-table-column>
          <el-table-column
            label="单价/元"
            prop="sellPrice"
            align="center"
          ></el-table-column>
          <el-table-column label="数量" prop="count" align="center">
            <template slot-scope="scope">
              <el-input-number
                v-model="scope.row.count"
                @change="
                  (currentValue, oldValue) =>
                    numChange(currentValue, oldValue, scope.row)
                "
                :min="1"
              >
              </el-input-number>
            </template>
          </el-table-column>

          <el-table-column
            label="金额/元"
            prop="price"
            align="center"
            width="100px"
            show-overflow-tooltip
          >
            <template slot-scope="scope">
              {{ scope.row.sellPrice * scope.row.count }}
            </template></el-table-column
          >
          <el-table-column align="center" width="150px">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="danger"
                round
                @click="handleDelete(scope.row)"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </el-main>

      <el-footer :inline="true" class="wn-footer">
        <el-row style="padding-top: 1px">
          <el-col :span="2"
            ><el-checkbox
              class="wnw-ml-50"
              style="padding: 7px 0"
              v-model="isCheckAll"
              :indeterminate="isCheckAllIndeterminate"
              @change="CheckAll"
              >全选</el-checkbox
            ></el-col
          >
          <el-col :span="16" class="align-left wnw-ml-50">
            <span style="font-size: 25px">合计：¥ {{ totalPrice }}</span>
          </el-col>
          <el-col :span="4" class="align-right wnw-mr-20">
            <el-button type="danger" round @click="settlement"
              >结算({{ checkModel.length }})</el-button
            >
          </el-col>
        </el-row>
      </el-footer>
    </el-container>
  </div>
</template>

<script>
import { currentUser } from "@/assets/js/Common";
export default {
  name: "ShopCart",
  data() {
    return {
      loading: false,
      isCheckAll: false,
      isCheckAllIndeterminate: false,
      rowKey: "id",
      checkModel: [],
      checkValue: [],
      tableData: [],
    };
  },
  mounted() {
    //  挂载
    this.getDate();
  },
  watch: {
    isCheckAll(v) {
      this.tableData.forEach((x) => {
        x.checked = v;
      });
    },
    tableData: {
      handler(v) {
        this.isCheckAllIndeterminate =
          this.checkedCount > 0 && v.length > this.checkedCount;
      },
      deep: true,
    },
  },
  computed: {
    checkedCount() {
      return this.tableData.filter((x) => x.checked).length;
    },
    totalPrice() {
      var p = 0;
      for (var i = 0; i < this.checkModel.length; i++) {
        p += this.checkModel[i].sellPrice * this.checkModel[i].count;
      }
      return p;
    },
  },
  methods: {
    getDate() {
      if (localStorage.getItem(currentUser.username + "shopCart")) {
        this.tableData = JSON.parse(
          localStorage.getItem(currentUser.username + "shopCart")
        );
      }
      for (var i = 0; i < this.tableData.length; i++) {
        this.tableData[i] = Object.assign(this.tableData[i], {
          price: this.tableData[i].sellPrice * this.tableData[i].count,
        });
      }
    },
    settlement() {
      this.$router.push("/editOrder");
    },
    // eslint-disable-next-line vue/return-in-computed-property
    numChange(curr, old, row) {
      for (var i = 0; i < this.tableData.length; i++) {
        if (this.tableData[i].id == row.id) {
          // 数量加1
          // eslint-disable-next-line vue/no-side-effects-in-computed-properties
          this.tableData[i].count = curr;
          break;
        }
      }
      localStorage.setItem(
        currentUser.username + "shopCart",
        JSON.stringify(this.tableData)
      );
    },
    handleDelete(row) {
      for (var i = 0; i < this.tableData.length; i++) {
        if (this.tableData[i].id == row.id) {
          this.tableData.splice(row, 1);
          break;
        }
      }
      localStorage.setItem(
        currentUser.username + "shopCart",
        JSON.stringify(this.tableData)
      );
    },
    deleteAll() {
      this.$confirm("是否确定全部删除？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.tableData = [];
        localStorage.removeItem(currentUser.username + "shopCart");
        this.$message({
          type: "success",
          message: "删除成功!",
        });
      });
    },
    CheckBoxChange(cheked, rowKey, value) {
      if (cheked) {
        this.checkModel.push(value);
        this.checkValue.push(rowKey);
      } else {
        let entity = this.checkModel.findIndex((item) => {
          if (item.id == rowKey) {
            return true;
          }
        });
        this.checkModel.splice(entity, 1);
        this.checkValue.splice(rowKey, 1);
      }
    },
    //全选事件
    CheckAll(bo) {
      if (bo) {
        if (this.tableData && this.tableData.length > 0) {
          var check = [];
          var newobj = [];
          for (let i = 0; i < this.tableData.length; i++) {
            newobj.push(this.tableData[i]);
            check.push(this.tableData[i][this.rowKey]);
          }
          this.checkModel = newobj;
          this.checkValue = check;
        }
      } else {
        this.checkValue = [];
        this.checkModel = [];
      }
    },
  },
};
</script>

<style lang="less" scoped>
.cart {
  width: 1200px;
  height: 850px;
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
          background: rgb(103, 194, 58);
          overflow: hidden;
        }
      }
    }
  }
}
</style>
