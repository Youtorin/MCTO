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
        <el-breadcrumb-item>分类列表</el-breadcrumb-item>
      </el-breadcrumb>
      <el-header>
        <el-form :inline="true" :model="param" class="user-search">
          <el-row class="wn-row wn-row-fir" style="margin-bottom: 5px">
            <el-col :span="24" align="right">
              <el-button type="primary" size="small" @click="AddCate"
                >新增分类</el-button
              >
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
        <el-table :data="tableData" style="height: 100%">
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
            label="分类名"
            prop="name"
            align="center"
            width="300px"
            show-overflow-tooltip
          ></el-table-column>
          <el-table-column
            label="描述"
            prop="description"
            header-align="center"
            align="left"
          ></el-table-column>
          <el-table-column
            label="创建时间"
            prop="createTime"
            align="center"
            width="160px"
          >
            <template #default="scope">
              {{ FormatUTC2Local(scope.row.createTime) }}
            </template>
          </el-table-column>
          <el-table-column align="center" width="150px">
            <template #default="scope">
              <el-button
                size="mini"
                type="success"
                round
                @click="edit(scope.row)"
                >编辑</el-button
              >
              <el-button
                size="mini"
                type="danger"
                round
                @click="handleDelete(scope.row.id)"
                >删除</el-button
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
    <el-dialog v-model="outerVisible" title="分类信息" width="50%">
      <el-main style="height: 50%">
        <template #default v-loading="boxLoading">
          <el-collapse v-model="actionvalue" accordion>
            <el-collapse-item
              title="分类信息详情"
              name="1"
              class="wn-form-collapse"
            >
              <el-main class="wnw-main">
                <el-form :model="form" label-width="100px">
                  <el-form-item label="分类名" prop="name">
                    <el-input type="text" v-model="form.name"></el-input
                  ></el-form-item>
                  <el-form-item label="排序" prop="viewOrder">
                    <el-input type="text" v-model="form.viewOrder"></el-input
                  ></el-form-item>
                  <el-form-item label="创建时间" prop="createtime">
                    <el-date-picker
                      v-model="form.createTime"
                      type="date"
                      placeholder="请选择日期"
                    >
                    </el-date-picker>
                  </el-form-item>
                  <el-form-item label="描述" prop="description">
                    <el-input type="text" v-model="form.description"></el-input
                  ></el-form-item>
                </el-form>
              </el-main>
            </el-collapse-item>
          </el-collapse>
        </template>
      </el-main>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="save" type="primary" size="mini">保存</el-button>
          <el-button @click="outerVisible = false" size="mini">关闭</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { GetPageList, Delete, Edit } from "@/api/foodCategory.js";
import { messageShow } from "@/assets/js/Common.js";
import moment from "moment";
import "@/assets/js/Common.js";
export default {
  data() {
    return {
      loading: false,
      outerVisible: false,
      actionvalue: ["1"],
      tableData: [],
      userAddressList: [],
      // 当前页
      pageIndex: this.defaultPageIndex,
      // 页面大小
      pageSize: this.defaultPageSize,
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
        name: "",
        description: "",
        viewOrder: "",
        createTime: "",
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
      GetPageList(this.param)
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
    async edit(row) {
      this.boxLoading = true;
      this.form = row;
      this.boxLoading = false;
      this.outerVisible = true;
    },
    AddCate() {
      this.form = "";
      this.outerVisible = true;
    },
    async save() {
      this.loading = true;
      await Edit(this.form)
        .then((res) => {
          if (res.success) {
            messageShow("success", "保存成功！");
            this.loading = false;
          }
        })
        .catch((err) => {
          messageShow("error", "保存失败," + err.$message);
          this.loading = false;
        });
      this.loaddata();
      this.outerVisible = false;
    },
    async handleDelete(id) {
      this.loading = true;
      await Delete({ id: id })
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
      this.loaddata();
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