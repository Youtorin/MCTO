package com.yangdonglin.mcto.controller;


import com.yangdonglin.mcto.entity.Shop;
import com.yangdonglin.mcto.mapper.ShopMapper;
import com.yangdonglin.mcto.module.AjaxResponse;
import com.yangdonglin.mcto.module.BaseController;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 商家登录表 前端控制器
 * </p>
 *
 * @author Yangdonglin
 * @since 2022-02-21
 */
@RestController
@RequestMapping("/api/shop")
public class ShopController extends BaseController {


    @Resource
    private ShopMapper shopMapper;

    @PostMapping("/getCurrShopInfo")
    AjaxResponse getCurrShopInfo(@RequestBody Shop param) {
        Shop shopAccount = shopMapper.getShopAccount(param.getShopAccount());
        if (ObjectUtils.isNotEmpty(shopAccount)) {
            try {
                boolean bool = shopAccount.getPassword().equals(param.getPassword());
                if (bool) {
                    return AjaxResponse.success();
                }
                return AjaxResponse.error(AjaxResponse.ErrorInfo.ERR_SQL_NOT_EXITS);
            } catch (Exception e) {
                return AjaxResponse.error(AjaxResponse.ErrorInfo.ERR_SQL_NOT_EXITS);
            }
        }
        return AjaxResponse.error(AjaxResponse.ErrorInfo.ERR_SQL_NOT_EXITS);
    }
}
