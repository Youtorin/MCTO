/** 商品列表 */
<template>
  <div>
    <el-container
      style="height: 100%"
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
        v-loading="loading"
      >
        <!-- 面包屑导航 -->
        <el-breadcrumb
          separator-class="el-icon-arrow-right"
          style="margin-bottom: 10px"
        >
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>商品列表</el-breadcrumb-item>
        </el-breadcrumb>
        <el-header>
          <el-form :inline="true" :model="param" class="user-search">
            <el-row class="wn-row wn-row-fir" style="margin-bottom: 5px">
              <el-col :span="24" align="right">
                <el-button type="danger" size="small">批量删除</el-button>
                <el-button type="primary" size="small" @click="Add"
                  >添加商品</el-button
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
          <el-table :data="tableData" style="width: 100%">
            <el-table-column width="40px" class="wn-table">
              <template v-slot="{ row }">
                <el-checkbox
                  @change="
                    (checked) => CheckBoxChange(checked, row[rowKey], row)
                  "
                ></el-checkbox>
              </template>
            </el-table-column>
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
              label="分类"
              prop="cateName"
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
              <template v-slot="{ row }">
                <el-image
                  style="width: 100px; height: 100px"
                  :src="row.cover"
                ></el-image>
              </template>
            </el-table-column>
            <el-table-column
              label="食品名称"
              prop="title"
              align="center"
            ></el-table-column>
            <el-table-column
              label="描述"
              prop="description"
              align="center"
            ></el-table-column>
            <el-table-column
              label="库存"
              prop="discount"
              align="center"
              width="100px"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              label="点赞次数"
              prop="likeNum"
              align="center"
              width="100px"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              label="创建时间"
              prop="createTime"
              align="center"
              width="140px"
            >
              <template #default="scope">
                {{ FormatUTC2Local(scope.row.createTime) }}
              </template></el-table-column
            >
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
                  @click="handleDelete(scope.row)"
                  >下架</el-button
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
    </el-container>

    <el-dialog
      append-to-body
      v-model="outerVisible"
      title="商品信息"
      width="50%"
    >
      <el-main style="height: 50%">
        <el-form :model="form" ref="modelInfo" label-width="100px">
          <el-form-item label="分类" prop="cateId">
            <el-select v-model="form.cateId">
              <el-option
                v-for="item in cateList"
                :key="item"
                :value="item.id"
                :label="item.name"
              >
                {{ item.name }}
              </el-option>
            </el-select></el-form-item
          >
          <el-form-item label="图片url" prop="cover">
            <el-input type="text" v-model="form.cover"></el-input
          ></el-form-item>
          <el-form-item label="食品名称" prop="title">
            <el-input type="text" v-model="form.title"></el-input
          ></el-form-item>
          <el-form-item label="库存" prop="discount">
            <el-input type="number" v-model="form.discount"></el-input
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
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="save" type="primary" size="mini">保存</el-button>
          <el-button @click="outerVisible = false" size="mini">关闭</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script >
import { GetCateList } from "@/api/foodCategory.js";
import { GetList, Edit } from "@/api/food.js";
import { messageShow } from "@/assets/js/Common.js";
import moment from "moment";
export default {
  data() {
    return {
      outerVisible: false,
      boxLoading: false,
      loading: false,
      actionvalue: ["1"],
      cateList: [],
      tableData: [],
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
      },
      param: {
        keywords: "",
        pagination: {
          cateId: "",
          rows: 10,
          page: 1,
          sidx: "CreateTime",
          sord: "desc",
        },
      },
    };
  },
  computed: {},
  mounted() {
    this.loaddata();
  },
  methods: {
    async loaddata() {
      this.loading = true;
      await GetCateList()
        .then((res) => {
          if (res.success && res.result) {
            this.cateList = res.result;
            console.log(this.cateList);
            this.cateList.unshift({
              name: "全部分类",
              id: "",
            });
          }
        })
        .catch((err) => {});
      await GetList(this.param)
        .then((res) => {
          if (res.success && res.result) {
            this.tableData = res.result.items;
            this.total = res.result.totalcount;
            console.log(this.tableData);
          }
        })
        .catch((err) => {});
      this.loading = false;
    },
    Search() {
      this.loaddata();
    },
    edit(row) {
      this.boxLoading = true;
      this.form = row;
      this.boxLoading = false;
      this.outerVisible = true;
    },
    handleDelete(row) {
      console.log(row);
    },
    async save() {
      this.loading = true;
      console.log(this.form);
      await Edit(this.form)
        .then((res) => {
          if (res.success) {
            messageShow("success", "保存成功！");
            this.loading = false;
          } else {
            messageShow("error", "保存失败！");
          }
        })
        .catch((err) => {
          messageShow("error", "保存失败！" + err.$message);
          this.loading = false;
        });
      this.loaddata();
      this.outerVisible = false;
    },
    Add() {
      this.outerVisible = true;
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
    handleChangeCate(val) {
      this.param.pagination.cateId = val.id;
      this.loaddata();
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
