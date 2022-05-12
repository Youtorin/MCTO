import { post } from "@/plugins/ajax";


/*
 * 获取订单列表-全部订单
 */
export const GetListByUser = (param) => post("/api/order/getListByUser", param);


/*
 * 创建订单
 */
export const AddOrder = (param) => post("/api/order/addOrder", param);

/*
 * 修改订单状态
 */
export const SetOrderStatus = (param) => post("/api/order/setOrderStatus", param);

/*
 * 退款申请
 */
export const Refund = (param) => post("/api/order/refund", param);

