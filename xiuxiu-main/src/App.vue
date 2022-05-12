<template>
  <div id="app">
    <!--    头部-->
    <Header v-show="$route.meta.show"></Header>
    <!--   我是跟标签-->
    <router-view></router-view>
  </div>
</template>

<script>
import Header from "@/components/Header";
export default {
  name: "App",
  components: {
    Header,
  },
  mounted() {
    // this.$store.dispatch('categoryList')
  },
};
</script>

<style>
</style>
