import { post, get } from '@/plugins/ajax'

/*
 * 获取实体列表
 * 接口页面:/api/-useraddress/getList
 */

export const GetModelList = param => post('/api/UserAddress/getList', param)

