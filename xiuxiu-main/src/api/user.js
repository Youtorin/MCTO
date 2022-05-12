import { post } from "@/plugins/ajax";


/*
 * 获取用户登录信息
 */
export const Login = (param) => post("/api/user/getCurrUserInfo", param);


/*
 * 获取用户信息
 */
export const GetModel = (param) => post("/api/user/getModel", param);

/*
 * 用户注册
 */
export const Register = (param) => post("/api/user/edit", param);


/*
 * 用户修改信息
 */
export const Edit = (param) => post("/api/user/edit", param);

/*
 * 余额增减
 */
export const EditWallet = (param) => post("/api/user/editWallet", param);