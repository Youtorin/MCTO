import { post } from "@/plugins/ajax";


/*
 * 获取用户地址列表
 */
export const GetAddressList = (param) => post("/api/userAddress/getList", param);

/*
 * 获取用户地址实体
 */
export const GetAddressModel = (param) => post("/api/userAddress/getModel", param);