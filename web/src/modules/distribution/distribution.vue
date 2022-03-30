<template>
  <div>
    <div id="map"></div>
    <div class="layui-btn-container" id="btns">
      <button
        type="button"
        class="layui-btn layui-btn-sm"
        @click="operationManual"
      >
        操作提示
      </button>
      <button type="button" class="layui-btn layui-btn-sm" @click="setDrawMode">
        绘制模式
      </button>
      <button
        type="button"
        class="layui-btn layui-btn-sm"
        @click="setInteractMode"
      >
        编辑模式
      </button>
      <button
        type="button"
        class="layui-btn layui-btn-sm"
        @click="location.reload"
      >
        重载
      </button>
      <button
        type="button"
        class="layui-btn layui-btn-normal layui-btn-sm"
        @click="submit"
      >
        提交选中区域数据
      </button>
    </div>
    <div id="polygon">
      <div class="layui-inline">
        <div class="layui-input-inline">
          <input
            type="text"
            class="layui-input"
            placeholder="请输入纬度,经度"
            ondblclick="getLatLng()"
          />
        </div>
      </div>
      <div class="layui-inline">
        <div class="layui-input-inline">
          <button
            type="button"
            class="layui-btn layui-btn-danger"
            @click="isPointInPolygon"
          >
            检测
          </button>
        </div>
      </div>
    </div>
    <blockquote
      class="layui-elem-quote layui-anim layui-anim-fadein layui-hide"
      id="manual"
    >
      <p><strong>绘制：</strong>鼠标左键点击及移动即可绘制图形</p>
      <p>
        <strong>结束绘制：</strong
        >鼠标左键双击即可结束绘制折线、多边形、圆形，多边形会自动闭合
      </p>
      <p><strong>单选：</strong>鼠标左键点击图形</p>
      <p><strong>多选：</strong>按下ctrl键后点击多个图形</p>
      <p><strong>删除：</strong>选中图形后按下delete键可删除图形</p>
      <p>
        <strong>拆分：</strong
        >选中单个多边形后可绘制拆分线，拆分线绘制完成后自动进行拆分
      </p>
      <p><strong>中断：</strong>绘制或编辑过程中按下esc键可中断该过程</p>
    </blockquote>
  </div>
</template>


<script>
import "@/assets/css/layui.min.css";
import "@/assets/js/gljs.json";
export default {
  data() {
    return {
      editor: [],
    };
  },
  async mounted() {
    await layui.$.getJSON("src/assets/js/latlng.json", (res) => {
      //图层数据
      var overlays = res.data.overlays;
      //标记数据
      var markers = res.data.markers;

      var map = new TMap.Map(document.getElementById("map"), {
        //设置地图中心点坐标
        center: new TMap.LatLng(markers[0].lat, markers[0].lng),
        //设置地图缩放级别
        zoom: 9,
        // viewMode: "2D",
      });

      map.on("click", function (evt) {
        var lat = evt.latLng.getLat().toFixed(6);
        var lng = evt.latLng.getLng().toFixed(6);
        console.log(lat + "," + lng);
      });

      //控件
      var control = map.getControl(TMap.constants.DEFAULT_CONTROL_ID.ZOOM);
      control.setPosition(TMap.constants.CONTROL_POSITION.BOTTOM_RIGHT);
      control.setNumVisible(true);

      //标记
      var label = new TMap.MultiLabel({
        //设置折线图层显示到哪个地图实例中
        map: map,
        //文字标记样式
        styles: {
          blue: new TMap.LabelStyle({
            color: "#3777FF", //颜色属性
            size: 20, //文字大小属性
            offset: { x: 0, y: 0 }, //文字偏移属性单位为像素
            angle: 0, //文字旋转属性
            alignment: "center", //文字水平对齐属性
            verticalAlignment: "middle", //文字垂直对齐属性
          }),
        },
        //文字标记数据
        geometries: markers.map((item) => {
          return {
            styleId: item.styleId, //样式id
            position: new TMap.LatLng(item.lat, item.lng), //标注点位置
            content: item.name, //标注文本
          };
        }),
      });

      /**
       * 几何图形编辑器
       * @link https://lbs.qq.com/webApi/javascriptGL/glDoc/glDocEditor
       */
      this.editor = new TMap.tools.GeometryEditor({
        //编辑几何图形的地图对象
        map: map,
        //用于编辑的几何图层 支持MultiMarker/MultiPolyline/MultiPolygon/MultiRectangle/MultiCircle/MultiEllipse
        overlayList: [
          {
            //标识，默认为图层id
            id: "polygon",
            //在控件下拉列表中显示的图层名称
            name: "多边形",
            //几何图层，支持MultiMarker/MultiPolyline/MultiPolygon/MultiRectangle/MultiCircle/MultiEllipse
            // @link https://lbs.qq.com/Vis/jue/APIDoc/jueoverlay
            overlay: new TMap.MultiPolygon({
              //图层id
              id: "",
              map: map,
              //图层样式映射表
              styles: {
                red: new TMap.PolygonStyle({
                  color: "rgba(208, 80, 80, 0.16)", //面填充色
                  showBorder: true, //是否显示拔起面的边线
                  borderColor: "rgba(208, 80, 80, 1)",
                }),
                green: new TMap.PolygonStyle({
                  color: "rgba(64, 160, 128, 0.16)", //面填充色
                  showBorder: true, //是否显示拔起面的边线
                  borderColor: "rgba(64, 160, 128, 1)",
                }),
              },
              //数据数组 多边形数据
              // @link https://lbs.qq.com/Vis/jue/APIDoc/juepolygon
              geometries: overlays.map((overlay) => {
                return {
                  //多边形图形数据的标志信息，不可重复
                  id: overlay.id,
                  styleId: overlay.styleId,
                  //多边形的边界路径信息
                  paths: overlay.paths.map((path) => {
                    return new TMap.LatLng(path.lat, path.lng);
                  }),
                  //多边形垂直于地面方向的偏移高度
                  offset: 0,
                  //多边形的属性数据
                  properties: {},
                };
              }),
            }),
            //绘制图形的样式id，对应图层styles样式表中的id，默认为default
            drawingStyleId: "",
            //选中图形的样式id，对应图层styles样式表中的id，若不设置则使用默认样式
            selectedStyleId: "",
          },
        ],
        //处于编辑状态的图层id，编辑状态下的图层可以新增图形、选中图形进行修改和删除
        activeOverlayId: "polygon",
        //编辑器的操作状态 INTERACT 交互模式，该模式下用户可选中图形进行删除、修改; DRAW	绘制模式，该模式下用户可绘制新图形
        actionMode: TMap.tools.constants.EDITOR_ACTION.INTERACT,
        //是否开启吸附功能，默认为false
        snappable: true,
        //是否开启点选功能，默认为false，开启后可以点选图形进行修改和删除操作
        selectable: true,
        //编辑器的icon样式
        iconInfo: {},
      });
      // 绘制完成，返回几何图形
      this.editor.on("draw_complete", (geometry) => {
        console.log("on draw_complete");
        console.log(geometry);
      });
      // 修改完成，返回修改后的几何图形
      this.editor.on("adjust_complete", (geometry) => {
        console.log("on adjust_complete");
        console.log(geometry);
      });
      // 删除完成，返回被删除的几何图形
      this.editor.on("delete_complete", (geometry) => {
        console.log("on adjust_complete");
        console.log(geometry);
      });
      // 点选图形
      this.editor.on("select", function () {
        console.log("on select");
        //console.log(this.editor.getActiveOverlay());
        // console.log(Math.round(TMap.geometry.computeArea(this.editor.getSelectedList()[0]['paths'])) + '平方米');
      });
    });
  },
  methods: {
    setInteractMode() {
      this.editor.setActionMode(TMap.tools.constants.EDITOR_ACTION.INTERACT);
    },

    setDrawMode() {
      this.editor.setActionMode(TMap.tools.constants.EDITOR_ACTION.DRAW);
    },

    operationManual() {
      layui.$("blockquote").hasClass("layui-hide")
        ? layui.$("blockquote").removeClass("layui-hide")
        : layui.$("blockquote").addClass("layui-hide");
    },

    submit() {
      console.log(this.editor);
      console.log(this.editor.properties);
      console.log("-------------");
      console.log(this.editor.properties(getSelectedList).length);
      if (this.editor.properties(getSelectedList).length === 1) {
        layui.layer.confirm("确定提交？", function (index) {
          console.log(this.editor.getSelectedList()[0]);
          layui.layer.close(index);
        });
      } else {
        layui.layer.msg("必须且有且只能选中一块");
      }
    },

    getLatLng() {
      layui.layer.open({
        type: 2,
        title: "腾讯坐标拾取器",
        content: ["https://lbs.qq.com/getPoint/", "no"],
        area: ["90%", "90%"],
        maxmin: true,
      });
    },

    isPointInPolygon() {
      var pos = layui.$("#polygon input").val().trim();
      if (pos.length === 0) {
        layui.layer.msg("请输入坐标");
        return;
      }
      var posArr = pos.trim().split(",");
      if (posArr.length !== 2) {
        layui.layer.msg("坐标格式非法");
        return;
      }
      if (this.editor.getSelectedList().length !== 1) {
        layui.layer.msg("请选中一块区域");
        return;
      }
      var isIn = TMap.geometry.isPointInPolygon(
        new TMap.LatLng(posArr[0], posArr[1]),
        this.editor.getSelectedList()[0].paths.map((item) => {
          return new TMap.LatLng(item.lat, item.lng);
        })
      );
      layui.layer.msg(isIn ? "范围内" : "范围外");
    },
  },
};
</script>

<style>
html,
body {
  height: 100%;
  margin: 0;
  padding: 0;
  position: relative;
}

#map {
  width: 100%;
  height: 100%;
}

#btns {
  position: absolute;
  z-index: 1001;
  top: 10px;
  left: 10px;
}

#manual {
  position: absolute;
  z-index: 1001;
  top: 40px;
  left: 10px;
}

#polygon {
  position: absolute;
  z-index: 1000;
  top: 50px;
  left: 10px;
}
</style>