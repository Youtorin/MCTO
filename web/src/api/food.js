import { post } from "@/plugins/ajax";

/*
 * 获取商品列表
 */
export const GetList = (param) => post("/api/food/getList", param);


/*
 * 获取商品实体
 */
export const GetModel = () => post("/api/food/getModel");

/*
 * 删除商品实体
 */
export const Delete = (param) => post("/api/food/delete", param);


/*
 * 修改商品
 */
export const Edit = (param) => post("/api/food/edit", param);
