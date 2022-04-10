<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-card shadow="hover">
          <template #header>
            <div class="clearfix">
              <span>基础信息</span>
            </div>
          </template>
          <div class="info">
            <div class="info-image" @click="showDialog">
              <img :src="avatarImg" />
              <span class="info-edit">
                <i class="el-icon-lx-camerafill"></i>
              </span>
            </div>
            <div class="info-name">{{ form.shopname }}</div>
            <span
              class="info-desc"
              v-if="form.remark !== '' && form.remark !== null"
              >{{ form.remark }}</span
            >
            <span class="info-desc" v-else>还什么都没写...</span>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row>
      <el-container
        style="height: 100%"
        class="background-6F9 eig-mian-doc"
        v-loading="loading"
      >
        <el-col :span="24">
          <el-card shadow="hover">
            <template #header>
              <div class="clearfix">
                <span>账户编辑</span>
              </div>
            </template>
            <el-form
              :model="form"
              :rules="rules"
              ref="form"
              label-width="100px"
            >
              <el-form-item label="用户名" prop="shopname">
                <el-input type="text" v-model="form.shopname"></el-input
              ></el-form-item>

              <el-form-item label="手机号" prop="mobile">
                <el-input type="text" v-model="form.mobile"></el-input
              ></el-form-item>

              <el-form-item label="邮箱" prop="email">
                <el-input type="text" v-model="form.email"></el-input
              ></el-form-item>
              <el-form-item label="旧密码" prop="oldPassword">
                <el-input type="password" v-model="form.oldPassword"></el-input>
              </el-form-item>
              <el-form-item label="新密码" prop="newPassword">
                <el-input type="password" v-model="form.newPassword"></el-input>
              </el-form-item>
              <el-form-item label="个人简介" prop="remark">
                <el-input v-model="form.remark"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="save">保存</el-button>
              </el-form-item>
            </el-form>
          </el-card>
        </el-col>
      </el-container>
    </el-row>
    <el-dialog title="裁剪图片" v-model="dialogVisible" width="600px">
      <vue-cropper
        ref="cropper"
        :src="imgSrc"
        :ready="cropImage"
        :zoom="cropImage"
        :cropmove="cropImage"
        style="width: 100%; height: 400px"
      ></vue-cropper>

      <template #footer>
        <span class="dialog-footer">
          <el-button class="crop-demo-btn" type="primary"
            >选择图片
            <input
              class="crop-input"
              type="file"
              name="image"
              accept="image/*"
              @change="setImage"
            />
          </el-button>
          <el-button type="primary" @click="saveAvatar">上传并保存</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { reactive, ref } from "vue";
import VueCropper from "vue-cropperjs";
import "cropperjs/dist/cropper.css";
import avatar from "@/assets/img/img.jpg";
import { GetInfo, Edit } from "@/api/shop.js";
import { messageShow } from "@/assets/js/Common.js";
export default {
  name: "user",
  components: {
    VueCropper,
  },
  data() {
    return {
      loading: false,
      form: {
        id: localStorage.getItem("currentUserId"),
        shopname: "",
        shopaccount: "",
        oldPassword: "",
        newPassword: "",
        password: "",
        mobile: "",
        email: "",
        remark: "",
      },
      rules: {
        shopname: [
          { required: true, message: "请输入用户名", trigger: "blur" },
        ],
        oldPassword: [
          { required: true, message: "请输入旧密码", trigger: "blur" },
        ],
        newPassword: [
          { required: true, message: "请输入新密码", trigger: "blur" },
        ],
      },
    };
  },
  mounted() {
    this.loaddata();
  },
  methods: {
    save() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.password !== this.form.oldPassword) {
            messageShow("error", "旧密码错误");
          } else {
            this.form.password = this.form.newPassword;
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
          }
        } else {
          messageShow("error", "还有未填字段");
          return false;
        }
      });
    },
    async loaddata() {
      this.loading = true;
      await GetInfo({ id: this.form.id })
        .then((res) => {
          if (res.success && res.result !== null) {
            this.form = res.result;
            this.loading = false;
          }
        })
        .catch((err) => {
          this.loading = false;
        });
    },
  },
  setup() {
    const avatarImg = ref(avatar);
    const imgSrc = ref("");
    const cropImg = ref("");
    const dialogVisible = ref(false);
    const cropper = ref(null);

    const showDialog = () => {
      dialogVisible.value = true;
      imgSrc.value = avatarImg.value;
    };

    const setImage = (e) => {
      const file = e.target.files[0];
      if (!file.type.includes("image/")) {
        return;
      }
      const reader = new FileReader();
      reader.onload = (event) => {
        dialogVisible.value = true;
        imgSrc.value = event.target.result;
        cropper.value && cropper.value.replace(event.target.result);
      };
      reader.readAsDataURL(file);
    };

    const cropImage = () => {
      cropImg.value = cropper.value.getCroppedCanvas().toDataURL();
    };

    const saveAvatar = () => {
      avatarImg.value = cropImg.value;
      dialogVisible.value = false;
    };

    return {
      cropper,
      avatarImg,
      imgSrc,
      cropImg,
      showDialog,
      dialogVisible,
      setImage,
      cropImage,
      saveAvatar,
    };
  },
};
</script>

<style scoped>
.info {
  text-align: center;
  padding: 35px 0;
}
.info-image {
  position: relative;
  margin: auto;
  width: 100px;
  height: 100px;
  background: #f8f8f8;
  border: 1px solid #eee;
  border-radius: 50px;
  overflow: hidden;
}
.info-image img {
  width: 100%;
  height: 100%;
}
.info-edit {
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  opacity: 0;
  transition: opacity 0.3s ease;
}
.info-edit i {
  color: #eee;
  font-size: 25px;
}
.info-image:hover .info-edit {
  opacity: 1;
}
.info-name {
  margin: 15px 0 10px;
  font-size: 24px;
  font-weight: 500;
  color: #262626;
}
.crop-demo-btn {
  position: relative;
}
.crop-input {
  position: absolute;
  width: 100px;
  height: 40px;
  left: 0;
  top: 0;
  opacity: 0;
  cursor: pointer;
}
</style>