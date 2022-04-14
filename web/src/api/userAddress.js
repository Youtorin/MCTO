import { post } from "@/plugins/ajax";


/*
 * 获取用户列表
 */
export const GetAddressList = (param) => post("/api/userAddress/getList", param);
