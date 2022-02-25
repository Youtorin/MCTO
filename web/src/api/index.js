import request from '../utils/request';
import { post, get } from '../plugins/ajax'

/*
 * 获取实体列表
 * 接口页面:/api/services/app/cfg-useraddress/getList
 */

export const GetModelList = param => post('/api/services/app/cfg-user/getList', param)

export const fetchData = query => {
    return request({
        url: './table.json',
        method: 'get',
        params: query
    });
};
