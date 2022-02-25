package com.yangdonglin.mcto.controller;


import com.yangdonglin.mcto.entity.CfgUseraddress;
import com.yangdonglin.mcto.module.AjaxResponse;
import com.yangdonglin.mcto.module.BaseController;
import com.yangdonglin.mcto.service.CfgUseraddressService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 用户配送地址表 前端控制器
 * </p>
 *
 * @author Yangdonglin
 * @since 2022-02-21
 */
@RestController
@RequestMapping("/cfg-useraddress")
public class CfgUseraddressController extends BaseController {

    @Resource
    CfgUseraddressService cfgUseraddressService;

    @PostMapping("getList")
    public AjaxResponse getList(){
        CfgUseraddress result = cfgUseraddressService.getById('1');
        if(result!=null){
            return AjaxResponse.success(result);
        }else {
            return AjaxResponse.error(AjaxResponse.ErrorInfo.ERR_SQL_NOT_EXITS);
        }

    }
}
