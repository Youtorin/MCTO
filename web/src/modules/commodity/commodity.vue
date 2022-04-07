/** 订单列表 */
<template>
  <el-container>
    <el-aside width="200px" style="background-color: rgb(255, 255, 255)">
      <el-menu :default-openeds="['1', '2', '3']">
        <el-submenu index="1">
          <template #title><i class="el-icon-message"></i>导航一</template>
          <el-menu-item-group>
            <template #title>分组一</template>
            <el-menu-item index="1-1">选项1</el-menu-item>
            <el-menu-item index="1-2">选项2</el-menu-item>
          </el-menu-item-group>
          <el-menu-item-group title="分组2">
            <el-menu-item index="1-3">选项3</el-menu-item>
          </el-menu-item-group>
          <el-submenu index="1-4">
            <template #title>选项4</template>
            <el-menu-item index="1-4-1">选项4-1</el-menu-item>
          </el-submenu>
        </el-submenu>
        <el-submenu index="2">
          <template #title><i class="el-icon-menu"></i>导航二</template>
          <el-menu-item-group>
            <template #title>分组一</template>
            <el-menu-item index="2-1">选项1</el-menu-item>
            <el-menu-item index="2-2">选项2</el-menu-item>
          </el-menu-item-group>
          <el-menu-item-group title="分组2">
            <el-menu-item index="2-3">选项3</el-menu-item>
          </el-menu-item-group>
          <el-submenu index="2-4">
            <template #title>选项4</template>
            <el-menu-item index="2-4-1">选项4-1</el-menu-item>
          </el-submenu>
        </el-submenu>
        <el-submenu index="3">
          <template #title><i class="el-icon-setting"></i>导航三</template>
          <el-menu-item-group>
            <template #title>分组一</template>
            <el-menu-item index="3-1">选项1</el-menu-item>
            <el-menu-item index="3-2">选项2</el-menu-item>
          </el-menu-item-group>
          <el-menu-item-group title="分组2">
            <el-menu-item index="3-3">选项3</el-menu-item>
          </el-menu-item-group>
          <el-submenu index="3-4">
            <template #title>选项4</template>
            <el-menu-item index="3-4-1">选项4-1</el-menu-item>
          </el-submenu>
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
            <el-col :span="1">
              <el-button type="danger" size="small">批量删除</el-button>
            </el-col>
            <el-col :span="1" class="wnw-ml-20">
              <el-button type="primary" size="small">添加商品</el-button>
            </el-col>
            <el-col :span="7" align="right">
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
          <el-table-column width="40px" class="wn-table">
            <template v-slot="{ row }">
              <el-checkbox
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
            width="40px"
          >
          </el-table-column>
          <el-table-column
            label="分类"
            prop="cate"
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
                :fit="fill"
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
            prop="desc"
            align="center"
          ></el-table-column>
          <el-table-column
            label="单价/元"
            prop="sellprice"
            align="center"
            width="120px"
            show-overflow-tooltip
          ></el-table-column>
          <el-table-column
            label="库存"
            prop="number"
            align="center"
            width="100px"
            show-overflow-tooltip
          ></el-table-column>
          <el-table-column
            label="创建时间"
            prop="createtime"
            align="center"
            width="140px"
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
  </el-container>
</template>

<script>
export default {
  data() {
    return {
      loading: false,
      tableData: [
        {
          id: "1",
          cate: "休闲零食",
          cover:
            "https://img1.baidu.com/it/u=4216761644,15569246&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=500",
          title: "梦龙雪糕（香草味）",
          desc: "雪糕",
          sellprice: 24.9,
          number: 999,
          createtime: "2016-05-03",
        },
        {
          id: "2",
          cate: "休闲零食",
          cover:
            "https://img1.baidu.com/it/u=4216761644,15569246&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=500",
          title: "梦龙雪糕（香草味）",
          desc: "雪糕",
          sellprice: 24.9,
          number: 999,
          createtime: "2016-05-03",
        },
        {
          id: "3",
          cate: "休闲零食",
          cover:
            "https://img1.baidu.com/it/u=4216761644,15569246&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=500",
          title: "梦龙雪糕（香草味）",
          desc: "雪糕",
          sellprice: 24.9,
          number: 999,
          createtime: "2016-05-03",
        },
        {
          id: "4",
          cate: "休闲零食",
          cover:
            "https://img1.baidu.com/it/u=4216761644,15569246&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=500",
          title: "梦龙雪糕（香草味）",
          desc: "雪糕",
          sellprice: 24.9,
          number: 999,
          createtime: "2016-05-03",
        },
        {
          id: "5",
          cate: "休闲零食",
          cover:
            "https://img1.baidu.com/it/u=4216761644,15569246&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=500",
          title: "梦龙雪糕（香草味）",
          desc: "雪糕",
          sellprice: 24.9,
          number: 999,
          createtime: "2016-05-03",
        },
        {
          id: "6",
          cate: "休闲零食",
          cover:
            "https://img1.baidu.com/it/u=4216761644,15569246&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=500",
          title: "梦龙雪糕（香草味）",
          desc: "雪糕",
          sellprice: 24.9,
          number: 999,
          createtime: "2016-05-03",
        },
        {
          id: "7",
          cate: "休闲零食",
          cover:
            "https://img1.baidu.com/it/u=4216761644,15569246&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=500",
          title: "梦龙雪糕（香草味）",
          desc: "雪糕",
          sellprice: 24.9,
          number: 999,
          createtime: "2016-05-03",
        },
        {
          id: "8",
          cate: "休闲零食",
          cover:
            "https://img1.baidu.com/it/u=4216761644,15569246&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=500",
          title: "梦龙雪糕（香草味）",
          desc: "雪糕",
          sellprice: 24.9,
          number: 999,
          createtime: "2016-05-03",
        },
        {
          id: "9",
          cate: "休闲零食",
          cover:
            "https://img1.baidu.com/it/u=4216761644,15569246&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=500",
          title: "梦龙雪糕（香草味）",
          desc: "雪糕",
          sellprice: 24.9,
          number: 999,
          createtime: "2016-05-03",
        },
        {
          id: "10",
          cate: "休闲零食",
          cover:
            "https://img1.baidu.com/it/u=4216761644,15569246&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=500",
          title: "梦龙雪糕（香草味）",
          desc: "雪糕",
          sellprice: 24.9,
          number: 999,
          createtime: "2016-05-03",
        },
      ],
      // 当前页
      pageIndex: this.defaultPageIndex,
      // 页面大小
      pageSize: this.defaultPageSize,
      total: 0,
      keyword: "",
      rowKey: "id",
      checkModel: [],
      checkValue: [],
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
      console.log(this.checkModel);
      console.log(this.checkValue);
    },
  },
};
</script>
