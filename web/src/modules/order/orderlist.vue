/** 订单列表 */
<template>
  <el-container
    style="height: 100%"
    class="background-6F9 eig-mian-doc"
    v-loading="loading"
  >
    <!-- 面包屑导航 -->
    <el-breadcrumb
      separator-class="el-icon-arrow-right"
      style="margin-bottom: 10px"
    >
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>订单管理</el-breadcrumb-item>
    </el-breadcrumb>
    <div height="100px" style="background-color: white">
      <el-row :gutter="20" style="margin: 5px 0">
        <el-col :span="5">
          <el-card
            shadow="hover"
            style="height: 100px; background-color: rgba(156, 205, 90)"
          >
            <el-row>
              <el-col :span="24" class="elRowText"><span>34522</span></el-col>
            </el-row>
            <el-row>
              <el-col
                :span="24"
                style="
                  text-align: center;
                  color: white;
                  margin-top: -5px;
                  font-size: 16px;
                "
                ><span>全部订单</span></el-col
              >
            </el-row>
          </el-card>
        </el-col>
        <el-col :span="5">
          <el-card
            shadow="hover"
            style="height: 100px; background-color: rgba(255, 88, 88)"
          >
            <el-row>
              <el-col :span="24" class="elRowText"><span>1210</span></el-col>
            </el-row>
            <el-row>
              <el-col
                :span="24"
                style="
                  text-align: center;
                  color: white;
                  margin-top: -5px;
                  font-size: 16px;
                "
                ><span>已完成订单</span></el-col
              >
            </el-row>
          </el-card>
        </el-col>
        <el-col :span="4">
          <el-card
            shadow="hover"
            style="height: 100px; background-color: rgba(209, 92, 90)"
          >
            <el-row>
              <el-col :span="24" class="elRowText"><span>34522</span></el-col>
            </el-row>
            <el-row>
              <el-col
                :span="24"
                style="
                  text-align: center;
                  color: white;
                  margin-top: -5px;
                  font-size: 16px;
                "
                ><span>退款订单</span></el-col
              >
            </el-row>
          </el-card>
        </el-col>
        <el-col :span="5">
          <el-card
            shadow="hover"
            style="height: 100px; background-color: rgba(173, 150, 214)"
          >
            <el-row>
              <el-col :span="24" class="elRowText"><span>34522</span></el-col>
            </el-row>
            <el-row>
              <el-col
                :span="24"
                style="
                  text-align: center;
                  color: white;
                  margin-top: -5px;
                  font-size: 16px;
                "
                ><span>待出库订单</span></el-col
              >
            </el-row>
          </el-card>
        </el-col>
        <el-col :span="5">
          <el-card
            shadow="hover"
            style="height: 100px; background-color: rgba(8, 133, 209)"
          >
            <el-row>
              <el-col :span="24" class="elRowText"><span>34522</span></el-col>
            </el-row>
            <el-row>
              <el-col
                :span="24"
                style="
                  text-align: center;
                  color: white;
                  margin-top: -5px;
                  font-size: 16px;
                "
                ><span>待收货订单</span></el-col
              >
            </el-row>
          </el-card>
        </el-col>
      </el-row>
    </div>
    <el-header>
      <el-form :inline="true" :model="param" class="user-search">
        <el-row class="wn-row wn-row-fir" style="margin-bottom: 5px">
          <el-col :span="5" align="right">
            <el-input
              v-model.trim="keyword"
              maxlength="50"
              placeholder="请输入关键字"
              class="wn-input-group wnw-left wnw-ml-20"
            >
            </el-input>
            <el-button
              icon="el-icon-search"
              style="margin: 8px 0; margin-right: 35px"
              type="success"
              size="mini"
              @click="Search"
              circle
            />
          </el-col>
        </el-row>
      </el-form>
    </el-header>
    <el-main class="wnw-main">
      <el-table :data="tableData" style="width: 100%">
        <el-table-column
          type="index"
          label="序号"
          header-align="center"
          align="center"
          class-name="wn-tableindex"
          width="40px"
        >
        </el-table-column>
        <el-table-column
          label="用户名"
          prop="username"
          align="center"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          label="手机号"
          prop="mobile"
          align="center"
        ></el-table-column>
        <el-table-column
          label="真实姓名"
          prop="truename"
          align="center"
        ></el-table-column>
        <el-table-column
          label="地址"
          prop="address"
          align="center"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          label="商品"
          prop="title"
          align="center"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          label="下单数量(件)"
          prop="number"
          align="center"
          width="120px"
        ></el-table-column>
        <el-table-column
          label="总价(元)"
          prop="price"
          align="center"
          width="120px"
        ></el-table-column>
        <el-table-column
          label="创建时间"
          prop="createtime"
          align="center"
        ></el-table-column>
        <el-table-column align="center" width="150px">
          <template #default="scope">
            <el-button
              size="mini"
              type="success"
              round
              @click="ShowDetails(scope.$index, scope.row)"
              >查看</el-button
            >
            <el-button
              size="mini"
              type="danger"
              round
              @click="handleDelete(scope.$index, scope.row)"
              >退款</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </el-main>
    <el-footer class="wn-footer">
      <el-row style="padding-top: 1px">
        <el-col :span="24" class="align-right">
          <el-pagination
            :background="true"
            :page-sizes="[5, 10, 20]"
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
</template>

<script>
export default {
  data() {
    return {
      loading: false,
      tableData: [
        {
          createtime: "2016-05-03",
          username: "Tom",
          mobile: "15242118869",
          truename: "杨东林",
          title: "大碗宽面,梦龙雪糕",
          number: "17",
          price: "153.67",
          address: "No. 189, Grove St, Los Angeles",
        },
        {
          createtime: "2016-05-03",
          username: "Tom",
          mobile: "15242118869",
          truename: "杨东林",
          title: "大碗宽面,梦龙雪糕",
          number: "17",
          price: "153.67",
          address: "No. 189, Grove St, Los Angeles",
        },
        {
          createtime: "2016-05-03",
          username: "Tom",
          mobile: "15242118869",
          truename: "杨东林",
          title: "大碗宽面,梦龙雪糕",
          number: "17",
          price: "153.67",
          address: "No. 189, Grove St, Los Angeles",
        },
        {
          createtime: "2016-05-03",
          username: "Tom",
          mobile: "15242118869",
          truename: "杨东林",
          title: "大碗宽面,梦龙雪糕",
          number: "17",
          price: "153.67",
          address: "No. 189, Grove St, Los Angeles",
        },
        {
          createtime: "2016-05-03",
          username: "Tom",
          mobile: "15242118869",
          truename: "杨东林",
          title: "大碗宽面,梦龙雪糕",
          number: "17",
          price: "153.67",
          address: "No. 189, Grove St, Los Angeles",
        },
        {
          createtime: "2016-05-03",
          username: "Tom",
          mobile: "15242118869",
          truename: "杨东林",
          title: "大碗宽面,梦龙雪糕",
          number: "17",
          price: "153.67",
          address: "No. 189, Grove St, Los Angeles",
        },
        {
          createtime: "2016-05-03",
          username: "Tom",
          mobile: "15242118869",
          truename: "杨东林",
          title: "大碗宽面,梦龙雪糕",
          number: "17",
          price: "153.67",
          address: "No. 189, Grove St, Los Angeles",
        },
        {
          createtime: "2016-05-02",
          username: "John",
          mobile: "15242118869",
          truename: "杨东林",
          title: "大碗宽面,梦龙雪糕",
          number: "17",
          price: "153.67",
          address: "No. 189, Grove St, Los Angeles",
        },
        {
          createtime: "2016-05-04",
          username: "Morgan",
          mobile: "15242118869",
          truename: "杨东林",
          title: "大碗宽面,梦龙雪糕",
          number: "17",
          price: "153.67",
          address: "No. 189, Grove St, Los Angeles",
        },
        {
          createtime: "2016-05-01",
          username: "Jessy",
          mobile: "15242118869",
          truename: "杨东林",
          title: "大碗宽面,梦龙雪糕",
          number: "17",
          price: "153.67",
          address: "No. 189, Grove St, Los Angeles",
        },
      ],
      // 当前页
      pageIndex: this.defaultPageIndex,
      // 页面大小
      pageSize: this.defaultPageSize,
      total: 0,
      keyword: "",
    };
  },
  computed: {
    param() {},
  },
  mounted() {
    this.total = this.tableData.length;
  },
  methods: {
    Search() {
      console.log(this.keyword);
    },
    ShowDetails(index, row) {
      console.log(index, row);
    },
    handleDelete(index, row) {
      console.log(index, row);
    },
    handleSizeChange() {},
    handleCurrentChange() {},
  },
};
</script>
