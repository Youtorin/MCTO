<template>
  <div class="map-content">
    <div class="page" id="map-container"></div>
    <div id="panel"></div>
  </div>
</template>
<script>
import AMap from "AMap";
import AMapUI from "AMapUI";
export default {
  name: "Amap",

  data() {
    return {
      map: null,
    };
  },
  mounted() {
    // 地图初始化
    this.map = new AMap.Map("map-container", {
      resizeEnable: true,
      center: [108.9470386505, 34.2593889736], // 地图中心点
      zoom: 16, // 地图显示的缩放级别
    });
    // 'AMap.ToolBar'集成了缩放、平移、定位等功能,'AMap.Scale'展示地图在当前层级和纬度下的比例尺
    // 添加需要操作的类AMap.Transfer(公交换乘[不包含地铁]),AMap.Geolocation(浏览器精准定位)
    // 公交站点查询
    AMap.plugin(
      [
        "AMap.ToolBar",
        "AMap.Scale",
        "AMap.Transfer",
        "AMap.Geolocation",
        "AMap.StationSearch",
      ],
      () => {
        this.map.addControl(new AMap.ToolBar());
        this.map.addControl(new AMap.Scale());
        this.map.addControl(new AMap.Transfer());
        this.map.addControl(new AMap.Geolocation());
        this.map.addControl(new AMap.StationSearch());
      }
    );
    this.getRoute(); // 获取线路规划
  },
  methods: {
    // 1.路线规划，不乘坐地铁2.自行车出行（暂时不做）3.最快捷模式（综合出行[包含地铁]），用1,2,3来区分出行方式（goMethod）
    // 路线规划文档地址https://lbs.amap.com/api/javascript-api/reference/route-search#m_TransferPolicy
    getRoute() {
      new AMap.Transfer({
        map: this.map,
        panel: "panel",
      }).search(
        [
          { keyword: "宁波大学", city: "宁波" },
          { keyword: "宁波老外滩", city: "宁波" },
        ],
        function (status, data) {
          console.log(data);
        }
      );
    },
  },
};
</script>
<style  scoped>
.page {
  height: calc(100vh - 50px);
}
.map-content {
  position: relative;
}
#panel {
  position: absolute;
  top: 0;
  right: 0;
}
</style>