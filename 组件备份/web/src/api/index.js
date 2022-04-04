import request from '../utils/request';
import { post, get } from '../plugins/ajax'

/*
 * 获取实体列表
 * 接口页面:/api/services/app/-useraddress/getList
 */

export const GetModelList = param => post('-useraddress/getList', param)

export const fetchData = query => {
    return request({
        url: './table.json',
        method: 'get',
        params: query
    });
};
