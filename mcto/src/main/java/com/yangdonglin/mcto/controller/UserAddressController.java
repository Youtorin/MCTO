package com.yangdonglin.mcto.controller;


import com.yangdonglin.mcto.entity.UserAddress;
import com.yangdonglin.mcto.module.AjaxResponse;
import com.yangdonglin.mcto.module.BaseController;
import com.yangdonglin.mcto.service.UserAddressService;
import org.springframework.web.bind.annotation.*;

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
@CrossOrigin(allowCredentials="true")
@RequestMapping("/api/UserAddress")
public class UserAddressController extends BaseController {

    @Resource
    UserAddressService UserAddressService;

    @PostMapping("/getList")
    public AjaxResponse getList(@RequestBody UserAddress param){
        UserAddress result = UserAddressService.getById('1');
        System.out.println(111);
        if(result!=null){
            System.out.println(result);
            return AjaxResponse.success(result);
        }else {
            return AjaxResponse.error(AjaxResponse.ErrorInfo.ERR_SQL_NOT_EXITS);
        }
    }
    @PostMapping("/sayHello")
    public String sayHello() {
        return "hello world !";
    }
}
