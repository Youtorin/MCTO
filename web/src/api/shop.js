import { post } from "@/plugins/ajax";

/*
 * 获取商店登录信息
 * 接口页面:/api/-useraddress/getList
 */
export const Login = (param) => post("/api/shop/getCurrShopInfo", param);
