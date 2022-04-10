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
      <el-breadcrumb-item>门店管理</el-breadcrumb-item>
    </el-breadcrumb>
    <el-header></el-header>
    <el-main class="wnw-main">
      <el-form
        :model="form"
        :rules="rules"
        ref="form"
        label-width="100px"
        class="demo-form"
      >
        <el-form-item label="门店图片" prop="storeImg">
          <el-upload
            class="avatar-uploader"
            action=""
            :limit="limit"
            :auto-upload="false"
            :on-change="UploadChange"
            :show-file-list="false"
          >
            <el-image v-if="form.storeImg" :src="form.storeImg" :fit="cover" />
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
              <el-cascader
                size="large"
                :options="options"
                v-model="this.address"
                :props="{ value: 'label' }"
                @change="handleChange"
                :show-all-levels="false"
                clearable
              >
              </el-cascader>
              <el-input
                style="width: 80%; padding: 0 10px"
                placeholder="请输入详细地址"
                v-model="form.address"
              ></el-input>
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
      <el-button @click="reset('form')" size="small">重置</el-button></el-footer
    >
  </el-container>
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
      address: [],
      form: {
        id: localStorage.getItem("currentUserId"),
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
    this.loaddata();
  },
  methods: {
    async loaddata() {
      this.loading = true;
      await GetInfo({ id: this.form.id })
        .then((res) => {
          if (res.success && res.result !== null) {
            this.form = res.result;
            if (
              res.result.instoreImg === null ||
              res.result.instoreImg === ""
            ) {
              this.form.instoreImg = [];
            } else {
              this.form.instoreImg = JSON.parse(res.result.instoreImg);
            }
            this.address.push(res.result.province);
            this.address.push(res.result.city);
            this.address.push(res.result.district);
            this.loading = false;
            console.log(this.address);
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
          console.log(this.form);
        } else {
          messageShow("error", "还有未填字段");
          return false;
        }
      });
    },
    reset(formName) {
      this.$refs[formName].resetFields();
    },
    handleChange(value) {
      this.form.province = value[0];
      this.form.city = value[1];
      this.form.district = value[2];
      console.log(this.address);
    },
    UploadChange(file, fileList) {
      const isJPG = file.raw.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("上传图片只能是 JPG 格式!");
        return isJPG;
      }
      if (!isLt2M) {
        this.$message.error("上传图片大小不能超过 2MB!");
        return isLt2M;
      }
      var reader = new FileReader();
      reader.readAsDataURL(file.raw);
      reader.onload = () => {
        this.form.storeImg = reader.result;
      };
    },
    handlePictureCard(file) {
      var reader = new FileReader();
      reader.readAsDataURL(file.raw);
      reader.onload = () => {
        this.form.instoreImg.push(reader.result);
      };
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
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
</style>