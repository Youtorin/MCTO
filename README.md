## 简介

​        智能外卖送餐系统是学校毕设项目，在普通外卖店铺系统点餐下单的基础上增加了配送环节，当订单业务状态处于待派送状态时，商家在执行发货操作后订单进入配送列表，然后在配送模块能根据订单上的配送地址与商家地址计算距离，然后通过距离、配送时间对多个订单进行优化排序，生成配送顺序，同时在模块中的地图模拟出配送路线，实现路线规划。

​        采用前后端分离开发模式，整个项目前端部分为：用户端、商家端

## 技术栈

#### 后端

SpirngBoot、MyBatis-Plus、MySql

#### 前端

Nodejs、vue、Element-UI

#### 接口调用

地图生成调用了高德地图的API，图片存储使用阿里云的对象存储服务

## 预览

### 商家端

###### 商家首页：

![image-20220615233230692](https://s3.bmp.ovh/imgs/2022/06/16/e848762ecb0bc1dd.png)

###### 订单管理：

![image-20220615233318347](https://s3.bmp.ovh/imgs/2022/06/16/810d6d719217b63c.png)

###### 门店评价：

![image-20220615233335925](https://s3.bmp.ovh/imgs/2022/06/16/062d381944c745ba.png)

###### 商品管理：

![image-20220615233420186](https://s3.bmp.ovh/imgs/2022/06/16/224389fc1a775df7.png)

![image-20220615233429407](https://i.postimg.cc/SRmk0w8R/image-20220615233429407.png)

###### 分类列表：

![image-20220615233441484](https://i.postimg.cc/25ppRsNY/image-20220615233441484.png)

###### 顾客管理：

![image-20220615233523830](https://i.postimg.cc/j5MJmY5J/image-20220615233523830.png)

###### 配送状态：

![image-20220615233535118](https://i.postimg.cc/y8RcdGfw/image-20220615233535118.png)

![image-20220615233545618](https://i.postimg.cc/3Nw2xJ6B/image-20220615233545618.png)

订单发货后进入配送列表，根据顾客订单地址和商家的距离生成配送预览路径，帮助商家更快选择配送顺序

###### 门店管理：

![image-20220615233718749](https://i.postimg.cc/Jnxb8BT0/image-20220615233718749.png)

### 用户端：

###### 门户首页：

![image-20220615233809671](https://i.postimg.cc/6QsVfK2W/image-20220615233809671.png)

###### 店内评价：

![image-20220615233924184](https://i.postimg.cc/8P5b1tgS/image-20220615233924184.png)

###### 购物车：

![image-20220615233943800](https://i.postimg.cc/qRTL4r4b/image-20220615233943800.png)

###### 订单创建：

![image-20220615234014074](https://i.postimg.cc/bNC3b49r/image-20220615234014074.png)

![image-20220615234036201](https://i.postimg.cc/gj44g79M/image-20220615234036201.png)

![image-20220615234045153](https://i.postimg.cc/V6qKC6KP/image-20220615234045153.png)

订单列表：

![image-20220615234058333](https://i.postimg.cc/2SqtdJSc/image-20220615234058333.png)

用户资料：

![image-20220615234122438](https://i.postimg.cc/PJr3CV7M/image-20220615234122438.png)



## 启动

### 前言

修改web(xiuxiu-main)\public目录下的mapIfram.html文件和selectMapCenter.html文件中的

```html
<script type="text/javascript">
        window._AMapSecurityConfig = {
            securityJsCode: '改这里，高德地图api安全码',
        }
    </script>
    <script src="https://a.amap.com/jsapi_demos/static/demo-center/js/demoutils.js"></script>
    <script type="text/javascript"
        src="https://webapi.amap.com/maps?v=1.4.15&key=改这里，高德地图api的key值&plugin=AMap.Driving&plugin=AMap.Driving"></script>

```

修改web\src\api目录下的aliyun.oss.client.js文件的

```javascript
export let client = new OSS({
    region: 'oss-cn-hangzhou',//节点
    accessKeyId: '改id',
    accessKeySecret: '改secret',
    bucket: 'bucket存储桶名'
})
```
修改数据库配置文件

分别启动web、xiuxiu-main下的项目和mcto后端

### Web&xiuxiu-main

```
# install dependencies
npm install

# serve with hot reload at localhost:8080
npm run dev

# build for production with minification
npm run build

# build for production and view the bundle analyzer report
npm run build --report

# run e2e tests
npm run e2e

# run all tests
npm test
```

