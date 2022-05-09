import { post } from "@/plugins/ajax";

/*
 * 获取商店信息
 * 接口页面:/api/-useraddress/getList
 */
export const GetInfo = (param) => post("/api/shopinfo/getInfo", param);


/*
 * 修改商店信息
 * 接口页面:/api/-useraddress/getList
 */
export const Edit = (param) => post("/api/shopinfo/edit", param);