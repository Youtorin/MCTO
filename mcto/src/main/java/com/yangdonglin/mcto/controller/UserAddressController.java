package com.yangdonglin.mcto.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yangdonglin.mcto.dto.IdDto;
import com.yangdonglin.mcto.entity.UserAddress;
import com.yangdonglin.mcto.module.AjaxResponse;
import com.yangdonglin.mcto.module.BaseController;
import com.yangdonglin.mcto.module.PageResult;
import com.yangdonglin.mcto.service.UserAddressService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
@RequestMapping("/api/userAddress")
public class UserAddressController extends BaseController {

    @Resource
    private UserAddressService userAddressService;

    @PostMapping("/getList")
    AjaxResponse getList(@RequestBody IdDto idDto) {
        LambdaQueryWrapper<UserAddress> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserAddress::getUserId, idDto.getId())
                .eq(UserAddress::getStatus, 1)
                .orderByDesc(UserAddress::getIsDefault);
        List<UserAddress> result = userAddressService.list(wrapper);
        return AjaxResponse.success(result);
    }

    @PostMapping("/getModel")
    AjaxResponse getModel(@RequestBody IdDto idDto) {
        UserAddress model = userAddressService.getById(idDto.getId());
        if (ObjectUtils.isNotEmpty(model)) {
            return AjaxResponse.success(model);
        }
        return AjaxResponse.error(AjaxResponse.ErrorInfo.ERR_SQL_NOT_EXITS);
    }

    @PostMapping("/setDefault")
    AjaxResponse setDefault(@RequestBody IdDto idDto) {
        UserAddress model = userAddressService.getById(idDto.getId());
        filterDefaule(model.getUserId());
        model.setIsDefault(1);
        boolean bool = userAddressService.updateById(model);
        if (bool) {
            return AjaxResponse.success();
        }
        return AjaxResponse.error(AjaxResponse.ErrorInfo.ERR_SQL_UPDATE);
    }

    @PostMapping("/editAddress")
    AjaxResponse editAddress(@RequestBody UserAddress param) {
        UserAddress model = userAddressService.getById(param.getId());
        if (ObjectUtils.isNotEmpty(model)) {
            model = param;
            if(model.getIsDefault() == 1){
                filterDefaule(model.getUserId());
            }
            boolean bool = userAddressService.updateById(model);
            if (bool) {
                return AjaxResponse.success();
            }
            return AjaxResponse.error(AjaxResponse.ErrorInfo.ERR_SQL_INSERT);
        } else {
            model = param;
            model.setId(UUID.randomUUID().toString());
            model.setAddress(param.getDistrict() + param.getAddress());
            model.setStatus(1);
            model.setCreateTime(new Date());
            if(model.getIsDefault() == 1){
                filterDefaule(model.getUserId());
            }
            boolean bool = userAddressService.save(model);
            if (bool) {
                return AjaxResponse.success();
            }
            return AjaxResponse.error(AjaxResponse.ErrorInfo.ERR_SQL_UPDATE);
        }
    }

    @PostMapping("/deleteAddress")
    AjaxResponse deleteAddress(@RequestBody IdDto idDto) {
        UserAddress model = userAddressService.getById(idDto.getId());
        model.setStatus(-1);
        boolean bool = userAddressService.updateById(model);
        if (bool) {
            return AjaxResponse.success();
        }
        return AjaxResponse.error(AjaxResponse.ErrorInfo.ERR_SQL_DELETE);
    }

    Boolean filterDefaule(String userId){
        LambdaQueryWrapper<UserAddress> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserAddress::getUserId, userId)
                .eq(UserAddress::getStatus, 1);
        List<UserAddress> list = userAddressService.list(wrapper);
        List<UserAddress> temp = new ArrayList<>();
        for (UserAddress entity : list) {
            if(!entity.getId().equals(userId)){
                entity.setIsDefault(0);
                temp.add(entity);
            }
        }
        boolean bool = userAddressService.updateBatchById(temp);
        if(bool){
            return true;
        }
        return false;
    }
}
