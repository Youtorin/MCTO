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
        <el-breadcrumb-item>门店管理</el-breadcrumb-item>
      </el-breadcrumb>
      <el-main class="wnw-main" style="width: 100%">
        <el-form :model="form" :rules="rules" ref="form" label-width="100px">
          <el-form-item label="门店图片" prop="storeImg">
            <el-upload
              ref="upload"
              style="display: inline-block"
              action=""
              multiple
              :limit="1"
              :http-request="httpRequest"
              :on-remove="handleRemove"
              :before-upload="beforeAvatarUpload"
            >
              <el-image
                v-if="form.storeImg"
                :src="form.storeImg"
                fit="fill"
                class="avatar"
              />
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
          <el-form-item label="店内图片" prop="instoreImg" v-if="false">
            <el-upload
              action=""
              :auto-upload="false"
              list-type="picture-card"
              :on-change="handlePictureCard"
              :on-preview="handlePictureCardPreview"
            >
              <i class="el-icon-plus"></i>
            </el-upload>
            <el-dialog v-model="dialogVisible">
              <el-image
                width="100%"
                :src="dialogImageUrl"
                :fit="contain"
                alt=""
              />
            </el-dialog>
          </el-form-item>
          <el-form-item label="门店名称" prop="shopname">
            <el-input v-model="form.shopname"></el-input>
          </el-form-item>

          <el-form-item label="门店地址" prop="address">
            <el-row>
              <el-col :span="22">
                <el-button size="mini" type="info" @click="showMap"
                  >选址</el-button
                >
                <el-input
                  style="width: 60%; padding: 8px 10px"
                  placeholder="请输入详细地址"
                  v-model="form.address"
                >
                  <template #prepend>{{ address }}</template>
                </el-input>
              </el-col>
            </el-row>
          </el-form-item>
          <el-form-item label="联系人" prop="contactMan">
            <el-input v-model="form.contactMan"></el-input>
          </el-form-item>
          <el-form-item label="联系电话" prop="contactMobile">
            <el-input v-model="form.contactMobile"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="form.email"></el-input>
          </el-form-item>
          <el-form-item label="营业时间" required>
            <el-col :span="11">
              <el-form-item prop="beginTime">
                <el-time-picker
                  placeholder="选择开始时间"
                  v-model="form.beginTime"
                  style="width: 100%"
                ></el-time-picker>
              </el-form-item>
            </el-col>
            <el-col class="line" :span="2">-</el-col>
            <el-col :span="11">
              <el-form-item prop="endTime">
                <el-time-picker
                  placeholder="选择结束时间"
                  v-model="form.endTime"
                  style="width: 100%"
                ></el-time-picker>
              </el-form-item>
            </el-col>
          </el-form-item>
          <el-form-item label="商家公告" prop="notice">
            <el-input type="textarea" v-model="form.notice"></el-input>
          </el-form-item>
        </el-form>
      </el-main>
      <el-footer class="wn-footer"
        ><el-button type="primary" size="small" @click="save('form')"
          >保存</el-button
        >
        <el-button @click="reset('form')" size="small"
          >重置</el-button
        ></el-footer
      >
    </el-container>
    <el-dialog v-model="outerVisible" title="店铺选址" width="50%">
      <el-main style="width: auto; height: 700px">
        <iframe
          src="/selectMapCenter.html"
          ref="frame"
          width="100%"
          height="100%"
          scrolling="no"
        ></iframe>
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
import { regionData } from "element-china-area-data";
import { GetInfo, Edit } from "@/api/shopinfo.js";
import { messageShow } from "@/assets/js/Common.js";

export default {
  data() {
    return {
      limit: 10,
      loading: false,
      options: regionData,
      dialogImageUrl: "",
      dialogVisible: false,
      outerVisible: false,
      address: "",
      poiData: "",
      form: {
        id: "",
        shopId: localStorage.getItem("currentUserId"),
        shopname: "",
        storeImg: "",
        instoreImg: [],
        contactMan: "",
        contactMobile: "",
        beginTime: "",
        endTime: "",
        email: "",
        province: "",
        city: "",
        district: "",
        address: "",
        longitude: "", //经度
        latitude: "", //维度
        notice: "",
      },
      rules: {
        shopname: [
          { required: true, message: "请输入用户名", trigger: "blur" },
        ],
      },
    };
  },
  mounted() {
    window.addEventListener("message", this.handleMessage);
    this.loaddata();
  },
  methods: {
    async loaddata() {
      this.loading = true;
      await GetInfo({ id: this.form.shopId })
        .then((res) => {
          if (res.success && res.result !== null) {
            this.form = res.result;
            this.address = res.result.district;
            if (
              res.result.instoreImg === null ||
              res.result.instoreImg === ""
            ) {
              this.form.instoreImg = [];
            } else {
              this.form.instoreImg = JSON.parse(res.result.instoreImg);
            }
            this.loading = false;
          }
        })
        .catch((err) => {
          this.loading = false;
        });
    },
    save(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.form.instoreImg = JSON.stringify(this.form.instoreImg);
          this.form.district = this.address;
          this.loading = true;
          Edit(this.form)
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
        } else {
          messageShow("error", "还有未填字段");
          return false;
        }
      });
    },
    reset(formName) {
      this.$refs[formName].resetFields();
    },
    httpRequest({ file }) {
      //阿里云OSS上传
      const fileName = `${Date.parse(new Date())}/${file.name}`; //定义唯一的文件名
      let OSS = require("ali-oss");
      var client = new OSS({
        region: "oss-cn-hangzhou", //节点
        accessKeyId: "accessKeyId",
        accessKeySecret: "accessKeySecret",
        bucket: "yangdonglin",
      });

      client
        .put(fileName, file)
        .then(({ res, url, name }) => {
          if (res && res.status == 200) {
            this.form.storeImg = url;
          }
        })
        .catch((err) => {
          console.log(`阿里云OSS上传失败回调`, err);
        });
    },
    handleRemove(e, fileList) {
      this.fileList = fileList;
    },
    beforeAvatarUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isLt2M) {
        this.$message.error("文件大小不能超过 2M !");
      }
      return isLt2M;
    },
    showMap() {
      this.outerVisible = true;
    },
    handleMessage(event) {
      // 获取从选址页面中传过来的值
      var poiData = event.data.params;
      if (poiData !== null && poiData !== "") {
        this.address = poiData.district + poiData.name;
        this.form.longitude = poiData.location.lng;
        this.form.latitude = poiData.location.lat;
      }
    },
  },
};
</script>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  display: block;
}
.el-dialog__headerbtn {
  top: 5px;
}
.el-form-item {
  margin-bottom: 20px;
}
</style>
