/** 顾客管理 */
<template>
  <div style="height: 100%">
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
            label="加入时间"
            prop="createTime"
            align="center"
            width="160px"
          >
            <template #default="scope">
              {{ FormatUTC2Local(scope.row.createTime) }}
            </template>
          </el-table-column>
          <el-table-column
            label="状态"
            prop="status"
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
                @click="showDetails(scope.row.id)"
                >查看</el-button
              >
              <el-button
                size="mini"
                type="danger"
                round
                @click="handleDelete(scope.row.id)"
                >禁用</el-button
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
    <el-dialog v-model="outerVisible" title="用户信息详情" width="50%">
      <el-main style="height: 50%">
        <template #default v-loading="boxLoading">
          <el-collapse v-model="actionvalue" accordion>
            <el-collapse-item
              title="用户信息"
              name="1"
              class="wn-form-collapse"
            >
              <el-main class="wnw-main">
                <el-form :model="form" label-width="100px">
                  <el-row>
                    <el-col :span="22">
                      <el-form-item label="用户名" prop="username">
                        <el-input
                          readonly
                          type="text"
                          v-model="form.username"
                        ></el-input></el-form-item
                    ></el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="22">
                      <el-form-item label="手机号" prop="mobile">
                        <el-input
                          readonly
                          type="text"
                          v-model="form.mobile"
                        ></el-input
                      ></el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="22">
                      <el-form-item label="真实姓名" prop="truename">
                        <el-input
                          readonly
                          type="text"
                          v-model="form.truename"
                        ></el-input></el-form-item
                    ></el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="22">
                      <el-form-item label="性别" prop="gender">
                        <el-input
                          readonly
                          type="text"
                          v-model="form.gender"
                        ></el-input></el-form-item
                    ></el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="22">
                      <el-form-item label="邮箱" prop="email">
                        <el-input
                          readonly
                          type="text"
                          v-model="form.email"
                        ></el-input></el-form-item
                    ></el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="22">
                      <el-form-item label="创建时间" prop="createtime">
                        <el-date-picker
                          v-model="form.createTime"
                          readonly
                          type="date"
                          placeholder="请选择日期" /></el-form-item
                    ></el-col>
                  </el-row>
                </el-form>
              </el-main>
            </el-collapse-item>

            <el-collapse-item
              title="地址列表"
              name="2"
              class="wn-form-collapse"
            >
              <el-main class="wnw-main">
                <el-table v-model="userAddressList" style="width: 100%">
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
                    label="省"
                    prop="province"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    label="市"
                    prop="city"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    label="区"
                    prop="district"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    label="详细地址"
                    prop="address"
                    align="left"
                    show-overflow-tooltip
                  ></el-table-column>
                </el-table>
              </el-main>
            </el-collapse-item>
          </el-collapse>
        </template>
      </el-main>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="outerVisible = false">关闭</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { GetList, Delete, GetModel } from "@/api/user.js";
import { GetAddressList } from "@/api/userAddress.js";
import { messageShow } from "@/assets/js/Common.js";
import moment from "moment";
import "@/assets/js/Common.js";
export default {
  data() {
    return {
      loading: false,
      boxLoading: false,
      outerVisible: false,
      actionvalue: ["1"],
      tableData: [],
      userAddressList: [],
      total: 0,
      param: {
        keywords: "",
        pagination: {
          rows: 10,
          page: 1,
          sidx: "CreateTime",
          sord: "desc",
        },
      },
      form: {
        username: "",
        mobile: "",
        truename: "",
        gender: "",
        email: "",
        createtime: "",
      },
    };
  },
  computed: {},
  mounted() {
    this.loaddata();
  },
  methods: {
    loaddata() {
      this.loading = true;
      GetList(this.param)
        .then((res) => {
          if (res.success && res.result) {
            this.tableData = res.result.items;
            this.total = res.result.totalcount;
          }
          this.loading = false;
        })
        .catch((err) => {
          this.loading = false;
        });
    },
    Search() {
      this.loaddata();
    },
    async showDetails(id) {
      this.boxLoading = true;
      await GetModel({ id: id })
        .then((res) => {
          if (res.success && res.result) {
            this.form = res.result;
          } else {
            messageShow("warning", "用户不存在");
          }
        })
        .catch((err) => {
          this.boxLoading = false;
        });
      await GetAddressList({ id: id })
        .then((res) => {
          if (res.success && res.result) {
            this.userAddressList = res.result;
            console.log(this.userAddressList);
          }
        })
        .catch((err) => {
          this.boxLoading = false;
        });

      this.boxLoading = false;
      this.outerVisible = true;
    },
    handleDelete(id) {
      this.loading = true;
      Delete({ id: id })
        .then((res) => {
          if (res.success) {
            messageShow("success", "禁用成功！");
            this.loading = false;
          }
        })
        .catch((err) => {
          messageShow("warning", "禁用失败！");
          this.loading = false;
        });
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
    statusFormat(val) {
      if (val === 1 || val === "1") {
        return "启用";
      } else if (val === 0 || val === "0") {
        return "禁用";
      }
    },

    FormatUTC2Local(date) {
      if (date === "" || date === null) {
        return "";
      }
      var f = "YYYY-MM-DD";
      return moment(date).format(f);
    },
  },
};
</script>