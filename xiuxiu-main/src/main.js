import Vue from "vue";
import App from "./App.vue";
import router from "@/router";
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
import tool from "@/assets/js/Common";
import "./assets/css/icon.css";
import "./assets/css/common.css";
import "./assets/css/newStyle.css";

import "swiper/css/swiper.css";
Vue.config.productionTip = false;

Vue.use(ElementUI);
Vue.use(tool);
// Vue.component(Carouse.name, Carouse)
Vue.component(ElementUI.Pagination.name, ElementUI.Pagination);
new Vue({
  beforeCreate() {
    Vue.prototype.$bus = this;
  },
  router,

  render: (h) => h(App),
}).$mount("#app");
