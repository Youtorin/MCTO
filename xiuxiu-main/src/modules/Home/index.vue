<template>
  <div>
    <Rank></Rank>
  </div>
</template>

<script>
import Rank from "@/modules/Home/Rank";

// import { mapState } from "vuex";

export default {
  name: "Home",
  components: {
    Rank,
  },
  mounted() {
    // this.$store.dispatch("getFloorList");
  },
  computed: {
    // ...mapState({
    //   floorList: (state) => state.home.floorList,
    // }),
  },
};
</script>

<style scoped>
</style>
