
<template>
  <div style="padding: 0 20%">
    <el-collapse v-model="activeNames">
      <el-collapse-item title="收货人信息" name="1" class="wn-form-collapse">
        <el-col>
          <el-row>
            <span class="addAddressCons">收货人信息</span>
            <span class="addAddress" @click="AddressShowClick"
              >新增收货地址</span
            >
          </el-row>
          <div class="scolleShow">
            <el-row
              class="consignee"
              v-for="(items, index) in filterAddress"
              :key="index"
            >
              <div
                v-on:mouseout="MouseOutList(items)"
                v-on:mouseleave="MouseOverList(items)"
              >
                <el-col :span="4">
                  <div
                    class="consigneeList consigneeListWidth"
                    :class="{ consigneeListClick: items.addressDefult }"
                    @click="currentIndexClick(items, index)"
                  >
                    <span v-text="items.name"></span>
                    <span v-text="items.city"></span>
                    <div v-show="items.addressDefult">
                      <div class="arrow-right"></div>
                      <i class="fa fa-check" aria-hidden="true"></i>
                    </div>
                  </div>
                </el-col>
                <el-col
                  class="rightAddress"
                  :span="20"
                  :class="{ BackColor: items.addressIsShow }"
                >
                  <span v-text="items.name"></span>
                  <span v-text="items.city"></span>
                  <span v-text="items.area"></span>
                  <span v-text="items.minarea"></span>
                  <span v-text="items.phone"></span>
                  <span v-if="items.isShowDefult" class="addressDefultClass"
                    >默认地址</span
                  >
                  <span v-if="items.addressIsShow" class="edmitAddre">
                    <span class="edmitSpan" @click="defultAddress(items)"
                      >设为默认地址</span
                    >
                    <span class="edmitSpan" @click="emitAddress(items, index)"
                      >编辑</span
                    >
                  </span>
                  <span
                    v-if="!items.isShowDefult"
                    @click="deleAddressAlert(index)"
                    class="edmitSpan edmitSpan-delete"
                    >删除</span
                  >
                </el-col>
                <div class="clearfix"></div>
              </div>
            </el-row>
          </div>
          <el-dialog
            title="新增收货地址"
            :visible.sync="newAddressShow"
            width="30%"
          >
            <el-form ref="form" :model="form" label-width="80px">
              <el-form-item label="收货人">
                <el-input
                  v-model="form.name"
                  placeholder="请输入内容"
                ></el-input>
              </el-form-item>
              <el-form-item label="所在地区">
                <el-input
                  v-model="form.city"
                  placeholder="请输入内容"
                ></el-input>
              </el-form-item>
              <el-form-item label="详细地址">
                <el-input
                  v-model="form.minarea"
                  placeholder="请输入内容"
                ></el-input>
              </el-form-item>
              <el-form-item label="手机号码">
                <el-input
                  v-model="form.phone"
                  placeholder="请输入内容"
                ></el-input>
              </el-form-item>
              <el-form-item>
                <el-checkbox-group v-model="form.isShowDefult">
                  <el-checkbox label="设为默认地址" name="type"
                    >设为默认地址</el-checkbox
                  >
                </el-checkbox-group>
              </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
              <el-button @click="dialogVisible = false" type="danger"
                >取 消</el-button
              >
              <el-button type="primary" @click="saveNewAdress(edmitType)"
                >保存</el-button
              >
            </span>
          </el-dialog>
          <div v-if="!moreAddressData.length == 0" class="muchAddress">
            <span @click="moreAddress"
              >更多地址
              <transition name="fade" mode="out-in">
                <i
                  key="up"
                  v-if="isTranShow"
                  class="fa fa-angle-double-down"
                ></i>
                <i key="down" v-else class="fa fa-angle-double-up"></i>
              </transition>
            </span>
          </div>
          <div v-if="moreAddressData.length == 0" class="noAddress">
            <span>您还没有收货地址，请增加新的收货地址！</span>
          </div>
        </el-col>
      </el-collapse-item>
      <el-collapse-item title="支付方式" name="2" class="wn-form-collapse">
        <div>
          控制反馈：通过界面样式和交互动效让用户可以清晰的感知自己的操作；
        </div>
        <div>页面反馈：操作后，通过页面元素的变化清晰地展现当前状态。</div>
      </el-collapse-item>
      <el-collapse-item title="送货清单" name="3" class="wn-form-collapse">
        <el-table :data="tableData" style="width: 100%" :show-header="false">
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
          <el-table-column label="单价/元" prop="sellPrice" align="center">
            <template slot-scope="{ row }"
              >单价：¥{{ row.sellPrice }}</template
            ></el-table-column
          >
          <el-table-column label="数量" prop="count" align="center">
            <template slot-scope="{ row }">x {{ row.count }}</template>
          </el-table-column>

          <el-table-column
            label="金额/元"
            prop="price"
            align="center"
            width="100px"
            show-overflow-tooltip
          >
            <template slot-scope="scope">
              总价：¥{{ scope.row.sellPrice * scope.row.count }}
            </template></el-table-column
          >
        </el-table>
      </el-collapse-item>
      <el-collapse-item title="金额" name="4" class="wn-form-collapse">
        <div>
          用户决策：根据场景可给予用户操作建议或安全提示，但不能代替用户进行决策；
        </div>
        <div>
          结果可控：用户可以自由的进行操作，包括撤销、回退和终止当前操作等。
        </div>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>
import { currentUser } from "@/assets/js/Common";
import "./css/ShoppingCartAdress.css";
export default {
  data() {
    return {
      tableData: [],
      edmitType: "",
      limitNum: 1, //默认显示几个地址
      isTranShow: true,
      form: {
        name: "",
        city: "",
        area: "",
        minarea: "",
        phone: "",
        addressDefult: "",
        addressIsShow: "",
        num: "",
        isShowDefult: "",
      },
      moreAddressData: [
        {
          name: "111111111111",
          city: "北京市",
          area: "丰台区",
          minarea: "嘉园一里9号楼1",
          phone: 18347460829,
          addressDefult: false,
          addressIsShow: false,
          isShowDefult: false,
        },
        {
          name: "222222222222",
          city: "北京市",
          area: "丰台区",
          minarea: "嘉园一里9号楼2",
          phone: 18347460829,
          addressDefult: false,
          addressIsShow: false,
          isShowDefult: false,
        },
        {
          name: "3333333333",
          city: "北京市",
          area: "丰台区",
          minarea: "嘉园一里9号楼1",
          phone: 18347460829,
          addressDefult: false,
          addressIsShow: false,
          isShowDefult: false,
        },
      ],
      newAddressShow: false,
      activeNames: ["1", "2", "3", "4"],
    };
  },
  mounted: function () {
    this.getDate();
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
    //新增收货地址
    AddressShowClick() {
      (this.edmitType = "新增"), (this.newAddressShow = true);
    },
    //点击更多地址函数
    moreAddress() {
      if (this.limitNum == 1) {
        this.limitNum = this.moreAddressData.length;
        this.isTranShow = !this.isTranShow;
      } else {
        this.limitNum = 1;
        this.isTranShow = !this.isTranShow;
        var tem, index;
        for (var y in this.moreAddressData) {
          if (this.moreAddressData[y].isShowDefult == true) {
            tem = this.moreAddressData[y];
            index = y;
          }
        }
        this.moreAddressData.splice(index, 1);
        this.moreAddressData.unshift(tem);
      }
    },
    //设置为默认收货地址
    defultAddress(item) {
      var tem, index;
      for (var x in this.moreAddressData) {
        this.moreAddressData[x].isShowDefult = false;
      }
      item.isShowDefult = true;
      for (var y in this.moreAddressData) {
        if (this.moreAddressData[y].isShowDefult == true) {
          tem = this.moreAddressData[y];
          index = y;
        }
      }
      this.moreAddressData.splice(index, 1);
      this.moreAddressData.unshift(tem);
    },
    //编辑收货地址函数
    emitAddress(item, index) {
      this.newAddressShow = true;
      this.edmitType = "修改";
      //把此行数据到对话框
      this.form.name = item.name;
      this.form.city = item.city;
      this.form.area = item.area;
      this.form.minarea = item.minarea;
      this.form.phone = item.phone;
      this.form.addressDefult = item.addressDefult;
      this.form.addressIsShow = item.addressIsShow;
      this.form.isShowDefult = item.isShowDefult;
      this.form.num = index;
    },
    /*鼠标移动函数显示：默认地址、编辑、删除*/
    MouseOutList(moreaddressData) {
      moreaddressData.addressIsShow = true;
    },
    MouseOverList(moreaddressData) {
      moreaddressData.addressIsShow = false;
    },
  },
  /*金额过滤器*/
  filters: {},
  /*用于过滤缓存(用于过滤地址显示几个)*/
  computed: {
    filterAddress() {
      return this.moreAddressData.slice(0, this.limitNum);
    },
  },
};
</script>