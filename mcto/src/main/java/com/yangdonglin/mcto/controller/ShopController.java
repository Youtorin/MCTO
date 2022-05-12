package com.yangdonglin.mcto.controller;


import com.yangdonglin.mcto.dto.IdDto;
import com.yangdonglin.mcto.entity.Shop;
import com.yangdonglin.mcto.entity.Shopinfo;
import com.yangdonglin.mcto.mapper.ShopMapper;
import com.yangdonglin.mcto.module.AjaxResponse;
import com.yangdonglin.mcto.module.BaseController;
import com.yangdonglin.mcto.service.ShopService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

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

    @Resource
    private ShopService shopService;

    @PostMapping("/getCurrShopInfo")
    AjaxResponse getCurrShopInfo(@RequestBody Shop param) {
        Shop shopAccount = shopMapper.getShopAccount(param.getShopAccount());
        if (ObjectUtils.isNotEmpty(shopAccount)) {
            try {
                boolean bool = shopAccount.getPassword().equals(param.getPassword());
                if (bool) {
                    return AjaxResponse.success(shopAccount);
                }
            } catch (Exception e) {
                return AjaxResponse.error(AjaxResponse.ErrorInfo.ERR_SQL_NOT_EXITS);
            }
        }
        return AjaxResponse.error(AjaxResponse.ErrorInfo.ERR_SQL_NOT_EXITS);
    }

    @PostMapping("/getInfo")
    public AjaxResponse getInfo(@RequestBody IdDto dto){
        Shop model = shopService.getById(dto.getId());
        if(ObjectUtils.isNotEmpty(model)){
            return AjaxResponse.success(model);
        }
        return AjaxResponse.success();
    }


    @PostMapping("/edit")
    public AjaxResponse edit(@RequestBody Shop dto){
        Shop model = shopService.getById(dto.getId());
        if(ObjectUtils.isNotEmpty(model)){
            model = dto;
            shopService.updateById(model);
            return AjaxResponse.success();
        }else{
            model = dto;
            model.setId(UUID.randomUUID().toString());
            model.setStatus(1);
            model.setCreateTime(new Date());
            shopService.save(model);
            return AjaxResponse.success();
        }
    }
}
