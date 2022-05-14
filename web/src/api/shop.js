import { post } from "@/plugins/ajax";

/*
 * 获取商店登录信息
 */
export const Login = (param) => post("/api/shop/getCurrShopInfo", param);

/*
 * 获取商店基本信息
 */
export const GetInfo = (param) => post("/api/shop/getInfo", param);


/*
 * 修改商店基本信息
 */
export const Edit = (param) => post("/api/shop/edit", param);


/*
 * 修改商店基本信息
 */
export const AddTodo = (param) => post("/api/shop/addTodo", param);
