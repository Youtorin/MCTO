import { post } from "@/plugins/ajax";


/*
 * 获取用户地址列表
 */
export const GetAddressList = (param) => post("/api/userAddress/getList", param);


/*
 * 新增地址
 */
export const EditAddress = (param) => post("/api/userAddress/editAddress", param);

/*
 * 设为默认地址
 */
export const SetDefault = (param) => post("/api/userAddress/setDefault", param);

/*
 * 设为默认地址
 */
export const DeleteAddress = (param) => post("/api/userAddress/deleteAddress", param);
