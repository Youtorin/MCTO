package com.yangdonglin.mcto.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yangdonglin.mcto.dto.IdDto;
import com.yangdonglin.mcto.entity.Shop;
import com.yangdonglin.mcto.entity.Shopinfo;
import com.yangdonglin.mcto.module.AjaxResponse;
import com.yangdonglin.mcto.module.BaseController;
import com.yangdonglin.mcto.service.ShopService;
import com.yangdonglin.mcto.service.ShopinfoService;
import com.yangdonglin.mcto.utils.BeanCopyHelper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * <p>
 * 商铺信息表 前端控制器
 * </p>
 *
 * @author Yangdonglin
 * @since 2022-02-21
 */
@RestController
@RequestMapping("/api/shopinfo")
public class ShopinfoController extends BaseController {

    @Resource
    private ShopinfoService shopinfoService;

    @Resource
    private ShopService shopService;

    @PostMapping("/getInfo")
    public AjaxResponse getInfo(@RequestBody IdDto dto){
        Shopinfo model = shopinfoService.getById(dto.getId());
        if(ObjectUtils.isNotEmpty(model)){
            return AjaxResponse.success(model);
        }
        return AjaxResponse.success();
    }


    @PostMapping("/edit")
    public AjaxResponse edit(@RequestBody Shopinfo dto){
        Shopinfo model = shopinfoService.getById(dto.getId());
        if(ObjectUtils.isNotEmpty(model)){
            model = dto;
            shopinfoService.updateById(model);
            return AjaxResponse.success();
        }else{
            model = dto;
            model.setId(UUID.randomUUID().toString());
            model.setStatus(1);
            model.setCreateTime(new Date());
            model.setShopId(dto.getShopId());
            shopinfoService.save(model);
            return AjaxResponse.success();
        }
    }
}
