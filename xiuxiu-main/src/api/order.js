import { post } from "@/plugins/ajax";


/*
 * 获取订单列表-全部订单
 */
export const GetOrderList = (param) => post("/api/order/getList", param);


/*
 * 创建订单
 */
export const AddOrder = (param) => post("/api/order/addOrder", param);