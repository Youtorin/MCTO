import { post } from "@/plugins/ajax";


/*
 * 获取用户列表
 */
export const GetList = (param) => post("/api/user/getList", param);


/*
 * 删除用户
 */
export const Delete = (param) => post("/api/user/delete", param);


/*
 * 获取单个用户信息
 */
export const GetModel = (param) => post("/api/user/getModel", param);

/*
 * 新增/修改用户信息
 */
export const Edit = (param) => post("/api/user/edit", param);