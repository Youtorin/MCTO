import { post } from "@/plugins/ajax";


/*
 * 获取订单列表-全部订单
 */
export const GetOrderList = (param) => post("/api/order/getList", param);


/*
 * 统计各类型订单数量
 */
export const SelectCount = (param) => post("/api/order/selectCount", param);

/*
 * 修改订单状态
 */
export const SetOrderStatus = (param) => post("/api/order/setOrderStatus", param);

/*
 * 退款
 */
export const HandlerRefund = (param) => post("/api/order/handlerRefund", param);

/*
 * 拒绝
 */
export const HandlerCancel = (param) => post("/api/order/handlerCancel", param);
