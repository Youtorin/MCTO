/** 出库订单管理 */
<template>
  <el-container
    style="height: 100%"
    class="background-6F9 eig-mian-doc"
    v-loading="loading"
  >
    <el-main class="wnw-main">
      <el-table :data="tableData" style="width: 100%">
        <el-table-column
          type="index"
          label="序号"
          header-align="center"
          align="center"
          class-name="wn-tableindex"
          width="80px"
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
    };
  },
  mounted() {
    this.total = this.tableData.length;
  },
  methods: {
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
