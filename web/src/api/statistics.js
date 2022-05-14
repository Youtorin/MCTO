import { post } from "@/plugins/ajax";

/*
 * 获取商店信息
 * 接口页面:/api/-useraddress/getList
 */
export const GetStatistics = () => post("/api/statistics/getStatistics");