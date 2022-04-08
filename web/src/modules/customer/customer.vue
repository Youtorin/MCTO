/** 顾客管理 */
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
      <el-breadcrumb-item>顾客管理</el-breadcrumb-item>
    </el-breadcrumb>
    <el-header>
      <el-form :inline="true" :model="param" class="user-search">
        <el-row class="wn-row wn-row-fir" style="margin-bottom: 5px">
          <el-col :span="24" align="right">
            <el-input
              v-model.trim="keyword"
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
          label="性别"
          prop="gender"
          align="center"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          label="邮箱"
          prop="email"
          align="center"
          width="240px"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          label="默认地址"
          prop="address"
          align="center"
          width="240px"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          label="加入时间"
          prop="createtime"
          align="center"
          width="160px"
        ></el-table-column>
        <el-table-column
          label="状态"
          prop="createtime"
          align="center"
          width="100px"
        >
          <template #default="scope">
            {{ statusFormat(scope.row.status) }}
          </template>
        </el-table-column>
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
          email: "santianliangjue@163.com",
          address: "No. 189, Grove St, Los Angeles",
          gender: "男",
          status: 1,
        },
        {
          createtime: "2016-05-03",
          username: "Tom",
          mobile: "15242118869",
          truename: "杨东林",
          email: "santianliangjue@163.com",
          address: "No. 189, Grove St, Los Angeles",
          gender: "男",
          status: 0,
        },
        {
          createtime: "2016-05-03",
          username: "Tom",
          mobile: "15242118869",
          truename: "杨东林",
          email: "santianliangjue@163.com",
          address: "No. 189, Grove St, Los Angeles",
          gender: "男",
          status: 1,
        },
        {
          createtime: "2016-05-03",
          username: "Tom",
          mobile: "15242118869",
          truename: "杨东林",
          email: "santianliangjue@163.com",
          address: "No. 189, Grove St, Los Angeles",
          gender: "男",
          status: 0,
        },
        {
          createtime: "2016-05-03",
          username: "Tom",
          mobile: "15242118869",
          truename: "杨东林",
          email: "santianliangjue@163.com",
          address: "No. 189, Grove St, Los Angeles",
          gender: "男",
          status: 1,
        },
        {
          createtime: "2016-05-03",
          username: "Tom",
          mobile: "15242118869",
          truename: "杨东林",
          email: "santianliangjue@163.com",
          address: "No. 189, Grove St, Los Angeles",
          gender: "男",
          status: 0,
        },
        {
          createtime: "2016-05-03",
          username: "Tom",
          mobile: "15242118869",
          truename: "杨东林",
          email: "santianliangjue@163.com",
          address: "No. 189, Grove St, Los Angeles",
          gender: "男",
          status: 0,
        },
        {
          createtime: "2016-05-03",
          username: "Tom",
          mobile: "15242118869",
          truename: "杨东林",
          email: "santianliangjue@163.com",
          address: "No. 189, Grove St, Los Angeles",
          gender: "男",
          status: 0,
        },
        {
          createtime: "2016-05-03",
          username: "Tom",
          mobile: "15242118869",
          truename: "杨东林",
          email: "santianliangjue@163.com",
          address: "No. 189, Grove St, Los Angeles",
          gender: "男",
          status: 1,
        },
        {
          createtime: "2016-05-03",
          username: "Tom",
          mobile: "15242118869",
          truename: "杨东林",
          email: "santianliangjue@163.com",
          address: "No. 189, Grove St, Los Angeles",
          gender: "男",
          status: 0,
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
    statusFormat(val) {
      if (val === 1 || val === "1") {
        return "启用";
      } else if (val === 0 || val === "0") {
        return "禁用";
      }
    },
  },
};
</script>
