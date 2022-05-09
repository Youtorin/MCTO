<template>
  <div id="app">
<!--    头部-->
    <Header v-show="$route.meta.show"></Header>
    <Nav v-show="$route.meta.show"></Nav>
<!--   我是跟标签-->
    <router-view></router-view>
    
  </div>
</template>

<script>
import Header from '@/components/Header'

import Nav from '@/components/Nav'
export default {
  name: 'App',
  components: {
    Header,
   
    Nav
  },
  mounted() {
    // this.$store.dispatch('categoryList')
  }
}
</script>

<style>

</style>
