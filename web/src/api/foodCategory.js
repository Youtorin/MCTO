import { post } from "@/plugins/ajax";

/*
 * 获取分类分页列表
 */
export const GetPageList = (param) => post("/api/foodcategory/getPageList", param);


/*
 * 获取分类列表
 */
export const GetList = () => post("/api/foodcategory/getList");

/*
 * 获取分类
 */
export const GetModel = () => post("/api/foodcategory/getModel");

/*
 * 删除分类实体
 */
export const Delete = (param) => post("/api/foodcategory/delete", param);


/*
 * 修改商店基本信息
 */
export const Edit = (param) => post("/api/foodcategory/edit", param);
